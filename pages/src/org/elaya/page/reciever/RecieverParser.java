package org.elaya.page.reciever;

import java.io.InputStream;
import java.util.LinkedList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.elaya.page.AliasData;
import org.elaya.page.application.Application;
import org.elaya.page.data.DynamicMethod;
import org.elaya.page.data.DynamicObject;
import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;

public class RecieverParser {
	private LinkedList<String> errors=new LinkedList<>();
	private Application application;

	public RecieverParser(Application papplication) {		
		application=papplication;		
	}
	
	public List<String> getErrors()
	{
		return errors;
	}

	private String getAttributeValue(Node pnode,String pname)
	{
		Node valueNode=pnode.getAttributes().getNamedItem(pname);
		if(valueNode !=null){
			return valueNode.getNodeValue();
		} else {
			return null;
		}
	}
		
	private String normelizeClassName(String pclassName) throws Exception
	{
		
		if(pclassName.startsWith("@")){			
			String className=application.getAlias(pclassName.substring(1),AliasData.ALIAS_RECIEVER);
			if(className!=null){
				return className;
			}
		}
		return pclassName;
	}
	
	private void setProperties(DynamicMethod pobject,Node pnode) throws  Exception
	{
		NamedNodeMap attributes=pnode.getAttributes();
		Node node;
		for(int cnt=0;cnt<attributes.getLength();cnt++){
			node=attributes.item(cnt);
			if(!"class".equals(node.getNodeName())){
				pobject.put(node.getNodeName(),node.getNodeValue());
			}
		}
		
	}
	
	@SuppressWarnings("unchecked")
	private <T> T makeObject(Node pnode,Class<T> pclass) throws  Exception
	{
		String className=getAttributeValue(pnode,"class");
		T returnValue=null;
		if(className==null){
			errors.add("Missing 'class' property");
		} else {
			Object object=DynamicObject.createObjectFromName(normelizeClassName(className));
			if(object != null){
				if(pclass.isAssignableFrom(object.getClass())){
					returnValue=(T) object;
				} else {
					errors.add("Wrong class name class "+className+" is not descended of "+pclass.getName());
				}
			}
		}
		if(returnValue != null && returnValue instanceof DynamicMethod){
			setProperties((DynamicMethod)returnValue,pnode);
		} else {
			returnValue =null;
		}
		return returnValue;
	}
	
	private void handleParameterNode(Reciever<?> preciever,Node pnode) throws  Exception
	{
		Parameter parameter=makeObject(pnode,Parameter.class);
		preciever.addParameter(parameter);
		//TODO handle subnodes=>validation?
	}
	
	private Reciever<?> handleRootNode(Node pnode) throws Exception
	{
		if(pnode.getNodeName() != "reciever"){
			errors.add("<reciever> expected");
			return null;
		}
		Reciever<?> reciever=makeObject(pnode,Reciever.class);
		if(reciever==null){
			return null;
		}
		reciever.setApplication(application);
		Node node=pnode.getFirstChild();
		while(node != null){
			if(node.getNodeType()==Node.ELEMENT_NODE){
				if(node.getNodeName()=="parameter"){
					handleParameterNode(reciever,node);
				} else {
					errors.add("Invalid node, 'parameter' expected, but '"+node.getNodeName()+"' found");
				}
			}
			node=node.getNextSibling();
		}
		return reciever;
	}
	
	public Reciever<?> parseXml(String pfileName) throws  Exception
	{
		DocumentBuilderFactory factory=DocumentBuilderFactory.newInstance();
		DocumentBuilder builder=factory.newDocumentBuilder();
		
		InputStream input= application.getConfigStream(pfileName);
		
		Document doc=builder.parse(input);

		Node node=doc.getFirstChild();
		node.normalize();
		return handleRootNode(node);	
	}
}
