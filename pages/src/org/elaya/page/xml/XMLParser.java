package org.elaya.page.xml;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;
import java.util.Objects;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.elaya.page.ElementVariant;
import org.elaya.page.Errors;
import org.elaya.page.Errors.NormalizeClassNameException;
import org.elaya.page.Errors.ReplaceVarException;
import org.elaya.page.Errors.SettingAttributeException;
import org.elaya.page.Errors.ValueNotFound;
import org.elaya.page.data.Dynamic.DynamicException;
import org.elaya.page.data.DynamicMethod.MethodNotFound;
import org.elaya.page.data.DynamicObject;
import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;


public abstract class XMLParser {
	
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
		@Override
		public String toString()
		{
			return super.toString()+" while parsing '"+exceptionFileName+"'";
		}
	}
	
	private String fileName;
	private HashMap<String,XMLConfig> configs=new HashMap<>();	
	private Map<String,Object> nameIndex;
	private LinkedList<Initializer> initializers=new LinkedList<>();
	private LinkedList<Map<String,String>> values=new LinkedList<>();
	
	
	public XMLParser(Map<String,Object> pnameIndex) {
		nameIndex=pnameIndex;
	}
		
	public XMLParser()
	{
		this(new HashMap<String,Object>());
	}

	protected String replaceVariables(String pstring) throws ReplaceVarException
	{
		int pos=0;
		int newPos;
		StringBuilder returnValue=new StringBuilder();
		String varName;
		Object value;
		String string=pstring==null?"":pstring;
		try{
			while(true){
				newPos=string.indexOf("$${",pos);
				if(newPos==-1){
					returnValue.append(string.substring(pos));
					break;
				}
				returnValue.append(string.substring(pos,newPos));
				pos=string.indexOf('}',newPos);
				if(pos==-1){
					throw new ReplaceVarException("Missing '}' after position "+newPos); 				
				}
				varName=string.substring(newPos+3,pos);	
				value=getValue(varName);				
				if(value != null){
					returnValue.append(value.toString());
				}			
				pos++;
			}
		}catch(Exception e){
			throw new ReplaceVarException("In String "+pstring,e);
		}
		return returnValue.toString();
	}	
	
	
	private void addValues(Map<String,String> pvalues)
	{
		values.addFirst(pvalues);
	}
	
	private void popValues()
	{
		values.pop();
	}
	
	private String getValue(String name) throws ValueNotFound
	{
		Iterator<Map<String,String>> iter=values.descendingIterator();
		Map<String,String> data;
		while(iter.hasNext()){
			data=iter.next();
			if(data.containsKey(name)){
				return data.get(name);
			}
		}
		throw new Errors.ValueNotFound(name);
	}
	public void addInitializer(Initializer pinitializer){
		initializers.add(pinitializer);
	}
		public Map<String,Object> getNameIndex(){ return nameIndex;}
	
	protected String getFileName(){
		return fileName;
	}
	protected String getAttributeValue(Node pnode,String pname) throws  ReplaceVarException
	{
		Node valueNode=pnode.getAttributes().getNamedItem(pname);
		if(valueNode !=null){
			return replaceVariables( valueNode.getNodeValue());
		} else {
			return null;
		}		
	}
			
	protected void addError(String perror,Node node) throws XMLLoadException 
	{
		
		throw new XMLLoadException(perror,node);
	}

	protected void addConfig(String pname,XMLConfig pconfig)
	{
		configs.put(pname,pconfig);
	}


	private XMLConfig getConfig(Node node){
		String nodeName=node.getNodeName();
		if(! configs.containsKey(nodeName)){
			new XMLLoadException("Unknown node type :'"+nodeName+"'",node);
		}		
		return configs.get(nodeName);		
	}
	
	private Node getNextFilledNode(Node node)
	{
		Node nodeIter=node;
		while(nodeIter!= null && nodeIter.getNodeType() != Node.ELEMENT_NODE ){
			if(nodeIter.getNodeType() ==Node.CDATA_SECTION_NODE||nodeIter.getNodeType()==Node.TEXT_NODE){
				String value=nodeIter.getTextContent();
				if(!value.trim().isEmpty()){
					break;
				}
			}
			nodeIter=nodeIter.getNextSibling();
		}
		return nodeIter;
	}
	//TODO: handle CDATA=>Error when trimmed
	
	private Object parseParameterValueNode(Object pparent,Node pnode) throws XMLLoadException, ClassNotFoundException, InstantiationException, IllegalAccessException, InvocationTargetException, NoSuchMethodException, MethodNotFound, ParserConfigurationException, SAXException, IOException, SettingAttributeException, NormalizeClassNameException 
	{
		Node element=getNextFilledNode(pnode.getFirstChild());		
		if(element==null){
			return "";
		} else{
			Node next=getNextFilledNode(element.getNextSibling());
			if(next != null){
				throw new XMLLoadException("Value should contain only one node, extra node="+next.getNodeName()+", type="+next.getNodeType()+", content="+next.getTextContent(),next);					
			} else if(element.getNodeType() != Node.ELEMENT_NODE){
				return element.getTextContent();
			} else {
				return parseElement(null,element);
			}
		}
	}
	
	private void parseParameter(Object parent,Node child) throws XMLLoadException, ReplaceVarException  
	{
		if(!"value".equals(child.getNodeName())){
			throw new XMLLoadException("'parameter' node expected, but "+child.getNodeName()+" found",child);
		} else {
			String name=getAttributeValue(child,"name");
			if(name==null){
				throw new XMLLoadException("Name attribute not found",child);
			}
			try{
				Object value=parseParameterValueNode(parent,child);
				DynamicObject.put(parent, name, value);
			}	catch(Exception e){
					throw new XMLLoadException(e,child);
			}
		}
	}
	private void parseParameters(Object pparent,Node pnode) throws ClassNotFoundException, InstantiationException, IllegalAccessException, InvocationTargetException, NoSuchMethodException, XMLLoadException, MethodNotFound, ParserConfigurationException, SAXException, IOException, SettingAttributeException, NormalizeClassNameException,  ReplaceVarException {
		Node child=pnode.getFirstChild();
		while(child!=null){
			if(child.getNodeType()==Node.ELEMENT_NODE){
				parseParameter(pparent,child);
			}
			child=child.getNextSibling();
		}
	}


	private void parseChildNodes(Object pparent,Node pnode) throws ClassNotFoundException, InstantiationException, IllegalAccessException, InvocationTargetException, NoSuchMethodException, XMLLoadException, MethodNotFound, ParserConfigurationException, SAXException, IOException, SettingAttributeException, NormalizeClassNameException,  ReplaceVarException 
	{
		Node child=pnode.getFirstChild();
		while(child!=null){
			if(child.getNodeType()==Node.ELEMENT_NODE){
				if("values".equals(child.getNodeName())){
					parseParameters(pparent,child);
				} else {
					parseElement(pparent,child);					
				}				
			}
			child=child.getNextSibling();
		}
	}
	
	private void setAttribute(Object object,Node attribute,ElementVariant variant) throws XMLLoadException, SettingAttributeException
	{
		String attributeName;
		attributeName=attribute.getNodeName();
		if(variant == null || !variant.containsParameter(attributeName)){
			if(!"name".equals(attributeName) && !"ref".equals(attributeName) && !"class".equals(attributeName) && !"file".equals(attributeName)){
				try{
					DynamicObject.put(object,attributeName,replaceVariables(attribute.getNodeValue()));
				} catch(Exception e){
					throw new Errors.SettingAttributeException(attributeName,object,e);
				}
			}
		}
		
	}
	
	private void parseAttributes(Object pobject,Node pnode,ElementVariant variant) throws XMLLoadException, SettingAttributeException 
	{
		Objects.requireNonNull(pobject);
		NamedNodeMap attributes=pnode.getAttributes();		
		for(int cnt=0;cnt<attributes.getLength();cnt++){			
			setAttribute(pobject,attributes.item(cnt),variant);
		}	
	}
	
	private void parseNamedObject(Object pobject,Node pnode) throws DynamicException, XMLLoadException,  ReplaceVarException  
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
	
	private Object createByClass(Node pnode,Class<?> pdefault) throws XMLLoadException, InstantiationException, IllegalAccessException, DynamicException, NormalizeClassNameException, InvocationTargetException, NoSuchMethodException, ClassNotFoundException,  ReplaceVarException  
	{
		String className=getAttributeValue(pnode,"class");
		Object object;
		if(className == null){
			if(pdefault ==null){
				throw new XMLLoadException("Node "+pnode.getNodeName()+" requires 'class' property ",pnode);		
			} else {
				object=pdefault.newInstance();			
			}
		} else {
			String normalizedClass=normalizeClassName(className);
			if(normalizedClass==null){
				throw new XMLLoadException("Alias "+className+" not found",pnode);				
			}
			object=DynamicObject.createObjectFromName(normalizedClass);
			if(object==null){
				return null;
			}

		}
		parseNamedObject(object,pnode);
		return object;
	}
	
	private Object createObjectByNode(Object parent,Node node,XMLConfig info) throws XMLLoadException, SettingAttributeException, ClassNotFoundException, InstantiationException, IllegalAccessException, InvocationTargetException, NoSuchMethodException, MethodNotFound, ParserConfigurationException, SAXException, IOException, NormalizeClassNameException, DynamicException,  ReplaceVarException  
	{
		Object object;
		String fileNameAttr=getAttributeValue(node,"file");
		if(fileNameAttr != null){
			XMLParser parser=createParser();
			object=parser.parse(fileNameAttr);
		} else {			
			String ref=getAttributeValue(node,"ref");
			if(ref != null){

				if(nameIndex.containsKey(ref)){
					return nameIndex.get(ref);

				} else {
					throw new XMLLoadException("Referenced item '"+ref+"' does not exists",node);					
				}
			} else if(info.getBaseClass() != null){
				object=createByClass(node,info.getDefaultClass());
				if(object==null){
					return null;
				}
			} else {
				if(info.getDefaultClass() ==null){
					throw new XMLLoadException("Internal error. Class name is empty, but defaultClass not set in config. Node name="+node.getNodeName(),node);					
				} else {
					object=info.getDefaultClass().newInstance();
					parseNamedObject(object,node);
				}	
			}	
		}
		
		if(object != null && parent != null){
			String methodName=info.getDefaultSetMethod();

			try{
				DynamicObject.call(parent, methodName,new Class<?>[]{info.getBaseClass()},new Object[]{object});
			} catch(NoSuchMethodException e){
				throw new XMLLoadException("Method "+methodName + " in object "+parent.getClass().getName()+" doesn't exists",e,node);
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
	
	protected ElementVariant getVariant(Node node) throws  XMLLoadException,  ReplaceVarException {
		return null;
	}
	
	
	Object parseElement(Object pparent,Node pnode) throws XMLLoadException 
	{
		Node node=pnode;
		ElementVariant variant=null;
		try{
			Object object;
			XMLConfig info=getConfig(node);
			if(info==null){
				throw new XMLLoadException("Invalid element '"+node.getNodeName()+"'",node);
			}	
			if(pparent==null && info.getNeedParent()){
				addError("Element "+node.getNodeName()+" needs a parent but=null",node);
			}
			
			if(info.getCustom()){
				return parseCustom(pparent,node);
			} 			
		
			variant=getVariant(node);
			if(variant !=null){
				
				addValues(variant.getDataFromNode(node));
				object=parseElement(pparent,variant.getContent());
			} else {
				object=createObjectByNode(pparent,node,info);
			}
			if(object!=null){
				setupObject(object);
				
				parseAttributes(object,node,variant);
				parseChildNodes(object,node);
			}
			if(variant !=null){
				popValues();
			}
			return object;
		} catch(XMLLoadException e){
			throw e;
		}catch(Exception e){
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
			return parseElement(null,rootNode);

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
	protected abstract XMLParser createParser();
	protected abstract void addConfig();
	protected abstract String normalizeClassName(String pname) throws  NormalizeClassNameException ;
	protected abstract String getName(Object pobject);
}