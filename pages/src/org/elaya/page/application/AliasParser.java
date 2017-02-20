package org.elaya.page.application;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import org.elaya.page.Errors.ReplaceVarException;
import org.elaya.page.xml.XMLParserBase;
import org.w3c.dom.Node;
 
public class AliasParser extends XMLParserBase<Map<String,AliasData>>{

	/**
	 * String added by default to the alias value
	 * this is set by the alias tag
	 */
	private String prefix="";
	private Application application;
	private HashMap<String,AliasNamespace> elements=new HashMap<>();
	private Map<String,AliasData> aliasList;
	
	public AliasParser(Application papplication,Map<String,AliasData> paliasList) throws IllegalAccessException
	{
		aliasList=paliasList;
		application=papplication;
		AliasNamespace[] items=AliasNamespace.class.getEnumConstants();
		for(AliasNamespace item:items){
			elements.put(item.getTag(), item);			
		}
	}
	
	/**
	 * Parse a single alias node from XML file. 
	 * 
	 * @param node XML Node containing the alias definition
	 * @throws org.elaya.page.xml.XMLParserBase.XMLLoadException
	 */

	private void parseAliasNode(Node node) throws org.elaya.page.xml.XMLParserBase.XMLLoadException
	{
		Node alias;
		String aliasValue; 
		Node className;
		if(elements.containsKey(node.getNodeName())){
			alias=node.getAttributes().getNamedItem("alias");
			className=node.getAttributes().getNamedItem("value");
			if(alias==null){
				throw new XMLLoadException("'alias' attribute not found",node);
			}
			if(className==null){
				throw new XMLLoadException("'value' attribute not found",node);
			}
			
			aliasValue=alias.getNodeValue();
			if(aliasList.containsKey(aliasValue)){
				throw new XMLLoadException("Alias '"+aliasValue+"' allready exists",node);
			} 		
			
			aliasList.put(aliasValue,new AliasData(elements.get(node.getNodeName()),prefix+className.getNodeValue()));
						
		} else {
			throw new XMLLoadException("Wrong type of alias tag  found:"+node.getNodeName(),node);
		}
	}
	
	/**
	 * Parse root, scans through the nodes:
	 * "prefix" node sets te default value prefix and recrusivly calls parseAlias
	 * to parse it's subnodes
	 * The alias nodes are parsed by "parseAliasNode"
	 * 
	 * @param pparent This method scand through the child elements of this node
	 * @throws ReplaceVarException 
	 */
	
	private void parseAliases(Node pparent) throws XMLLoadException, ReplaceVarException
	{
		Node currentDef=pparent.getFirstChild();
		 
		while(currentDef!=null){
			if(currentDef.getNodeType()==Node.ELEMENT_NODE){
				if(elements.containsKey(currentDef.getNodeName())){
					parseAliasNode(currentDef);
				} else if("prefix".equals(currentDef.getNodeName())){
					String prvPrefix=prefix;
					String newPrefix=getAttributeValue(currentDef,"prefix");
					prefix=prefix+newPrefix;
					parseAliases(currentDef);
					prefix=prvPrefix;
				} else {
					throw new XMLLoadException("Invalid node",currentDef);
				}
			}
			currentDef=currentDef.getNextSibling();
		}
	}
	
	@Override
	protected Map<String,AliasData> parseRootNode(Node node) throws XMLLoadException {
		if(!"aliases".equals(node.getNodeName())){
			throw new XMLLoadException("'aliases' node expected",node);
		}
		try{
			parseAliases(node);
		}catch(Exception e){
			throw new XMLLoadException("",e,node);
		}
		return aliasList;
	}

	@Override
	protected InputStream openFile(String fileName) throws FileNotFoundException {
		return application.getConfigStream(fileName);
	}
}
