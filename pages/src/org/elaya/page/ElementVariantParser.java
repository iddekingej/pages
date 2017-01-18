package org.elaya.page;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Map;

import org.elaya.page.application.Application;
import org.elaya.page.xml.XmlAppParser;
import org.elaya.page.xml.XMLConfig;
import org.elaya.page.xml.XMLCustomConfig;
import org.elaya.page.xml.XMLParser;
import org.w3c.dom.Node;

public class ElementVariantParser extends XmlAppParser{

	public ElementVariantParser(Application papplication) {
		super(papplication);
	}

	public ElementVariantParser(Application papplication,Map<String, Object> pnameIndex) {
		super(papplication,pnameIndex);		
	}
	
	
	private Node getNextElementNode(Node node)
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
	
	@Override
	public Object parseCustom(Object parent,Node node) throws XMLLoadException
	{
		if(parent instanceof  ElementVariant){
			Node content=getNextElementNode(node.getFirstChild());
			if(content==null){
				throw new XMLLoadException("'content' node should contain xml nodes",node);
			}
			if(getNextElementNode(content.getNextSibling()) != null){
				throw new XMLLoadException("There should be only one xml top node inside content",node);
			}
			((ElementVariant)parent).setContent(content);
		}
		return node;
	}
	
	@Override
	protected XMLParser createParser() {
		return new ElementVariantParser(getApplication(),getNameIndex());
	}
	
	@Override
	public String getAliasNamespace() {
		return null;
	}

	@Override
	protected InputStream openFile(String fileName) throws FileNotFoundException {
		return getApplication().getConfigStream(fileName);		
	}



	@Override
	protected void setupConfig() {
		addConfig("variants",new XMLConfig(ElementVariantList.class,ElementVariantList.class,null,null));
		addConfig("variant",new XMLConfig(ElementVariant.class,ElementVariant.class,"addVariant",ElementVariantList.class));
		addConfig("content",new XMLCustomConfig("",ElementVariant.class));
		addConfig("parameter",new XMLConfig(ElementVariantParameter.class,ElementVariantParameter.class,"addParameter",ElementVariant.class));
	}

	@Override
	protected String getName(Object pobject) {
		return null;
	}


}
