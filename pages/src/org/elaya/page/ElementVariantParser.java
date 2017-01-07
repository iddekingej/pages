package org.elaya.page;

import java.io.FileNotFoundException;
import java.io.InputStream;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.elaya.page.application.Application;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class ElementVariantParser {
	public static  class VariantParserException extends Exception
	{
		private final transient Node node;
		private String fileName;
		private static final long serialVersionUID = -1607030494090125690L;
		
		public VariantParserException(String pmessage,Node pnode)
		{		
			super(pmessage);
			node=pnode;
		}
	
		public VariantParserException(Throwable error,Node pnode)
		{
			super("Parsing failed",error);
			node=pnode;
		}
		public String getFileName()
		{
			return fileName;
		}
		
		
		public void setFileName(String pfileName)
		{
			fileName=pfileName;
		}
		
		public Node getNode()
		{
			return node;
		}
		@Override
		public String toString()
		{
			return super.toString()+"  in file '"+fileName+"'";
		}
	}
	
	private String fileName;
	private Application application;
	
	
	public ElementVariantParser(Application papplication)
	{
		application=papplication;
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
	
	Node getNextElementNode(Node node)
	{
		Node iter=node;
		while(iter != null){
			if(iter.getNodeType()==Node.ELEMENT_NODE){
				break;
			}
			iter=iter.getNextSibling();
		}
		return iter;
	}
	
	void parseVariant(ElementVariantList list,Node node) throws VariantParserException
	{
		String name=getAttributeValue(node,"name");
		if(name==null){
			throw new VariantParserException("Variant name missing",node);
		}
		Node child=getNextElementNode(node.getFirstChild());
		if(child==null){
			throw new VariantParserException("A xml node expected",node);
		}
		if(getNextElementNode(child.getNextSibling())!=null){
			throw new VariantParserException("Only one xml node expected",node);
		}		
		try{
			list.addElementVariant(name, child);
		} catch(ElementVariantList.DuplicateElementVariant e){
			throw new VariantParserException(e,node);
		}
	}
	
	private ElementVariantList parseRoot(Node rootNode,ElementVariantList list) throws VariantParserException
	{
		if(rootNode.getNodeName()!="variants"){
			throw new VariantParserException("'variant' node expected",rootNode);
		}
		Node node=rootNode.getFirstChild();
		while(node !=null){
			if(node.getNodeType()==Node.ELEMENT_NODE){
				if("variant".equals(node.getNodeName())){
					parseVariant(list,node);
				} else {
					throw new VariantParserException("'variant' node expected",node);
				}
			}
			node=node.getNextSibling();
		}
		return list;
	}
	public ElementVariantList parse(String pfileName,ElementVariantList list) throws VariantParserException{
			try{
				fileName=pfileName;

				DocumentBuilderFactory factory=DocumentBuilderFactory.newInstance();
				DocumentBuilder builder=factory.newDocumentBuilder();
				InputStream stream=application.getConfigStream(pfileName);
				if(stream ==null){
					throw new FileNotFoundException(pfileName);
				}
				Document doc=builder.parse( stream);
				NodeList nl=doc.getChildNodes();
				Node rootNode=nl.item(0);
				rootNode.normalize();
				return parseRoot(rootNode,list);
			}
			catch(VariantParserException e)
			{
				e.setFileName(fileName);
				throw e;
			}
			catch(Exception e)
			{
				throw new VariantParserException(e,null);
			}
	}
}
