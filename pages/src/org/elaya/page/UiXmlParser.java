package org.elaya.page;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.LinkedList;
import java.util.Objects;

import javax.xml.parsers.DocumentBuilder; 
import javax.xml.parsers.DocumentBuilderFactory;

import org.slf4j.Logger;

import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;




public class UiXmlParser {

	Application application;
	LinkedList<String> errors=new LinkedList<String>();
	Logger logger;
	
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
	
	private void setProperty(Object p_object,String p_name,String p_value) throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException
	{
		Method l_methods[]=p_object.getClass().getMethods();
		Method l_method=null;
		int l_cnt;
		String l_name="set"+p_name;
		for(l_cnt=0;l_cnt<l_methods.length;l_cnt++){
			l_method=l_methods[l_cnt];
			if(l_method.getName().toLowerCase().equals(l_name)){
				l_method.invoke(p_object, p_value);
				return;
			}
		}
		
		errors.add("Method "+p_name+" not find for object type "+p_object.getClass().getName());
	}
	
	private Object createElementFromName(String p_name) throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException{
		Class<?> l_class;		
		try{
			l_class=Class.forName(p_name);			
		}catch(ClassNotFoundException l_e){
			errors.add("Page class "+p_name+" not found");
			return null;
		}
		Class<?>[] l_types={};
		
		Constructor<?> l_constructor;
		try{
			l_constructor=l_class. getConstructor(l_types);
		} catch(NoSuchMethodException l_e){
			errors.add("Object doesn't have a constructor without parameters");
			return null;
		}
		
		Object[] l_params={};
		Object l_object=l_constructor.newInstance(l_params);

				
		
		return l_object;  

	}
	
	private Object createObjectFromName(String p_name) throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException{
		Class<?> l_class;		
		try{
			l_class=Class.forName(p_name);			
		}catch(ClassNotFoundException l_e){
			errors.add("Page class "+p_name+" not found");
			return null;
		}
		Class<?>[] l_types={Application.class};
		
		Constructor<?> l_constructor;
		try{
			l_constructor=l_class.getConstructor(l_types);
		} catch(NoSuchMethodException l_e){
			errors.add("Object doesn't have a constructor without parameters");
			return null;
		}
		
		Object[] l_params={application};
		Object l_object=l_constructor.newInstance(l_params);

				
		
		return l_object;  

	}
	
	public UiXmlParser(Application p_application){
		application=p_application;
	}
	
	public Node findNodeByTag(Node p_node,String p_tag){
		Node l_current=p_node.getFirstChild();
		while(l_current != null){
			if(l_current.getNodeName().equals(p_tag)) break;
			l_current=l_current.getNextSibling();
		}
		return l_current;
	}
	
	private String getTypeValue(Node p_node)
	{
		Node l_typeNode=p_node.getAttributes().getNamedItem("type");
		if(l_typeNode != null){
			return l_typeNode.getNodeValue();
		} else {
			errors.add("Missing 'type' at '"+p_node.getNodeName()+"' tag ");
		}
		return null;	
	}
	
	public void processElements(Element p_element,Node p_parent) throws Exception{
		
		String l_type;
		Objects.requireNonNull(p_element,"p_element");
		Objects.requireNonNull(p_parent,"p_parent");
		Node l_current=p_parent.getFirstChild();
		while(l_current!=null){
			if(l_current.getNodeType()==Node.ELEMENT_NODE){
				if(l_current.getNodeName().equals("element")){
					l_type=getTypeValue(l_current);
					if(l_type != null){
						Object l_object=createElementFromName(l_type);
						if(l_object!=null){
							if(l_object instanceof Element){								
								p_element.addElement((Element)l_object);
								processNodeDef((Element)l_object,l_current);
							}
						}
					} else {
						errors.add("Missing type");
					}
				} else {
					errors.add("Invalid node "+getNodePath(l_current)+" inside 'Elements' definition");
				}
			}
			l_current=l_current.getNextSibling();
		}

	}
	@SuppressWarnings("rawtypes")
	public void processNodeDef(Element p_element,Node p_parent) throws Exception{		
		Node l_currentDef=p_parent.getFirstChild();
		NamedNodeMap l_properties=p_parent.getAttributes();
		Node l_node;
		
		for(int l_cnt=0;l_cnt<l_properties.getLength();l_cnt++){
			l_node=l_properties.item(l_cnt);
			if(!l_node.getNodeName().equals("type") && !l_node.getNodeName().equals("file")){
					setProperty(p_element,l_node.getNodeName(),l_node.getNodeValue());
			}
		}
		
		while(l_currentDef!=null){
			if(l_currentDef.getNodeType()==javax.xml.soap.Node.ELEMENT_NODE){
				if(l_currentDef.getNodeName().equals("elements")){					
					processElements(p_element,l_currentDef);
					
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
			l_object=createObjectFromName(l_type);
			if(l_object instanceof Page){
				l_page=(Page)l_object;
		
				processNodeDef(l_page,p_rootNode);
			} else {
				errors.add("Class '"+l_type+"' doesn't discent from Page");
				return null;
			}
		} 
		return l_page;
	}
	public Page parseUiXml(String p_fileName,Logger p_logger) throws Exception{
		logger=p_logger;
		DocumentBuilderFactory l_factory=DocumentBuilderFactory.newInstance();
		DocumentBuilder l_builder=l_factory.newDocumentBuilder();
		Document l_doc=l_builder.parse(new File(application.getRealPath(p_fileName)));
		NodeList l_nl=l_doc.getChildNodes();
		Node l_rootNode=l_nl.item(0);
		if(l_rootNode.getNodeName().equals("page")){
			return processRootNode(l_rootNode);
		} else {
			errors.add("'page' tag expected");
			return null;
		}
		
	}

}
