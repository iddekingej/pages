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

	public UiXmlParser(Application p_application,ClassLoader p_classLoader) {
		super(p_application);		
		classLoader=p_classLoader;
	}

	public UiXmlParser(Application p_application,ClassLoader p_classLoader,Map<String, Object> p_nameIndex) {
		super(p_application,p_nameIndex);		
		classLoader=p_classLoader;
	}
	
	@Override
	protected InputStream openFile(String p_fileName) throws FileNotFoundException {		
		return getApplication().getConfigStream(p_fileName);		
	}
 
	@Override
	protected XmlParser createParser() {
		return new UiXmlParser(getApplication(),classLoader,getNameIndex());
	}
	
	private LinkedList<OptionItem> parseOptions(Node p_parent) throws XmlLoadError{
		LinkedList<OptionItem> l_list=new LinkedList<>();
		Node l_child=p_parent.getFirstChild();
		Node l_valueNode;
		Node l_textNode;
		while(l_child!=null){
			if(l_child.getNodeType()==Node.ELEMENT_NODE){
				if(l_child.getNodeName()=="option"){
					l_valueNode=l_child.getAttributes().getNamedItem("value");
					l_textNode=l_child.getAttributes().getNamedItem("text");
					if(l_valueNode ==null){
						addError("Missing 'value' attribute in tag ",l_child);				
					}
					if(l_textNode == null){
						addError("Missing 'text' attribute in tag ",l_child);						
					}
					if(l_valueNode != null && l_textNode != null){
						l_list.add(new OptionItem(l_valueNode.getNodeValue(),l_textNode.getNodeValue()));
					}
				} else {
					addError("Options list contain invalid tag ",l_child);
				}
			}
			l_child=l_child.getNextSibling();
		}
		return l_list;
}
	@Override
	protected Object parseCustom(Object p_parent,Node p_node) throws XmlLoadError
	{
		if(p_node.getNodeName()=="options"){
			return parseOptions(p_node);
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
	protected void afterCreate(Object p_object) throws Exception
	{
		if(p_object instanceof DataModel){
			((DataModel)p_object).setApplication(getApplication());
		}
		if(p_object instanceof Page){
			((Page)p_object).setApplication(getApplication());
			((Page)p_object).initTheme();
		}
	}

	@Override
	protected String getName(Object p_object) {
		if(p_object instanceof Element<?>){
			return ((Element<?>)p_object).getNamespaceName();
		}
		return "";
	}

	@Override
	public String getAliasNamespace() {
		return AliasData.alias_element;
	}

}
