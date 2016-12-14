package org.elaya.page.xml;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.elaya.page.Errors;
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
	private void parseChildNodes(Object pparent,Node pnode) throws  Exception
	{
		Node child=pnode.getFirstChild();
		String debug="";
		while(child!=null){
			if("parameters".equals(child.getNodeName())){
				parseParameters(pparent,child);
			} else {
				debug = debug+String.valueOf(child.getNodeType());
				if(child.getNodeType()==Node.ELEMENT_NODE){

					Object object=parseNode(pparent,child);
					if(object != null){
						if(! configs.containsKey(pnode.getNodeName())){
							addError("Unkown node type :'"+pnode.getNodeName()+"'",pnode);
							return;
						}
						
						XmlConfig info=configs.get(child.getNodeName());
						String methodName=info.getDefaultSetMethod();
						try{							
							Method method=pparent.getClass().getMethod(methodName, new Class<?>[]{info.getBaseClass()});
							method.invoke(pparent,object);
						} catch(NoSuchMethodException e){
							addError("Method "+methodName + " in object "+pparent.getClass().getName()+" doesn't exists",pnode);
						}
						parseChildNodes(object,child);
					}
				}
			}
			child=child.getNextSibling();
		}
	}
	private void parseAttributes(Object pobject,Node pnode) throws ParseError, XmlLoadError 
	{
		Objects.requireNonNull(pobject);
		NamedNodeMap map=pnode.getAttributes();
		Node attribute;
		String attributeName;
		for(int cnt=0;cnt<map.getLength();cnt++){
			attribute=map.item(cnt);
			attributeName=attribute.getNodeName();
			if(!"name".equals(attributeName) && !"ref".equals(attributeName) && !"class".equals(attributeName) && !"file".equals(attributeName)){
				if(!DynamicObject.containsKey(pobject,attribute.getNodeName())){
					addError("Object of type "+pobject.getClass().getName()+" doesn't contain attribute "+attributeName,pnode);
				} else {
					try{
						DynamicObject.put(pobject,attribute.getNodeName(),attribute.getNodeValue());
					} catch(Exception e){
						throw new ParseError("Error setting attribute:'"+attribute+"' at object "+pobject.getClass().getName()+" error:"+e.getMessage());
					}
				}
			}
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
			String ref=getAttributeValue(pnode,"ref");
			if(ref != null){
				if(nameIndex.containsKey(ref)){
					object=nameIndex.get(ref);
					parseAttributes(object,pnode);
					parseChildNodes(object,pnode);
					return null;

				} else {
					addError("Reference to item "+ref+" does not exists",pnode);
					return null;
				}
			} else if(base != null){
				object=createByClass(pnode,defaultValue);
				if(object==null){
					return null;
				}
			} else {
				if(defaultValue ==null){
					addError("Internal error. Not a dynamic object, but defaultClass not set in config. Node name="+pnode.getNodeName(),pnode);
					return null;
				} else {
					object=defaultValue.newInstance();
					parseNamedObject(object,pnode);
				}	
			}
		}
		for(Initializer initializer:initializers){
			initializer.processObject(object);
		}
		afterCreate(object);
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

	protected void afterCreate(Object pparent) throws Exception
	{
		
	}
	protected Object parseCustom(Object pparent,Node pnode) throws XmlLoadError
	{
		return null;
	}
	protected abstract InputStream openFile(String pfileName) throws FileNotFoundException;
	protected abstract XmlParser createParser();
	protected abstract void addConfig();
	protected abstract String normalizeClassName(String pname) throws Exception;
	protected abstract String getName(Object pobject);
}