package org.elaya.page;

import java.io.File;
import java.util.LinkedList;
import java.util.Map;
import java.util.Objects;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.slf4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class AliasParser {
	private Application application;
	private Logger  logger;
	private LinkedList<String> errors=new LinkedList<String>();
	
	public LinkedList<String> getErrors()
	{
		return errors;
	}
	
	public AliasParser(Application p_application)
	{
		Objects.requireNonNull(p_application,"p_application");
		application=p_application;
		logger=p_application.getLogger();
	}
	
	void parseAlias(Node p_parent,Map<String,String> p_map)
	{
		Node l_currentDef=p_parent.getFirstChild();
		Node l_alias;
		String l_aliasValue; 
		Node l_class;
		while(l_currentDef!=null){
			if(l_currentDef.getNodeType()==Node.ELEMENT_NODE){
				if(l_currentDef.getNodeName().equals("alias")){
					l_alias=l_currentDef.getAttributes().getNamedItem("alias");
					l_class=l_currentDef.getAttributes().getNamedItem("class");
					if(l_alias==null)errors.add("'alias'  attribute not found");
					if(l_class==null){
							errors.add("'class' attribute not found");
					} else {
						if(l_alias != null){
							l_aliasValue=l_alias.getNodeValue();
							if(p_map.containsKey(l_aliasValue)){
								errors.add("Alias '"+l_aliasValue+"' allready defined");
							} else {								
								p_map.put(l_aliasValue,l_class.getNodeValue());
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
	void parseAliases(String p_fileName,Map<String,String> p_map) throws Exception{		
		DocumentBuilderFactory l_factory=DocumentBuilderFactory.newInstance();
		DocumentBuilder l_builder=l_factory.newDocumentBuilder();
		Document l_doc=l_builder.parse(new File(application.getRealPath(p_fileName)));
		NodeList l_nl=l_doc.getChildNodes();
		Node l_rootNode=l_nl.item(0);
		l_rootNode.normalize();
		if(l_rootNode.getNodeName().equals("aliasses")){
			parseAlias(l_rootNode,p_map);
		} else {
			errors.add("<aliasses> expected as outer tag");
		}

	}
}
