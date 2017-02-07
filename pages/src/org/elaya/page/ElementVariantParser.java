package org.elaya.page;

import java.io.FileNotFoundException;
import java.io.InputStream;
import org.elaya.page.application.Application;
import org.elaya.page.xml.XMLAppParser;
import org.elaya.page.xml.XMLConfig;
import org.elaya.page.xml.XMLCustomConfig;
import org.w3c.dom.Node;

public class ElementVariantParser extends XMLAppParser{

	public ElementVariantParser(Application papplication) {
		super(papplication);
	}
	
	/**
	 * Get the next element node ignore other types of node.
	 * 
	 * @param node Start point of iteration
	 * @return The first element node starting with "node" or null when nothing found.
	 */
	
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
	
	/**
	 * The definition of the element variant's xml is inside the "content" tag.
	 * This is parsed in a custom routine.
	 */
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
		} else {
			throw new XMLLoadException("Parent is not a ElementVariant,but a '"+parent.getClass().getName()+"'",node);
		}
		return node;
	}
	
	/**
	 * Aliasses used in ElementVariant files must be part of element namespace
	 * 
	 */

	@Override
	public String getAliasNamespace() {
		return "element";
	}

	@Override
	protected InputStream openFile(String fileName) throws FileNotFoundException {
		return getApplication().getConfigStream(fileName);		
	}

    /**
     * Configure parser
     */

	@Override
	protected void setupConfig() {
		addConfig("variants",new XMLConfig(ElementVariantList.class,ElementVariantList.class,null,null));
		addConfig("variant",new XMLConfig(ElementVariant.class,ElementVariant.class,"put",ElementVariantList.class));
		addConfig("content",new XMLCustomConfig("",ElementVariant.class));
		addConfig("parameter",new XMLConfig(ElementVariantParameter.class,ElementVariantParameter.class,"addParameter",ElementVariant.class));
	}

	@Override
	protected String getName(Object pobject) {
		return null;
	}


}
