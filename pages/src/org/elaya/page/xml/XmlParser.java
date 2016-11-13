package org.elaya.page.xml;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Objects;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.elaya.page.Application;
import org.elaya.page.Errors;
import org.elaya.page.Errors.XmlLoadError;
import org.elaya.page.data.DynamicObject;
import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

abstract public class XmlParser {
	private HashMap<String,XmlConfig> configs=new HashMap<>();
	private LinkedList<String> errors=new LinkedList<>();
	private HashMap<String,Object> nameIndex;
	private Application application;
	
	public XmlParser(Application p_application)
	{
		nameIndex=new HashMap<String,Object>();
		application=p_application;
	}
	
	public XmlParser(Application p_application,HashMap<String,Object> p_nameIndex) {
		nameIndex=p_nameIndex;
		application=p_application;
	}
	
	public HashMap<String,Object> getNameIndex(){ return nameIndex;}
	public Application getApplication(){ return application;}
	
	
	private String getAttributeValue(Node p_node,String p_name)
	{
		Node l_valueNode=p_node.getAttributes().getNamedItem(p_name);
		if(l_valueNode !=null){
			return l_valueNode.getNodeValue();
		} else {
			return null;
		}		
	}
		
	public LinkedList<String> getErrors(){ return errors;}
	
	protected void addError(String p_error,Node p_node) throws XmlLoadError
	{
		
		throw new Errors.XmlLoadError(p_error);
	}

	protected void addConfig(String p_name,XmlConfig p_config)
	{
		configs.put(p_name,p_config);
	}

	private Object parseParameterValueNode(Object p_parent,Node p_node) throws DOMException, Exception
	{
		Node l_first=p_node.getFirstChild();
		if(l_first.getNextSibling() != null){
			addError("Parameter should contain definition of one object",p_node);
			return null;
		} else if(l_first.getNodeType() != Node.ELEMENT_NODE){
			return l_first.getTextContent();
		} else {
			return parseNode(p_parent,p_node);
		}
	}
	
	private void parseParameters(Object p_parent,Node p_node) throws DOMException, Exception{
		Node l_child=p_node.getFirstChild();
		while(l_child!=null){
			if(l_child.getNodeType()==Node.ELEMENT_NODE){
				if(!l_child.getNodeName().equals("parameter")){
					addError("'parameter' node expected",l_child);
				} else {
					String l_name=getAttributeValue(l_child,"name");
					if(l_name==null){
						addError("Name attribute not found",l_child);
						continue;
					}
					Object l_value;
					if(l_child.getChildNodes().getLength()==0){
						l_value="";
					} else {
						l_value=parseParameterValueNode(p_parent,p_node);
					}
					DynamicObject.put(p_parent, l_name, l_value);
				}
			}
			l_child=l_child.getNextSibling();
		}
	}
	private void parseChildNodes(Object p_parent,Node p_node) throws DOMException, Exception
	{
		Node l_child=p_node.getFirstChild();
		String l_debug="";
		while(l_child!=null){
			if(l_child.getNodeName().equals("parameters")){
				parseParameters(p_parent,p_node);
			} else {
				l_debug = l_debug+String.valueOf(l_child.getNodeType());
				if(l_child.getNodeType()==Node.ELEMENT_NODE){

					Object l_object=parseNode(p_parent,l_child);
					if(l_object != null){
						if(! configs.containsKey(p_node.getNodeName())){
							addError("Unkown node type :'"+p_node.getNodeName()+"'",p_node);
							return;
						}
						
						XmlConfig l_info=configs.get(l_child.getNodeName());
						String l_methodName=l_info.getDefaultSetMethod();
						try{							
							Method l_method=p_parent.getClass().getMethod(l_methodName, new Class<?>[]{l_info.getBaseClass()});
							l_method.invoke(p_parent,l_object);
						} catch(NoSuchMethodException l_e){
							addError("Method "+l_methodName + " in object "+p_parent.getClass().getName()+" doesn't exists",p_node);
						}
						parseChildNodes(l_object,l_child);
					}
				}
			}
			l_child=l_child.getNextSibling();
		}
	}
	private void parseAttributes(Object p_object,Node p_node) throws DOMException, Exception
	{
		Objects.requireNonNull(p_object);
		NamedNodeMap l_map=p_node.getAttributes();
		Node l_attribute;
		String l_attributeName;
		for(int l_cnt=0;l_cnt<l_map.getLength();l_cnt++){
			l_attribute=l_map.item(l_cnt);
			l_attributeName=l_attribute.getNodeName();
			if(!l_attributeName.equals("name") && !l_attributeName.equals("ref") && !l_attributeName.equals("class") && !l_attributeName.equals("file")){
				if(!DynamicObject.containsKey(p_object,l_attribute.getNodeName())){
					addError("Object of type "+p_object.getClass().getName()+" doesn't contain attribute "+l_attributeName,p_node);
				} else {
					DynamicObject.put(p_object,l_attribute.getNodeName(),l_attribute.getNodeValue());
				}
			}
		}	
	}
	
	void parseNamedObject(Object p_object,Node p_node) throws Exception
	{
		String l_name=getAttributeValue(p_node,"name");
		if(l_name != null){
			DynamicObject.put(p_object,"name",l_name);
			if(nameIndex.containsKey(l_name)){
				addError("An object with name "+l_name+" allready exists",p_node);
			} else {
				nameIndex.put(l_name,p_object);
			}
		}
	}
	
	Object parseNode(Object p_parent,Node p_node) throws DOMException, Exception
	{
		if(! configs.containsKey(p_node.getNodeName())){
			addError("Unkown node type :'"+p_node.getNodeName()+"'",p_node);
			return null;
		}
		XmlConfig l_info=configs.get(p_node.getNodeName());
		Class<?> l_base=l_info.getBaseClass();
		Class<?> l_default=l_info.getDefaultClass();
		Object l_object;
		String l_fileName=getAttributeValue(p_node,"file");
		if(p_parent==null && l_info.getNeedParent()){
			addError("Element "+p_node.getNodeName()+" needs a parent but=null",p_node);
		}
		if(l_info.getCustom()){
			return parseCustom(p_parent,p_node);
		}
		if(l_fileName != null){
			XmlParser l_parser=createParser();
			l_object=l_parser.parse(l_fileName);
			if(l_object==null){
				return null;
			}
		} else {
			String l_ref=getAttributeValue(p_node,"ref");
			if(l_ref != null){
				if(nameIndex.containsKey(l_ref)){
					l_object=nameIndex.get(l_ref);
					parseAttributes(l_object,p_node);
					parseChildNodes(l_object,p_node);
					return null;

				} else {
					addError("Reference to item "+l_ref+" does not exists",p_node);
					return null;
				}
			} else if(l_base != null){
				String l_className=getAttributeValue(p_node,"class");

				if(l_className == null){
					if(l_default ==null){
						addError("Node requires 'class' property "+p_node.getNodeName(),p_node);
						return null;
					} else {
						l_object=l_default.newInstance();
						parseNamedObject(l_object,p_node);
					}
				} else {
					String l_normalizedClass=normalizeClassName(l_className);
					if(l_normalizedClass==null){
						addError("Alias "+l_className+" not found",p_node);
						return null;
					}
					l_object=DynamicObject.createObjectFromName(l_normalizedClass,errors);
					if(l_object==null){
						return null;
					}
					parseNamedObject(l_object,p_node);

				}
			} else {
				if(l_default ==null){
					addError("Internal error. Not a dynamic object, but defaultClass not set in config. Node name="+p_node.getNodeName(),p_node);
					return null;
				} else {
					l_object=l_default.newInstance();
					parseNamedObject(l_object,p_node);
				}	
			}
		}
		AfterCreate(l_object);
		parseAttributes(l_object,p_node);
		return l_object;
	}
	
	public Object parse(String p_fileName) throws Exception{
		addConfig();
		DocumentBuilderFactory l_factory=DocumentBuilderFactory.newInstance();
		DocumentBuilder l_builder=l_factory.newDocumentBuilder();
		InputStream l_stream=openFile(p_fileName);
		if(l_stream ==null){
			throw new FileNotFoundException("pages/"+p_fileName);
		}
		Document l_doc=l_builder.parse( l_stream);
		NodeList l_nl=l_doc.getChildNodes();
		Node l_rootNode=l_nl.item(0);
		Object l_object=parseNode(null,l_rootNode);
		parseChildNodes(l_object,l_rootNode);
		return l_object;
	}

	protected void AfterCreate(Object p_parent) throws Exception
	{
		
	}
	protected Object parseCustom(Object p_parent,Node p_node) throws XmlLoadError
	{
		return null;
	}
	abstract protected InputStream openFile(String p_fileName);
	abstract protected XmlParser createParser();
	abstract protected void addConfig();
	abstract protected String normalizeClassName(String p_name) throws Exception;
}