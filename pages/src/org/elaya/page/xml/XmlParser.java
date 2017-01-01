package org.elaya.page.xml;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Objects;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.elaya.page.Errors;
import org.elaya.page.Errors.NormalizeClassNameException;
import org.elaya.page.Errors.SettingAttributeException;
import org.elaya.page.data.Dynamic.DynamicException;
import org.elaya.page.data.DynamicMethod.MethodNotFound;
import org.elaya.page.data.DynamicObject;
import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;


public abstract class XmlParser {
	
	public static class XMLLoadException extends Exception
	{
		private static final long serialVersionUID = 4450324776754322884L;
		private final transient Node node;
		private String exceptionFileName="";
		
		
		public XMLLoadException(String message,Throwable cause,Node exNode){
			super(message,cause);
			node=exNode;			
		}
		
		public XMLLoadException(String message,Node exNode){
			super(message);
			node=exNode;
		}
		
		public XMLLoadException(Throwable cause,Node exNode){
			super(cause);
			node=exNode;
		}
		
		public Node getNode(){ return node;}
		public String getExceptionFileName(){ return exceptionFileName;}
		public void setFileName(String xmlFileName){exceptionFileName=xmlFileName;}
	}
	
	private String fileName;
	private HashMap<String,XmlConfig> configs=new HashMap<>();	
	private Map<String,Object> nameIndex;
	private LinkedList<Initializer> initializers=new LinkedList<>();
		
	public XmlParser(Map<String,Object> pnameIndex) {
		nameIndex=pnameIndex;
	}
		
	public XmlParser()
	{
		this(new HashMap<String,Object>());
	}

	public void addInitializer(Initializer pinitializer){
		initializers.add(pinitializer);
	}
	

	
	public Map<String,Object> getNameIndex(){ return nameIndex;}
	
	protected String getFileName(){
		return fileName;
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
			
	protected void addError(String perror,Node node) throws XMLLoadException 
	{
		
		throw new XMLLoadException(perror,node);
	}

	protected void addConfig(String pname,XmlConfig pconfig)
	{
		configs.put(pname,pconfig);
	}


	private XmlConfig getConfig(Node node){
		String nodeName=node.getNodeName();
		if(! configs.containsKey(nodeName)){
			new XMLLoadException("Unknown node type :'"+nodeName+"'",node);
		}		
		return configs.get(nodeName);		
	}
	
	private Object parseParameterValueNode(Object pparent,Node pnode) throws XMLLoadException 
	{
		Node first=pnode.getFirstChild();
		if(first==null){
			return "";
		} else 	if(first.getNextSibling() != null){
			throw new XMLLoadException("Parameter should contain definition of one",pnode);					
		} else if(first.getNodeType() != Node.ELEMENT_NODE){
			return first.getTextContent();
		} else {
			return parseNode(pparent,pnode);
		}
	}
	
	private void parseParameter(Object parent,Node child) throws XMLLoadException  
	{
		if(!"parameter".equals(child.getNodeName())){
			throw new XMLLoadException("'parameter' node expected, but "+child.getNodeName()+" found",child);
		} else {
			String name=getAttributeValue(child,"name");
			if(name==null){
				throw new XMLLoadException("Name attribute not found",child);
			}
			Object value=parseParameterValueNode(parent,child);

			try{
				DynamicObject.put(parent, name, value);
			}catch(Exception e){
				throw new XMLLoadException(e,child);
			}			
		}
	}
	private void parseParameters(Object pparent,Node pnode) throws ClassNotFoundException, InstantiationException, IllegalAccessException, InvocationTargetException, NoSuchMethodException, XMLLoadException, MethodNotFound, ParserConfigurationException, SAXException, IOException, SettingAttributeException, NormalizeClassNameException {
		Node child=pnode.getFirstChild();
		while(child!=null){
			if(child.getNodeType()==Node.ELEMENT_NODE){
				parseParameter(pparent,child);
			}
			child=child.getNextSibling();
		}
	}

	private void parseElement(Object parent,Node child) throws ClassNotFoundException, InstantiationException, IllegalAccessException, InvocationTargetException, NoSuchMethodException, XMLLoadException, MethodNotFound, ParserConfigurationException, SAXException, IOException, SettingAttributeException, NormalizeClassNameException  
	{
		Object object=parseNode(parent,child);
		if(object != null){
			XmlConfig info=getConfig(child);
			String methodName=info.getDefaultSetMethod();

			try{
				DynamicObject.call(parent, methodName,new Class<?>[]{info.getBaseClass()},new Object[]{object});
			} catch(NoSuchMethodException e){
				throw new XMLLoadException("Method "+methodName + " in object "+parent.getClass().getName()+" doesn't exists",e,child);
			}
			parseChildNodes(object,child);
		}
	}
	private void parseChildNodes(Object pparent,Node pnode) throws ClassNotFoundException, InstantiationException, IllegalAccessException, InvocationTargetException, NoSuchMethodException, XMLLoadException, MethodNotFound, ParserConfigurationException, SAXException, IOException, SettingAttributeException, NormalizeClassNameException 
	{
		Node child=pnode.getFirstChild();
		while(child!=null){
			if(child.getNodeType()==Node.ELEMENT_NODE){
				if("parameters".equals(child.getNodeName())){
					parseParameters(pparent,child);
				} else {
					parseElement(pparent,child);					
				}				
			}
			child=child.getNextSibling();
		}
	}
	
	private void setAttribute(Object object,Node attribute) throws XMLLoadException, SettingAttributeException
	{
		String attributeName;
		attributeName=attribute.getNodeName();
		if(!"name".equals(attributeName) && !"ref".equals(attributeName) && !"class".equals(attributeName) && !"file".equals(attributeName)){
			try{
				DynamicObject.put(object,attributeName,attribute.getNodeValue());
			} catch(Exception e){
				throw new Errors.SettingAttributeException(attributeName,object,e);
			}
		}
		
	}
	
	private void parseAttributes(Object pobject,Node pnode) throws XMLLoadException, SettingAttributeException 
	{
		Objects.requireNonNull(pobject);
		NamedNodeMap map=pnode.getAttributes();
		Node attribute;
		for(int cnt=0;cnt<map.getLength();cnt++){
			attribute=map.item(cnt);
			setAttribute(pobject,attribute);
		}	
	}
	
	void parseNamedObject(Object pobject,Node pnode) throws DynamicException, XMLLoadException  
	{
		String name=getAttributeValue(pnode,"name");
		if(name != null){
			DynamicObject.put(pobject,"name",name);
			String refName=getName(pobject);
			if(refName != null){
				if(nameIndex.containsKey(refName)){
					addError("An object with name "+refName+" allready exists",pnode);
				} else {
					nameIndex.put(refName,pobject);
				}
			}
		}
	}
	
	private Object createByClass(Node pnode,Class<?> pdefault) throws XMLLoadException, InstantiationException, IllegalAccessException, DynamicException, NormalizeClassNameException, InvocationTargetException, NoSuchMethodException, ClassNotFoundException  
	{
		String className=getAttributeValue(pnode,"class");
		Object object;
		if(className == null){
			if(pdefault ==null){
				addError("Node requires 'class' property "+pnode.getNodeName(),pnode);
				return null;
			} else {
				object=pdefault.newInstance();
				parseNamedObject(object,pnode);
			}
		} else {
			String normalizedClass=normalizeClassName(className);
			if(normalizedClass==null){
				addError("Alias "+className+" not found",pnode);
				return null;
			}
			object=DynamicObject.createObjectFromName(normalizedClass);
			if(object==null){
				return null;
			}
			parseNamedObject(object,pnode);

		}
		return object;
	}
	
	private Object createObjectByNode(Node node,XmlConfig info) throws XMLLoadException, SettingAttributeException, ClassNotFoundException, InstantiationException, IllegalAccessException, InvocationTargetException, NoSuchMethodException, MethodNotFound, ParserConfigurationException, SAXException, IOException, NormalizeClassNameException, DynamicException  
	{
		Object object;
		String ref=getAttributeValue(node,"ref");
		if(ref != null){
			if(nameIndex.containsKey(ref)){
				object=nameIndex.get(ref);
				parseAttributes(object,node);
				parseChildNodes(object,node);
				return null;

			} else {
				addError("Reference to item "+ref+" does not exists",node);
				return null;
			}
		} else if(info.getBaseClass() != null){
			object=createByClass(node,info.getDefaultClass());
			if(object==null){
				return null;
			}
		} else {
			if(info.getDefaultClass() ==null){
				addError("Internal error. Not a dynamic object, but defaultClass not set in config. Node name="+node.getNodeName(),node);
				return null;
			} else {
				object=info.getDefaultClass().newInstance();
				parseNamedObject(object,node);
			}	
		}	
		return object;
	}
	
	protected void setupObject(Object object) throws ClassNotFoundException, InstantiationException, IllegalAccessException, InvocationTargetException, NoSuchMethodException{
		for(Initializer initializer:initializers){
			initializer.processObject(object);
		}
		afterCreate(object);		
	}
	
	Object parseNode(Object pparent,Node pnode) throws XMLLoadException 
	{
		try{
			XmlConfig info=getConfig(pnode);
			Object object;
			String fileNameAttr=getAttributeValue(pnode,"file");
			if(pparent==null && info.getNeedParent()){
				addError("Element "+pnode.getNodeName()+" needs a parent but=null",pnode);
			}
			
			if(info.getCustom()){
				return parseCustom(pparent,pnode);
			} else if(fileNameAttr != null){
				XmlParser parser=createParser();
				object=parser.parse(fileNameAttr);
			} else {
				object=createObjectByNode(pnode,info);
			}
			
			if(object!=null){
				setupObject(object);
				parseAttributes(object,pnode);
			}
			
			return object;
		} catch(Exception e){
			throw new XMLLoadException(e,pnode);
		}
	}
	
	@SuppressWarnings("unchecked")
	public <T>T parse(String fileName,Class<T> type) throws XMLLoadException
	{
		Object object=parse(fileName);
		if(type.isInstance(object)){
			return (T)object;
		}
		throw new XMLLoadException("Object is of type "+object.getClass().getName()+" and is not inherited from "+type.getName()+" as required, in xml file "+fileName,null);
	}
	
	private Object parse(String pfileName) throws XMLLoadException {
		try{
			fileName=pfileName;
			addConfig();
			DocumentBuilderFactory factory=DocumentBuilderFactory.newInstance();
			DocumentBuilder builder=factory.newDocumentBuilder();
			InputStream stream=openFile(pfileName);
			if(stream ==null){
				throw new FileNotFoundException(pfileName);
			}
			Document doc=builder.parse( stream);
			NodeList nl=doc.getChildNodes();
			Node rootNode=nl.item(0);
			rootNode.normalize();
			Object object=parseNode(null,rootNode);
			parseChildNodes(object,rootNode);
			return object;

		}catch(XMLLoadException e){
			e.setFileName(pfileName);
			throw e;
		}catch(Exception e){
			throw new XMLLoadException("Parsing "+pfileName+" failed",e,null);
		}
	}

	protected void afterCreate(Object parent) throws ClassNotFoundException, InstantiationException, IllegalAccessException, InvocationTargetException, NoSuchMethodException 
	{
		
	}
	protected Object parseCustom(Object parent,Node pnode) throws XMLLoadException
	{
		return null;
	}
	protected abstract InputStream openFile(String fileName) throws FileNotFoundException;
	protected abstract XmlParser createParser();
	protected abstract void addConfig();
	protected abstract String normalizeClassName(String pname) throws  NormalizeClassNameException ;
	protected abstract String getName(Object pobject);
}