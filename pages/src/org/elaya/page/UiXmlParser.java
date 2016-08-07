package org.elaya.page;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.LinkedList;

import javax.xml.parsers.DocumentBuilder; 
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.slf4j.Logger;
import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;



public class UiXmlParser {

	Application application;
	LinkedList<String> errors=new LinkedList<String>();
	Logger logger;
	
	public LinkedList<String> getErrors()
	{
		return errors;
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
			l_constructor=l_class.getConstructor(l_types);
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
	
	public void processElement(ElementSubList p_list,Node p_parent) throws Exception{
		String l_type=getTypeValue(p_parent);
		if(l_type != null){
			Object l_object=createElementFromName(l_type);
			if(l_object!=null){
				p_list.addElement(l_object);
			}
		}
	}
	@SuppressWarnings("rawtypes")
	public void ProcessElements(ElementSubList p_list,Node p_parent) throws Exception{
		Node l_elements=findNodeByTag(p_parent,"elements");
		if(l_elements==null) return;
		Node l_current=l_elements.getFirstChild();
		while(l_current != null){
			if(l_current.getNodeName().equals("element")){
				logger.debug("Found element");
				processElement(p_list,l_current);
			}
			l_current=l_current.getNextSibling();
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
				ProcessElements(l_page,p_rootNode);
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
