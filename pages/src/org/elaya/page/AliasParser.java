package org.elaya.page;

import java.io.InputStream;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.slf4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class AliasParser {

	private Logger      logger;
	private LinkedList<String> errors=new LinkedList<String>();
	private Set<String> elements=new HashSet<String>();
/**
 * Returns list of errors during parsing of the Alias file
 * @return LinkedList<String> of error strings
 */
	
	public LinkedList<String> getErrors()
	{
		return errors;
	}
	
/** 
 * Constructor of  AliasParser
 * @param p_logger Logger used for logging
 */
	public AliasParser(Logger p_logger)
	{
		Objects.requireNonNull(p_logger);
		logger=p_logger;
	}
	
	protected void parseAlias(Node p_parent,Map<String,AliasData> p_map)
	{
		Node l_currentDef=p_parent.getFirstChild();
		Node l_alias;
		String l_aliasValue; 
		Node l_class;
		while(l_currentDef!=null){
			if(l_currentDef.getNodeType()==Node.ELEMENT_NODE){
				if(elements.contains(l_currentDef.getNodeName())){
					l_alias=l_currentDef.getAttributes().getNamedItem("alias");
					l_class=l_currentDef.getAttributes().getNamedItem("value");
					if(l_alias==null)errors.add("'alias'  attribute not found");
					if(l_class==null){
							errors.add("'class' attribute not found");
					} else {
						if(l_alias != null){
							l_aliasValue=l_alias.getNodeValue();
							if(p_map.containsKey(l_aliasValue)){
								errors.add("Alias '"+l_aliasValue+"' allready defined");
							} else {		
								p_map.put(l_aliasValue,new AliasData(l_currentDef.getNodeName(),l_class.getNodeValue()));
							}
						}
					}
					
				} else {
					errors.add("'alias' tag expected but '"+l_currentDef.getNodeName()+"' found");
				}
			}
			l_currentDef=l_currentDef.getNextSibling();
		}
	}
	
	/**
	 * Parse Alias file
	 * @param p_fileName Name of file to parse 
	 * @param p_map Found aliases are added to this map
	 * @throws Exception
	 */
	void parseAliases(InputStream p_input,Map<String,AliasData> p_map) throws Exception{		
		DocumentBuilderFactory l_factory=DocumentBuilderFactory.newInstance();
		DocumentBuilder l_builder=l_factory.newDocumentBuilder();
		Document l_doc=l_builder.parse(p_input);
		NodeList l_nl=l_doc.getChildNodes();
		Node l_rootNode=l_nl.item(0);
		l_rootNode.normalize();
		elements.add("element");
		elements.add("jsfile");
		elements.add("cssfile");
		elements.add("url");
		elements.add("reciever");
		if(l_rootNode.getNodeName().equals("aliasses")){
			parseAlias(l_rootNode,p_map);
		} else {
			errors.add("<aliasses> expected as outer tag");
		}

	}
}
