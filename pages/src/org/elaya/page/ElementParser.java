package org.elaya.page;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.LinkedList;
import org.elaya.page.Errors.ReplaceVarException;
import org.elaya.page.application.AliasNamespace;
import org.elaya.page.data.DataLayer;
import org.elaya.page.data.XMLDataLayerParser;
import org.elaya.page.widget.Element;
import org.elaya.page.widget.Page;
import org.elaya.page.widget.jsplug.JSPlug;
import org.elaya.page.widget.quickform.OptionItem;
import org.elaya.page.xml.XMLAppParser;
import org.elaya.page.xml.XMLConfig;
import org.elaya.page.xml.XMLCustomConfig;
import org.w3c.dom.Node;
/**
 * Parses a page XML definition to objects
 *
 */
public class ElementParser extends XMLAppParser {
	
	/**
	 * Getting stream to input file. This can be an internal (inside jar)
	 * or an external file
	 */
	@Override
	protected InputStream openFile(String pfileName) throws FileNotFoundException {		
		return getApplication().getConfigStream(pfileName);		
	}
 

	/**
	 * Handle \<option\> node inside an \<options\> block
	 * An \<options\> block is a list of (value,text) pair.
	 *  
	 * @param child   the Option node
	 * @param list	OptionItem (value,text) pair is added to this list
	 */
	
	private void processOption(Node child,LinkedList<OptionItem> list) throws XMLLoadException 
	{
		Node valueNode;
		Node textNode;
		if(child.getNodeName()=="option"){
			valueNode=child.getAttributes().getNamedItem("value");
			textNode=child.getAttributes().getNamedItem("text");
			if(valueNode ==null){
				throw new XMLLoadException("Missing 'value' attribute in tag ",child);				
			}
			if(textNode == null){
				throw new XMLLoadException("Missing 'text' attribute in tag ",child);						
			}
			list.add(new OptionItem(valueNode.getNodeValue(),textNode.getNodeValue()));
		} else {
			throw new XMLLoadException("Options list contain invalid tag ",child);
		}
	}
	/**
	 * Handle options tag definition oelection list 
	 * , radion buttons, checkboxes or selection lists
	 * 
	 * @param parent  Parent Node
	 * @return        A linkedlist with defined options
	 * 
	 */
	private LinkedList<OptionItem> parseOptions(Node pparent) throws XMLLoadException {
		LinkedList<OptionItem> list=new LinkedList<>();
		Node child=pparent.getFirstChild();

		while(child!=null){
			if(child.getNodeType()==Node.ELEMENT_NODE){
				processOption(child,list);
			}
			child=child.getNextSibling();
		}
		return list;
	}
	
	/**
	 * Parse custome tags.
	 * On this moment only used for "options" tags
	 */

	@Override
	protected Object parseCustom(Object pparent,Node pnode) throws XMLLoadException 
	{
		if(pnode.getNodeName()=="options"){
			return parseOptions(pnode);
		} else {
			throw new XMLLoadException("Custom node not handled",pnode);
		}
	}
	
	/**
	 * Check if node refers a "ElementVariant". 
	 * 
	 * return If the node refers to a element vairant, the ElementVariant object is return
	 *        Otherwise a "null" value is returned 
	 */
	
	@Override
	protected ElementVariant getVariant(Node node) throws XMLLoadException, ReplaceVarException   
	{
		String className=this.getAttributeValue(node, "class");
		ElementVariant variant=null;
		if((className != null) && (className.charAt(0)=='@')){
			variant=getApplication().getVariantByName(className.substring(1));
		}
		return variant;
	}
	
	/**
	 * Parser configuration,how to handle every element.
	 */
	
	@Override
	protected void setupConfig() {
		addConfig("page",new XMLConfig(Page.class,Page.class,"",null));
		addConfig("element",new XMLConfig(Element.class,null,  "addElement",Element.class));
		addConfig("options",new XMLCustomConfig("",Element.class));
		addConfig("jsplug",new XMLConfig(JSPlug.class,null,"addJsPlug",Element.class));
		XMLConfig config=new XMLConfig(DataLayer.class,null,"setDataModel",Element.class);
		config.setXMLParser(XMLDataLayerParser.class);
		addConfig("datamodel",config);
		addConfig("data",new XMLCustomConfig("",null));
	}

	@Override
	protected String getName(Object object) {
		if(object instanceof Element){
			return ((Element<?>)object).getRefName(); 
		}
		return null;
	}

	@Override
	public AliasNamespace getAliasNamespace() {
		return AliasNamespace.ELEMENT;
	}

}
