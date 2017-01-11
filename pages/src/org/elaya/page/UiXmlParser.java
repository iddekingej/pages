package org.elaya.page;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.util.LinkedList;
import java.util.Map;

import org.elaya.page.Errors.ReplaceVarException;
import org.elaya.page.application.Application;
import org.elaya.page.data.DataModel;
import org.elaya.page.jsplug.JSPlug;
import org.elaya.page.quickform.OptionItem;
import org.elaya.page.xml.XmlAppParser;
import org.elaya.page.xml.XMLConfig;
import org.elaya.page.xml.XMLParser;
import org.w3c.dom.Node;

public class UiXmlParser extends XmlAppParser {
	
	public UiXmlParser(Application papplication) {
		super(papplication);		

	}

	public UiXmlParser(Application papplication,Map<String, Object> pnameIndex) {
		super(papplication,pnameIndex);		
	}
	
	
	
	@Override
	protected InputStream openFile(String pfileName) throws FileNotFoundException {		
		return getApplication().getConfigStream(pfileName);		
	}
 
	@Override
	protected XMLParser createParser() {
		return new UiXmlParser(getApplication(),getNameIndex());
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
	@Override
	protected Object parseCustom(Object pparent,Node pnode) throws XMLLoadException 
	{
		if(pnode.getNodeName()=="options"){
			return parseOptions(pnode);
		}
		return null;
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
	protected void addConfig() {
		addConfig("page",new XMLConfig(Page.class,Page.class,false,"",false));
		addConfig("element",new XMLConfig(Element.class,null, false, "addElement",true));
		addConfig("options",new XMLConfig(null,null,true,"",true));
		addConfig("jsplug",new XMLConfig(JSPlug.class,null,false,"addJsPlug",true));
		addConfig("datamodel",new XMLConfig(DataModel.class,null,false,"setDataModel",true));
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
