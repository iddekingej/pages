package org.elaya.page.xml;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.elaya.page.Errors;
import org.elaya.page.Errors.NormalizeClassNameException;
import org.elaya.page.Errors.SettingAttributeException;
import org.elaya.page.Errors.XmlLoadError;
import org.elaya.page.data.DynamicObject;
import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;


public abstract class XmlParser {
	
	class ParseError extends Exception{

		private static final long serialVersionUID = 5079459731617602718L;
		public  ParseError(String pmessage){ super(pmessage);}
	}
	
	private String fileName;
	private HashMap<String,XmlConfig> configs=new HashMap<>();
	private LinkedList<String> errors=new LinkedList<>();
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
		
	public List<String> getErrors(){ return errors;}
	
	protected void addError(String perror,Node pnode) throws XmlLoadError
	{
		
		throw new Errors.XmlLoadError(perror);
	}

	protected void addConfig(String pname,XmlConfig pconfig)
	{
		configs.put(pname,pconfig);
	}

	private Object parseParameterValueNode(Object pparent,Node pnode) throws Exception
	{
		Node first=pnode.getFirstChild();
		if(first.getNextSibling() != null){
			addError("Parameter should contain definition of one",pnode);
			return null;
		} else if(first.getNodeType() != Node.ELEMENT_NODE){
			return first.getTextContent();
		} else {
			return parseNode(pparent,pnode);
		}
	}
	
	private void parseParameters(Object pparent,Node pnode) throws  Exception{
		Node child=pnode.getFirstChild();
		while(child!=null){
			if(child.getNodeType()==Node.ELEMENT_NODE){
				if(!"parameter".equals(child.getNodeName())){
					addError("'parameter' node expected, but "+child.getNodeName()+" found",child);
				} else {
					String name=getAttributeValue(child,"name");
					if(name==null){
						addError("Name attribute not found",child);
						continue;
					}
					Object value;
					if(child.getChildNodes().getLength()==0){
						value="";
					} else {
						value=parseParameterValueNode(pparent,child);
					}
					DynamicObject.put(pparent, name, value);
				}
			}
			child=child.getNextSibling();
		}
	}
	
	private void parseElement(Object parent,Node child) throws Exception
	{
		Object object=parseNode(parent,child);
		if(object != null){
			if(! configs.containsKey(child.getNodeName())){
				addError("Unkown node type :'"+child.getNodeName()+"'",child);
				return;
			}
			
			XmlConfig info=configs.get(child.getNodeName());
			String methodName=info.getDefaultSetMethod();
			try{							
				Method method=parent.getClass().getMethod(methodName, new Class<?>[]{info.getBaseClass()});
				method.invoke(parent,object);
			} catch(NoSuchMethodException e){
				addError("Method "+methodName + " in object "+parent.getClass().getName()+" doesn't exists",child);
			}
			parseChildNodes(object,child);
		}
	}
	private void parseChildNodes(Object pparent,Node pnode) throws  Exception
	{
		Node child=pnode.getFirstChild();
		while(child!=null){
			if("parameters".equals(child.getNodeName())){
				parseParameters(pparent,child);
			} else {
			
				if(child.getNodeType()==Node.ELEMENT_NODE){

						parseElement(pparent,child);
					
				}
			}
			child=child.getNextSibling();
		}
	}
	
	private void setAttribute(Object object,Node attribute,Node node) throws XmlLoadError, SettingAttributeException
	{
		String attributeName;
		attributeName=attribute.getNodeName();
		if(!"name".equals(attributeName) && !"ref".equals(attributeName) && !"class".equals(attributeName) && !"file".equals(attributeName)){
			if(!DynamicObject.containsKey(object,attribute.getNodeName())){
				addError("Object of type "+object.getClass().getName()+" doesn't contain attribute "+attributeName,node);
			} else {
				try{
					DynamicObject.put(object,attributeName,attribute.getNodeValue());
				} catch(Exception e){
					throw new Errors.SettingAttributeException(attributeName,object,e);
				}
			}
		}
	}
	
	private void parseAttributes(Object pobject,Node pnode) throws XmlLoadError, SettingAttributeException 
	{
		Objects.requireNonNull(pobject);
		NamedNodeMap map=pnode.getAttributes();
		Node attribute;
		for(int cnt=0;cnt<map.getLength();cnt++){
			attribute=map.item(cnt);
			setAttribute(pobject,attribute,pnode);
		}	
	}
	
	void parseNamedObject(Object pobject,Node pnode) throws Exception
	{
		String name=getAttributeValue(pnode,"name");
		if(name != null){
			DynamicObject.put(pobject,"name",name);
			String refName=getName(pobject);
			if(nameIndex.containsKey(refName)){
				addError("An object with name "+refName+" allready exists",pnode);
			} else {
				nameIndex.put(refName,pobject);
			}
		}
	}
	
	private Object createByClass(Node pnode,Class<?> pdefault) throws Exception
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
	
	private Object createObjectByNode(Node node,Class<?> defaultValue,Class<?> base) throws Exception
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
		} else if(base != null){
			object=createByClass(node,defaultValue);
			if(object==null){
				return null;
			}
		} else {
			if(defaultValue ==null){
				addError("Internal error. Not a dynamic object, but defaultClass not set in config. Node name="+node.getNodeName(),node);
				return null;
			} else {
				object=defaultValue.newInstance();
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
	
	Object parseNode(Object pparent,Node pnode) throws  Exception
	{
		if(! configs.containsKey(pnode.getNodeName())){
			addError("Unkown node type :'"+pnode.getNodeName()+"'",pnode);
			return null;
		}
		XmlConfig info=configs.get(pnode.getNodeName());
		Class<?> base=info.getBaseClass();
		Class<?> defaultValue=info.getDefaultClass();
		Object object;
		String fileNameAttr=getAttributeValue(pnode,"file");
		if(pparent==null && info.getNeedParent()){
			addError("Element "+pnode.getNodeName()+" needs a parent but=null",pnode);
		}
		if(info.getCustom()){
			return parseCustom(pparent,pnode);
		}
		if(fileNameAttr != null){
			XmlParser parser=createParser();
			object=parser.parse(fileNameAttr);
			if(object==null){
				return null;
			}
		} else {
			object=createObjectByNode(pnode,defaultValue,base);
			if(object==null){
				return null;
			}
		}
		setupObject(object);
		parseAttributes(object,pnode);
		return object;
	}
	
	public Object parse(String pfileName) throws Exception{
		fileName=pfileName;
		addConfig();
		DocumentBuilderFactory factory=DocumentBuilderFactory.newInstance();
		DocumentBuilder builder=factory.newDocumentBuilder();
		InputStream stream=openFile(pfileName);
		if(stream ==null){
			throw new FileNotFoundException("pages/"+pfileName);
		}
		Document doc=builder.parse( stream);
		NodeList nl=doc.getChildNodes();
		Node rootNode=nl.item(0);
		rootNode.normalize();
		Object object=parseNode(null,rootNode);
		parseChildNodes(object,rootNode);
		return object;
	}

	protected void afterCreate(Object pparent) throws ClassNotFoundException, InstantiationException, IllegalAccessException, InvocationTargetException, NoSuchMethodException 
	{
		
	}
	protected Object parseCustom(Object parent,Node pnode) throws XmlLoadError
	{
		return null;
	}
	protected abstract InputStream openFile(String pfileName) throws FileNotFoundException;
	protected abstract XmlParser createParser();
	protected abstract void addConfig();
	protected abstract String normalizeClassName(String pname) throws  NormalizeClassNameException ;
	protected abstract String getName(Object pobject);
}