package org.elaya.page.xml;

import java.io.FileNotFoundException;
import java.io.InputStream;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public abstract class XMLParserBase<T> {
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

	public String getFileName()
	{
		return fileName;
	}
	
	protected abstract T parseRootNode(Node node) throws XMLLoadException;
	protected abstract InputStream openFile(String fileName) throws FileNotFoundException;

	protected Object subParse(String pfileName) throws XMLLoadException
	{
		String prvFileName=fileName;
		fileName=pfileName;
		Node node=loadFromFile(pfileName);
		Object object=parseRootNode(node);
		fileName=prvFileName;
		return object;
	}
	
	protected Node loadFromFile(String pfileName) throws XMLLoadException 
	{
		try{
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
			return rootNode;
		}catch(Exception e){
			throw new XMLLoadException("Parsing "+pfileName+" failed",e,null);
		}
	}
	
	public T parse(String pfileName) throws XMLLoadException {
		try{
			Node node=loadFromFile(pfileName);
			return parseRootNode(node);

		}catch(XMLLoadException e){
			e.setFileName(pfileName);
			throw e;
		}catch(Exception e){
			throw new XMLLoadException("Parsing "+pfileName+" failed",e,null);
		}
	}
	
	
	
}
