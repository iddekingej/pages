package org.elaya.page.application;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.elaya.page.xml.XMLParserBase;
import org.w3c.dom.Node;
 
public class AliasParser extends XMLParserBase<Map<String,AliasData>>{

	private Application application;
	private Set<String> elements=new HashSet<>();
	private Map<String,AliasData> aliasList;
	
	public AliasParser(Application papplication,Map<String,AliasData> paliasList)
	{
		aliasList=paliasList;
		application=papplication;
		elements.add(AliasData.ALIAS_ELEMENT);
		elements.add(AliasData.ALIAS_JSFILE);
		elements.add(AliasData.ALIAS_CSSFILE);
		elements.add(AliasData.ALIAS_URL);
		elements.add(AliasData.ALIAS_RECIEVER);
		elements.add(AliasData.ALIAS_SECURITY);
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
		if(elements.contains(node.getNodeName())){
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
			
			aliasList.put(aliasValue,new AliasData(node.getNodeName(),className.getNodeValue()));
						
		} else {
			throw new XMLLoadException("Wrong type of alias tag  found:"+node.getNodeName(),node);
		}
	}
	
	/**
	 *  'aliases' is the top XML node, the children of this node are the alias definitions  
	 *  This route iterates through the children. For each node parseAliasNode is called. 
	 *  
	 * @param pparent
	 * @throws org.elaya.page.xml.XMLParserBase.XMLLoadException
	 */
	
	private void parseAlias(Node pparent) throws org.elaya.page.xml.XMLParserBase.XMLLoadException
	{
		Node currentDef=pparent.getFirstChild();
		 
		while(currentDef!=null){
			if(currentDef.getNodeType()==Node.ELEMENT_NODE){
				parseAliasNode(currentDef);
			}
			currentDef=currentDef.getNextSibling();
		}
	}
	
	@Override
	protected Map<String,AliasData> parseRootNode(Node node) throws XMLLoadException {
		if(!"aliases".equals(node.getNodeName())){
			throw new XMLLoadException("'aliases' node expected",node);
		}
		parseAlias(node);
		return aliasList;
	}

	@Override
	protected InputStream openFile(String fileName) throws FileNotFoundException {
		return application.getConfigStream(fileName);
	}
}
