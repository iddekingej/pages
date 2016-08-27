package org.elaya.page;
import java.io.File;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.LinkedList;
import java.util.Objects;
import javax.xml.parsers.DocumentBuilder; 
import javax.xml.parsers.DocumentBuilderFactory;

import org.elaya.page.data.Data;
import org.elaya.page.form.OptionItem;
import org.slf4j.Logger;
import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;


public class UiXmlParser {

	Application application;
	LinkedList<String> errors=new LinkedList<String>();
	Logger logger;
	Data data;
	
	public LinkedList<String> getErrors()
	{
		return errors;
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
	
	private String replaceVariables(String p_value) throws Exception
	{
		int l_pos=0;
		int l_newPos;
		StringBuilder l_return=new StringBuilder();
		String l_varName;
		Object l_value;
		while(true){
			l_newPos=p_value.indexOf("${",l_pos);
			if(l_newPos==-1){
				l_return.append(p_value.substring(l_pos));
				break;
			}
			l_return.append(p_value.substring(l_pos,l_newPos));
			l_pos=p_value.indexOf("}",l_newPos);
			if(l_pos==-1){
				errors.add("Missing end }"); //TODO: Error Location
				break;
			}
			l_varName=p_value.substring(l_newPos+2,l_pos);			
			if(!data.contains(l_varName)){
				errors.add("Variable '"+l_varName+"' not found in data:"+data.getClass().getName());
			} else {
				l_value=data.get(l_varName);				
				if(l_value != null)	l_return.append(l_value.toString());
			}
			l_pos++;
		}
		return l_return.toString();
	}
	
	
	private <T> void setProperty(Object p_object,String p_name,T p_value) throws Exception
	{
		Method l_methods[]=p_object.getClass().getMethods();
		Method l_method=null;
		
		int l_cnt;
		String l_name="set"+p_name;
		for(l_cnt=0;l_cnt<l_methods.length;l_cnt++){
			l_method=l_methods[l_cnt];
			if(l_method.getName().toLowerCase().equals(l_name)){
				Class<?> l_types[]=l_method.getParameterTypes();
				if(l_types.length != 1)continue;
				if(l_types[0]==p_value.getClass()){
					if(p_value instanceof String){
						l_method.invoke(p_object,replaceVariables((String)p_value));
					} else {
						l_method.invoke(p_object, p_value);
					}
					return;
				}				
			}
		}
		
		errors.add("Method "+p_name+" not found for object type "+p_object.getClass().getName());
	}
	
	private Object createObjectFromNameP(String p_name,Class<?>[] p_types,Object[] p_params) throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException{
		Class<?> l_class;		
		try{
			l_class=Class.forName(p_name);			
		}catch(ClassNotFoundException l_e){
			errors.add("Page class "+p_name+" not found");
			return null;
		}
	
		Constructor<?> l_constructor;
		try{
			l_constructor=l_class. getConstructor(p_types);
		} catch(NoSuchMethodException l_e){
			errors.add("Object doesn't have a constructor without parameters");
			return null;
		}
		
		Object l_object=l_constructor.newInstance(p_params);

		return l_object;  

	}

	private Object createElementFromName(String p_name) throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException
	{		
		return createObjectFromNameP(p_name, new Class<?>[]{},new Object[]{} );
	}
	
	private Object createPageFromTypeName(String p_name) throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException{
		return createObjectFromNameP(p_name,new Class<?>[]{Application.class},new Object[]{application});
	}
	
	private Data createDataFromTypeName(String p_name,Data p_parent) throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException
	{
		Object l_object=createObjectFromNameP(p_name,new Class<?>[]{Data.class},new Object[]{p_parent});
		if(l_object instanceof Data){
			return (Data)l_object;
		}
		errors.add(p_name+" is not a descendant of 'Data'");
		return null;
	}
	
	public UiXmlParser(Application p_application,Data p_data){
		application=p_application;
		data=p_data;
		logger=p_application.getLogger();
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
	
	public void processSubStructure(Element p_element,String p_name,Node p_parent) throws Exception{
		Node l_child=p_parent.getFirstChild();
		while(l_child != null){
			if(l_child.getNodeType()==Node.ELEMENT_NODE){
				if(l_child.getNodeName().equals("options")){
					setProperty(p_element,p_name,processOptions(l_child));
				} else {
					errors.add("Invalid tag in options property definition "+getNodePath(l_child));
				}
			}
			l_child=l_child.getNextSibling();
		}
	}
	public void processSubValue(Element p_element,Node p_parent) throws DOMException, Exception{
		Node l_nameNode=p_parent.getAttributes().getNamedItem("name");
		String l_name;
		if(l_nameNode != null){
			l_name=l_nameNode.getNodeValue();
			if(p_parent.getChildNodes().getLength() ==0){
				setProperty(p_element,l_name,"");
			} else if(p_parent.getChildNodes().getLength()==1 &&  p_parent.getFirstChild().getNodeType()!=Node.ELEMENT_NODE){
				setProperty(p_element,l_name,p_parent.getFirstChild().getTextContent());
			} else {
				processSubStructure(p_element,l_name,p_parent);
			}
		} else {
			errors.add("Name attribute missing in property tag "+getNodePath(p_parent));
		}
	}
	
	
	public void processProperties(Element p_element,Node p_parent) throws DOMException, Exception{
		Objects.requireNonNull(p_element);
		Objects.requireNonNull(p_parent);
		Node l_current=p_parent.getFirstChild();
		
		while(l_current != null){
			if(l_current.getNodeType()==Node.ELEMENT_NODE){
				if(l_current.getNodeName().equals("property")){
					processSubValue(p_element,l_current);
				}  else {
					errors.add("Invalid properties definition "+getNodePath(l_current));
				}
			}
			l_current=l_current.getNextSibling();
		}
	}
	
	public Element processElement(Element<ThemeItemBase> p_element,Node p_node) throws Exception
	{
		String l_type;
		String l_file;
		Element<ThemeItemBase> l_element=null;
		if(p_node.getNodeName().equals("element")){
			l_type=getTypeValue(p_node);
			if(l_type != null){
				Object l_object=createElementFromName(l_type);
				if(l_object!=null){
					if(l_object instanceof Element){								
						p_element.addElement((Element)l_object);
						processNodeDef((Element)l_object,p_node);
						l_element=(Element)l_object;
					}
				}
			} else {
				l_file=getFileValue(p_node);
				if(l_file != null){					
					UiXmlParser l_parser=new UiXmlParser(application,data);
					l_element=l_parser.parseElementXml(p_element, l_file);
					if(l_element !=null)processNodeDef(l_element,p_node);			
					errors.addAll(l_parser.getErrors());
				}else {
					errors.add("Missing 'type' or 'file' property :"+getNodePath(p_node));
				}
			}
		} else {
			errors.add("Invalid node "+getNodePath(p_node)+" inside 'Elements' definition");
		}		
		return l_element;
	}
	
	public void processElements(Element p_element,Node p_parent) throws Exception{
		
		Objects.requireNonNull(p_element,"p_element");
		Objects.requireNonNull(p_parent,"p_parent");
		Node l_current=p_parent.getFirstChild();
		while(l_current!=null){
			if(l_current.getNodeType()==Node.ELEMENT_NODE){
				processElement(p_element,l_current);
			}
			l_current=l_current.getNextSibling();
		}

	}
	@SuppressWarnings("rawtypes")
	public void processNodeDef(Element p_element,Node p_parent) throws Exception{		
		Node l_currentDef=p_parent.getFirstChild();
		NamedNodeMap l_properties=p_parent.getAttributes();
		Node l_node;
		Data l_prvData=data;
		
		String l_adapterName=getAttributeValue(p_parent,"adapter");
		if(p_element instanceof PageElement){
			if(l_adapterName != null){				
				data=createDataFromTypeName(l_adapterName,data);
			}
			((PageElement)p_element).setData(data);
		} else {
			if(l_adapterName != null){
				errors.add("Adapter can only be used with page element ");//TODO position form node
			}			
		} 
		
		for(int l_cnt=0;l_cnt<l_properties.getLength();l_cnt++){
			l_node=l_properties.item(l_cnt);
			if(!l_node.getNodeName().equals("type") && !l_node.getNodeName().equals("adapter") && !l_node.getNodeName().equals("file")){
					setProperty(p_element,l_node.getNodeName(),l_node.getNodeValue());
			}
		}
		
		while(l_currentDef!=null){
			if(l_currentDef.getNodeType()==Node.ELEMENT_NODE){
				if(l_currentDef.getNodeName().equals("elements")){					
					processElements(p_element,l_currentDef);					
				} else if (l_currentDef.getNodeName().equals("properties")){
					processProperties(p_element,l_currentDef);
				}
				
			}
			l_currentDef=l_currentDef.getNextSibling();
		}
		data=l_prvData;
			
	}
	
	public Page processRootNode(Node p_rootNode) throws Exception
	{
		Page l_page=null;
		String l_type=getTypeValue(p_rootNode);
		if(l_type != null){
			Object l_object;
			l_object=createPageFromTypeName(l_type);
			if(l_object instanceof Page){
				l_page=(Page)l_object;
		
			} else {
				errors.add("Class '"+l_type+"' doesn't discent from Page");
				return null;
			}
		} else {
			String l_file=getFileValue(p_rootNode);
			if(l_file != null){
				UiXmlParser  l_parser=new UiXmlParser(application,data);
				l_page=l_parser.parseUiXml(l_file);
				errors.addAll(l_parser.getErrors());
				if(l_page==null){
					return null;
				}
			}
		}
		processNodeDef(l_page,p_rootNode);
		return l_page;
	}
	
	public Element parseElementXml(Element p_element,String p_fileName) throws Exception
	{		
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
