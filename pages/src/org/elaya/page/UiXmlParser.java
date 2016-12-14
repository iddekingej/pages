package org.elaya.page;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.LinkedList;
import java.util.Map;

import org.elaya.page.Errors.XmlLoadError;
import org.elaya.page.application.Application;
import org.elaya.page.data.DataModel;
import org.elaya.page.jsplug.JSPlug;
import org.elaya.page.quickform.OptionItem;
import org.elaya.page.xml.XmlAppParser;
import org.elaya.page.xml.XmlConfig;
import org.elaya.page.xml.XmlParser;
import org.w3c.dom.Node;

public class UiXmlParser extends XmlAppParser {
	private ClassLoader classLoader;

	public UiXmlParser(Application papplication,ClassLoader pclassLoader) {
		super(papplication);		
		classLoader=pclassLoader;
	}

	public UiXmlParser(Application papplication,ClassLoader pclassLoader,Map<String, Object> pnameIndex) {
		super(papplication,pnameIndex);		
		classLoader=pclassLoader;
	}
	
	@Override
	protected InputStream openFile(String pfileName) throws FileNotFoundException {		
		return getApplication().getConfigStream(pfileName);		
	}
 
	@Override
	protected XmlParser createParser() {
		return new UiXmlParser(getApplication(),classLoader,getNameIndex());
	}
	
	private void processOption(Node child,LinkedList<OptionItem> list) throws XmlLoadError
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
	private LinkedList<OptionItem> parseOptions(Node pparent) throws XmlLoadError{
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
	protected Object parseCustom(Object pparent,Node pnode) throws XmlLoadError
	{
		if(pnode.getNodeName()=="options"){
			return parseOptions(pnode);
		}
		return null;
	}
	
	@Override
	protected void addConfig() {
		addConfig("page",new XmlConfig(Page.class,Page.class,false,"",false));
		addConfig("element",new XmlConfig(Element.class,null, false, "addElement",true));
		addConfig("options",new XmlConfig(null,null,true,"",true));
		addConfig("jsplug",new XmlConfig(JSPlug.class,null,false,"addJsPlug",true));
		addConfig("datamodel",new XmlConfig(DataModel.class,null,false,"setDataModel",true));
	}

	@Override
	protected void afterCreate(Object pobject) throws Exception
	{
		if(pobject instanceof DataModel){
			((DataModel)pobject).setApplication(getApplication());
		}
		if(pobject instanceof Page){
			((Page)pobject).setApplication(getApplication());
			((Page)pobject).initTheme();
		}
	}

	@Override
	protected String getName(Object pobject) {
		if(pobject instanceof Element<?>){
			return ((Element<?>)pobject).getNamespaceName();
		}
		return "";
	}

	@Override
	public String getAliasNamespace() {
		return AliasData.ALIAS_ELEMENT;
	}

}
