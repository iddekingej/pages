package org.elaya.page;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
 
public class AliasParser {

	private LinkedList<String> errors=new LinkedList<>();
	private Set<String> elements=new HashSet<>();
/**
 * Returns list of errors during parsing of the Alias file
 * @return LinkedList<String> of error strings
 */
	
	public List<String> getErrors()
	{
		return errors;
	}
	
	private void parseAliasNode(Node node,Map<String,AliasData> map)
	{
		Node alias;
		String aliasValue; 
		Node className;
		if(elements.contains(node.getNodeName())){
			alias=node.getAttributes().getNamedItem("alias");
			className=node.getAttributes().getNamedItem("value");
			if(alias==null){
				errors.add("'alias'  attribute not found");
			}
			if(className==null){
					errors.add("'value' attribute not found");
			} else if(alias != null){
				aliasValue=alias.getNodeValue();
				if(map.containsKey(aliasValue)){
					errors.add("Alias '"+aliasValue+"' allready defined");
				} else {		
					map.put(aliasValue,new AliasData(node.getNodeName(),className.getNodeValue()));
				}
			}
		
			
		} else {
			errors.add("'alias' tag expected but '"+node.getNodeName()+"' found");
		}
	}
	
	protected void parseAlias(Node pparent,Map<String,AliasData> map)
	{
		Node currentDef=pparent.getFirstChild();

		while(currentDef!=null){
			if(currentDef.getNodeType()==Node.ELEMENT_NODE){
				parseAliasNode(currentDef,map);
			}
			currentDef=currentDef.getNextSibling();
		}
	}
	
	/**
	 * Parse Alias file
	 * @param pinput Input stream
	 * @param pmap Found aliases are added to this map
	 * @throws ParserConfigurationException 
	 * @throws IOException 
	 * @throws SAXException 
	 * @throws Exception
	 */
	public void parseAliases(InputStream input,Map<String,AliasData> pmap) throws ParserConfigurationException, SAXException, IOException{
		Objects.requireNonNull(input, "Input stream of alias parser is null");
		DocumentBuilderFactory factory=DocumentBuilderFactory.newInstance();
		DocumentBuilder builder=factory.newDocumentBuilder();
		Document doc=builder.parse(input);
		NodeList nl=doc.getChildNodes();
		Node rootNode=nl.item(0);
		rootNode.normalize();
		elements.add(AliasData.ALIAS_ELEMENT);
		elements.add(AliasData.ALIAS_JSFILE);
		elements.add(AliasData.ALIAS_CSSFILE);
		elements.add(AliasData.ALIAS_URL);
		elements.add(AliasData.ALIAS_RECIEVER);
		elements.add(AliasData.ALIAS_SECURITY);
		if("aliasses".equals(rootNode.getNodeName())){
			parseAlias(rootNode,pmap);
		} else {
			errors.add("<aliasses> expected as outer tag");
		}
		input.close();
	}
}
