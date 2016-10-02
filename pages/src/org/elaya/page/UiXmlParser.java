package org.elaya.page;
import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Objects;
import javax.xml.parsers.DocumentBuilder; 
import javax.xml.parsers.DocumentBuilderFactory;

import org.elaya.page.Application.InvalidAliasType;
import org.elaya.page.data.DataModel;
import org.elaya.page.data.DynamicMethod;
import org.elaya.page.data.DynamicObject;
import org.elaya.page.jsplug.JSPlug;
import org.elaya.page.quickform.OptionItem;
import org.slf4j.Logger;
import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;


public class UiXmlParser {

	Application application;
	LinkedList<String> errors=new LinkedList<String>();
	int cnt=0;
	Logger logger;	
	HashMap<String,Element<?>> elementIndex=null;
	
	protected boolean isNameValid(String p_name)
	{
		char l_ch;
		for(int l_cnt=0;l_cnt<p_name.length();l_cnt++)
		{
			l_ch=p_name.charAt(l_cnt);
			if((l_ch<'a' || l_ch>'z') && (l_ch<'A'||l_ch>'Z') && (l_ch<'0'||l_ch>'9') && (l_ch !='_')) return false;
		}
		return true;
	}
	
	public void addToElementIndex(Element<?> p_element){
		if(p_element.getName().length()>0){
			elementIndex.put(p_element.getName(),p_element);
		}
	}
	
	public void setElementIndex(HashMap<String,Element<?>> p_index)
	{
		elementIndex=p_index;
	}
	//--(Errors)----
	public void addError(String p_message){
		errors.add(p_message);		
	}
	
	private void addError(String p_error,Node p_node)
	{
		errors.add(getNodePath(p_node)+":"+p_error);
	}
	

	public LinkedList<String> getErrors()
	{
		return errors;
	}
	//-----
	
	public UiXmlParser(Application p_application,Logger p_logger){
		Objects.requireNonNull(p_application,"p_application");
		application=p_application;
		logger=p_logger;
	}
	
	private String getNodePath(Node p_node){
		String l_path="";
		Node l_node=p_node;
		while(l_node!=null){
			l_path=l_node.getNodeName()+"/"+l_path;
			l_node=l_node.getParentNode();
		}
		return l_path;
	}

	private void setPropertyByNode(DynamicMethod p_object,Node p_node) throws DOMException, Exception
	{
		p_object.put(p_node.getNodeName(),p_node.getNodeValue());
	}
	
	private String normelizeClassName(String p_className) throws Exception
	{
		
		if(p_className.startsWith("@")){			
			String l_className=application.getAlias(p_className.substring(1),AliasData.alias_element);
			if(l_className!=null){
				return l_className;				
			}
			
		} else {
			return p_className;
		}
		errors.add("Alias "+p_className+" not found");
		return p_className;
	}
	
	private Object createObjectFromNameP(String p_name,Class<?>[] p_types,Object[] p_params) throws Exception{
		String l_className=normelizeClassName(p_name);
		return DynamicObject.createObjectFromName(l_className, p_types, p_params, errors);
	}

	private Object createObjectFromName(String p_name) throws Exception
	{		
		return createObjectFromNameP(p_name, new Class<?>[]{},new Object[]{} );
	}
	
	private Object createPageFromTypeName(String p_name) throws Exception{
		return createObjectFromNameP(p_name,new Class<?>[]{Application.class},new Object[]{application});
	}
	
	private DataModel createDataModelFromTypeName(String p_name,Application p_application) throws Exception
	{
		Object l_object=createObjectFromNameP(p_name,new Class<?>[]{Application.class},new Object[]{p_application});
		if(l_object != null){
			if(l_object instanceof DataModel){
				return (DataModel)l_object;
			}
			errors.add(p_name+" is not a descendant of 'Data':"+l_object.getClass().getName());
		}
		return null;
	}
	
	
	public Node findNodeByTag(Node p_node,String p_tag){
		Node l_current=p_node.getFirstChild();
		while(l_current != null){
			if(l_current.getNodeName().equals(p_tag)) break;
			l_current=l_current.getNextSibling();
		}
		return l_current;
	}
	
	private String getAttributeValue(Node p_node,String p_name)
	{
		Node l_valueNode=p_node.getAttributes().getNamedItem(p_name);
		if(l_valueNode !=null){
			return l_valueNode.getNodeValue();
		} else {
			return null;
		}
	}
	
	private String getRefValue(Node p_node)
	{
		return getAttributeValue(p_node,"ref");
	}
	
	private String getFileValue(Node p_node)
	{
		return getAttributeValue(p_node,"file");
	}
	private String getTypeValue(Node p_node)
	{
		return getAttributeValue(p_node,"type");
	}
	
	private LinkedList<OptionItem> processOptions(Node p_parent){
			LinkedList<OptionItem> l_list=new LinkedList<OptionItem>();
			Node l_child=p_parent.getFirstChild();
			Node l_valueNode;
			Node l_textNode;
			while(l_child!=null){
				if(l_child.getNodeType()==Node.ELEMENT_NODE){
					if(l_child.getNodeName()=="option"){
						l_valueNode=l_child.getAttributes().getNamedItem("value");
						l_textNode=l_child.getAttributes().getNamedItem("text");
						if(l_valueNode ==null){
							errors.add("Missing 'value' attribute in tag "+getNodePath(l_child));				
						}
						if(l_textNode == null){
							errors.add("Missing 'text' attribute in tag "+getNodePath(l_child));						
						}
						if(l_valueNode != null && l_textNode != null){
							l_list.add(new OptionItem(l_valueNode.getNodeValue(),l_textNode.getNodeValue()));
						}
					} else {
						errors.add("Options list contain invalid tag "+getNodePath(l_child));
					}
				}
				l_child=l_child.getNextSibling();
			}
			return l_list;
	}
	
	public void processJSPlug(Element<?> p_element, Node p_parent) throws Exception
	{
		Node l_classNode=p_parent.getAttributes().getNamedItem("class");
		if(l_classNode != null){
			Object l_object=createObjectFromName(l_classNode.getNodeValue());
			if(l_object != null){
				if(l_object instanceof JSPlug){
					JSPlug l_plug=(JSPlug) l_object;
					NamedNodeMap l_attributes=p_parent.getAttributes();
					for(int l_cnt=0;l_cnt<l_attributes.getLength();l_cnt++){
						Node l_node=l_attributes.item(l_cnt);
						if(!l_node.getNodeName().equals("class") ){
							setPropertyByNode(l_plug,l_node);
						}
					}
					p_element.addJsPlug(l_plug);
					Node l_node=p_parent.getFirstChild();
					while(l_node != null){
						processProperty(p_element,l_node);
						l_node=l_node.getNextSibling();
					}
				} else {
					addError("Invalid type not an instance of JSPlug",p_parent);
				}
			}
		}
	}
	public void processSubStructure(DynamicMethod p_element,String p_name,Node p_parent) throws Exception{
		Node l_child=p_parent.getFirstChild();
		while(l_child != null){
			if(l_child.getNodeType()==Node.ELEMENT_NODE){
				if(l_child.getNodeName().equals("options")){
					p_element.put(p_name,processOptions(l_child));
				} else {
					errors.add("Invalid tag in options property definition "+getNodePath(l_child));
				}
			}
			l_child=l_child.getNextSibling();
		}
	}
	

	
	public void processProperty(DynamicMethod p_element,Node p_parent) throws DOMException, Exception{
		Node l_nameNode=p_parent.getAttributes().getNamedItem("name");
		String l_name;
		if(l_nameNode != null){
			l_name=l_nameNode.getNodeValue();
			if(p_parent.getChildNodes().getLength() ==0){
				p_element.put(l_name,"");
			} else if(p_parent.getChildNodes().getLength()==1 &&  p_parent.getFirstChild().getNodeType()!=Node.ELEMENT_NODE){
				p_element.put(l_name, p_parent.getFirstChild().getTextContent());
			} else {
				processSubStructure(p_element,l_name,p_parent);
			}
		} else {
			addError("Name attribute missing in property tag ",p_parent);
		}
	}
	
	
	public Element<?> processElement(Element<?> p_element,Node p_node) throws Exception
	{
		String l_type;
		String l_file;
		Element<?> l_element=null;
		if(p_node.getNodeName().equals("element")){
			l_type=getTypeValue(p_node);
			if(l_type != null){
				Object l_object=createObjectFromName(l_type);
				if(l_object!=null){
					if(l_object instanceof Element){						
						if(!p_element.checkElement((Element <?>)l_object)){
							errors.add("Can add object of type "+l_object.getClass().getName()+" to parent of type "+p_element.getClass().getName()) ;
						} else {
							p_element.addElement((Element<?>)l_object);
							processNodeDef((Element<?>)l_object,p_node);
							l_element=(Element<?>)l_object;
						}
					} else {
						errors.add("Object is not a element :"+l_object.getClass().getName());
					}
				}
			} else {
				l_file=getFileValue(p_node);
				if(l_file != null){					
					UiXmlParser l_parser=new UiXmlParser(application,logger);
					l_parser.setElementIndex(elementIndex);
					l_element=l_parser.parseElementXml(p_element, l_file);
					if(l_element !=null)processNodeDef(l_element,p_node);			
					errors.addAll(l_parser.getErrors());
				}else {
					String l_ref=getRefValue(p_node);
					if(l_ref != null){
						l_element=elementIndex.get(l_ref);
						if(l_element != null){
							processNodeDef(l_element,p_node);
						} else {
							errors.add("Reference '"+l_ref+"' not found");
						}
					} else {
						errors.add("Missing 'type' , 'file' or 'ref property :"+getNodePath(p_node));
					}
				}
			}
			if(l_element != null)l_element.setApplication(application);
		} else {
			errors.add("Invalid node "+getNodePath(p_node)+" inside 'Elements' definition");
		}		
		return l_element;
	}
	
	public void processNodeDef(Element<?> p_element,Node p_parent) throws Exception{		
		Node l_currentDef=p_parent.getFirstChild();
		NamedNodeMap l_properties=p_parent.getAttributes();
		Node l_node;
		String l_dataModelName;
		
		l_dataModelName=getAttributeValue(p_parent,"datamodel");
		
		if(l_dataModelName != null){				
			DataModel l_dataModel=createDataModelFromTypeName(l_dataModelName,application);
				if(l_dataModel==null){
					errors.add("Adaptername return null "+l_dataModelName);
				} else {
					((PageElement<?>)p_element).setDataModel(l_dataModel);
				}
		}
			
		for(int l_cnt=0;l_cnt<l_properties.getLength();l_cnt++){
			l_node=l_properties.item(l_cnt);
			if(!l_node.getNodeName().equals("type") && !l_node.getNodeName().equals("datamodel") && !l_node.getNodeName().equals("file") && !l_node.getNodeName().equals("ref") ){
					setPropertyByNode(p_element,l_node);
			}
		}
		
		addToElementIndex(p_element);
		if(!isNameValid(p_element.getName())){
			addError("Invlid name: '"+p_element.getName()+"'",p_parent);
		}
		while(l_currentDef!=null){
			if(l_currentDef.getNodeType()==Node.ELEMENT_NODE){
				if(l_currentDef.getNodeName().equals("element")){					
					processElement(p_element,l_currentDef);					
				} else if (l_currentDef.getNodeName().equals("property")){
					processProperty(p_element,l_currentDef);
				} else if (l_currentDef.getNodeName().equals("jsplug")){
					processJSPlug(p_element,l_currentDef);
				} else{
					errors.add("Invalid tag '"+l_currentDef.getNodeName()+"',expect 'property'  or 'element'");
				}
				
			}
			l_currentDef=l_currentDef.getNextSibling();
		}
			
	}
	
	public Page processRootNode(Node p_rootNode) throws Exception
	{
		Page l_page=null;
		String l_type=getTypeValue(p_rootNode);
		if(l_type != null){
			Object l_object;
			l_object=createPageFromTypeName(l_type);	
			if(l_object != null){
				if(l_object instanceof Page){
					l_page=(Page)l_object;
				} else {
					errors.add("Class '"+l_type+"' doesn't descent from Page");
					return null;
				}
			}
		} else {
			String l_file=getFileValue(p_rootNode);
			if(l_file != null){
				UiXmlParser  l_parser=new UiXmlParser(application,logger);
				l_parser.setElementIndex(elementIndex);
				l_page=l_parser.parseUiXml(l_file);
				errors.addAll(l_parser.getErrors());
				if(l_page==null){
					return null;
				}
			} else {
				String l_ref=getRefValue(p_rootNode);
				if(l_ref != null){
					Element<?> l_element=elementIndex.get(l_ref);
					if(l_element ==null){
						errors.add("Page with reference '"+l_ref+"' not found");
						return null;
					} else {
						if(l_element instanceof Page){
							l_page=(Page)l_element;
						} else {
							errors.add("Refenced object '"+l_ref+"is not a page" );
							return null;
						}
					}
				} else {
					errors.add("A Type,file of is missing");
				}
					
			}
		}
		processNodeDef(l_page,p_rootNode);
		l_page.setApplication(application);
		l_page.process();
		return l_page;
	}
	
	public Element<?> parseElementXml(Element<?> p_element,String p_fileName) throws Exception
	{		
		if(elementIndex==null) elementIndex=new HashMap<>();
		DocumentBuilderFactory l_factory=DocumentBuilderFactory.newInstance();
		DocumentBuilder l_builder=l_factory.newDocumentBuilder();
		Document l_doc=l_builder.parse(new File(application.getRealPath(p_fileName)));
		NodeList l_nl=l_doc.getChildNodes();
		Node l_rootNode=l_nl.item(0);
		l_rootNode.normalize();
		if(l_rootNode.getNodeName().equals("element")){
			return processElement(p_element,l_rootNode);
		} else {
			errors.add("'element' tag expected");
			return null;
		}
	}
	
	public Page parseUiXml(String p_fileName) throws Exception{
		if(elementIndex==null){
			elementIndex=new HashMap<>();
			logger.info("new index");
		}
		DocumentBuilderFactory l_factory=DocumentBuilderFactory.newInstance();
		DocumentBuilder l_builder=l_factory.newDocumentBuilder();
		Document l_doc=l_builder.parse(new File(application.getRealPath(p_fileName)));
		NodeList l_nl=l_doc.getChildNodes();
		Node l_rootNode=l_nl.item(0);
		l_rootNode.normalize();
		if(l_rootNode.getNodeName().equals("page")){
			return processRootNode(l_rootNode);
		} else {
			errors.add("'page' tag expected");
			return null;
		}
		
	}

}
