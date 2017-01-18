package org.elaya.page;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

import org.elaya.page.Errors.ReplaceVarException;
import org.elaya.page.application.AliasData;
import org.elaya.page.application.Application;
import org.elaya.page.data.DataModel;
import org.elaya.page.data.Parameterized;
import org.elaya.page.jsplug.JSPlug;
import org.elaya.page.quickform.OptionItem;
import org.elaya.page.xml.XmlAppParser;
import org.elaya.page.xml.XMLConfig;
import org.elaya.page.xml.XMLCustomConfig;
import org.elaya.page.xml.XMLParser;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;

public class ElementParser extends XmlAppParser {
	
	public ElementParser(Application papplication) {
		super(papplication);		

	}

	public ElementParser(Application papplication,Map<String, Object> pnameIndex) {
		super(papplication,pnameIndex);		
	}
	
	
	
	@Override
	protected InputStream openFile(String pfileName) throws FileNotFoundException {		
		return getApplication().getConfigStream(pfileName);		
	}
 
	@Override
	protected XMLParser createParser() {
		return new ElementParser(getApplication(),getNameIndex());
	}
	
	private void processOption(Node child,LinkedList<OptionItem> list) throws XMLLoadException 
	{
		Node valueNode;
		Node textNode;
		if(child.getNodeName()=="option"){
			valueNode=child.getAttributes().getNamedItem("value");
			textNode=child.getAttributes().getNamedItem("text");
			if(valueNode ==null){
				addError("Missing 'value' attribute in tag ",child);				
			}
			if(textNode == null){
				addError("Missing 'text' attribute in tag ",child);						
			}
			if(valueNode != null && textNode != null){
				list.add(new OptionItem(valueNode.getNodeValue(),textNode.getNodeValue()));
			}
		} else {
			addError("Options list contain invalid tag ",child);
		}
	}
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
	
	private Map<String,Object> parseData(Node pnode)
	{
		Map<String,Object> data=new HashMap<>();
		NamedNodeMap attrs=pnode.getAttributes();
		Node attr;
		for(int cnt=0;cnt<attrs.getLength();cnt++){
			attr=attrs.item(cnt);
			data.put(attr.getNodeName(), attr.getNodeValue());
		}
		return data;
	}
	@Override
	protected Object parseCustom(Object pparent,Node pnode) throws XMLLoadException 
	{
		if(pnode.getNodeName()=="options"){
			return parseOptions(pnode);
		} else if(pnode.getNodeName()=="data"){
			return parseData(pnode);
		} else {
			throw new XMLLoadException("Custome node not handled",pnode);
		}
	}
	
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
	
	@Override
	protected void setupConfig() {
		addConfig("page",new XMLConfig(Page.class,Page.class,"",null));
		addConfig("element",new XMLConfig(Element.class,null,  "addElement",Element.class));
		addConfig("options",new XMLCustomConfig("",Element.class));
		addConfig("jsplug",new XMLConfig(JSPlug.class,null,"addJsPlug",Element.class));
		addConfig("datamodel",new XMLConfig(DataModel.class,null,"setDataModel",Element.class));
		addConfig("data",new XMLCustomConfig("setParameters",Parameterized.class));
	}

	@Override
	protected void afterCreate(Object object) throws ClassNotFoundException, InstantiationException, IllegalAccessException, InvocationTargetException, NoSuchMethodException  
	{
		if(object instanceof Page){
			((Page)object).initTheme();
		}
	}

	@Override

	protected String getName(Object object) {
		if(object instanceof Element){
			return ((Element<?>)object).getFullName(); 
		}
		return null;
	}

	@Override
	public String getAliasNamespace() {
		return AliasData.ALIAS_ELEMENT;
	}

}
