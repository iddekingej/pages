package org.elaya.page.reciever;

import java.io.File;
import java.util.HashMap;
import java.util.LinkedList;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.elaya.page.Application;
import org.elaya.page.data.DynamicMethod;
import org.elaya.page.data.DynamicObject;
import org.slf4j.Logger;
import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;

public class RecieverParser {
	private Logger logger;
	private LinkedList<String> errors=new LinkedList<String>();
	private Application application;
	private HashMap<String,String> aliasses;

	public RecieverParser(Logger p_logger,Application p_application,HashMap<String,String> p_aliasses) {
		logger=p_logger;
		application=p_application;
		aliasses=p_aliasses;
	}
	
	public LinkedList<String> getErrors()
	{
		return errors;
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
	
	
	private String normelizeClassName(String p_className)
	{
		
		if(p_className.startsWith("@")){			
			String l_className=aliasses.get(p_className.substring(1));
			if(l_className!=null){
				return l_className;
			}
			
		}
		return p_className;
	}
	
	


	private void setProperties(DynamicMethod p_object,Node p_node) throws DOMException, Exception
	{
		NamedNodeMap l_attributes=p_node.getAttributes();
		Node l_node;
		for(int l_cnt=0;l_cnt<l_attributes.getLength();l_cnt++){
			l_node=l_attributes.item(l_cnt);
			if(!l_node.getNodeName().equals("class") ){
				p_object.put(l_node.getNodeName(),l_node.getNodeValue());
			}
		}
		
	}
	
	@SuppressWarnings("unchecked")
	private <T> T makeObject(Node p_node,Class<T> p_class) throws DOMException, Exception
	{
		String l_class=getAttributeValue(p_node,"class");
		T l_return=null;
		if(l_class==null){
			errors.add("Missing 'class' property");
		} else {
			Object l_object=DynamicObject.createObjectFromName(normelizeClassName(l_class),errors);
			if(l_object != null){
				if(p_class.isAssignableFrom(l_object.getClass())){
					l_return=(T) l_object;
				} else {
					errors.add("Wrong class name class "+l_class+" is not descended of "+p_class.getName());
				}
			}
		}
		if(l_return != null && l_return instanceof DynamicMethod){
			if(l_return != null)setProperties((DynamicMethod)l_return,p_node);
		} else {
			l_return =null;
		}
		return l_return;
	}
	
	private void handleParameterNode(Reciever<?> p_reciever,Node p_node) throws DOMException, Exception
	{
		Parameter l_parameter=makeObject(p_node,Parameter.class);
		p_reciever.addParameter(l_parameter);
		//TODO handle subnodes=>validation?
	}
	
	private Reciever<?> handleRootNode(Node p_node) throws DOMException, Exception
	{
		if(p_node.getNodeName() != "reciever"){
			errors.add("<reciever> expected");
			return null;
		}
		Reciever<?> l_reciever=makeObject(p_node,Reciever.class);
		if(l_reciever==null){
			return null;
		}
		l_reciever.setApplication(application);
		Node l_node=p_node.getFirstChild();
		while(l_node != null){
			if(l_node.getNodeType()==Node.ELEMENT_NODE){
				if(l_node.getNodeName()=="parameter"){
					handleParameterNode(l_reciever,l_node);
				} else {
					errors.add("Invalid node, 'parameter' expected, but '"+l_node.getNodeName()+"' found");
				}
			}
			l_node=l_node.getNextSibling();
		}
		return l_reciever;
	}
	
	public Reciever<?> parseXml(String p_fileName) throws DOMException, Exception
	{
		DocumentBuilderFactory l_factory=DocumentBuilderFactory.newInstance();
		DocumentBuilder l_builder=l_factory.newDocumentBuilder();
		Document l_doc=l_builder.parse(new File(application.getRealPath(p_fileName)));


		Node l_node=l_doc.getFirstChild();
		l_node.normalize();
		return handleRootNode(l_node);
		
	}
}
