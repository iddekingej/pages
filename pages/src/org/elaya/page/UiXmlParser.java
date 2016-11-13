package org.elaya.page;

import java.io.InputStream;
import java.util.HashMap;
import java.util.LinkedList;

import org.elaya.page.Errors.XmlLoadError;
import org.elaya.page.jsplug.JSPlug;
import org.elaya.page.quickform.OptionItem;
import org.elaya.page.xml.XmlConfig;
import org.elaya.page.xml.XmlParser;
import org.w3c.dom.Node;

public class UiXmlParser extends XmlParser {
	private ClassLoader classLoader;
	
	public UiXmlParser(Application p_application,ClassLoader p_classLoader) {
		super(p_application);
		classLoader=p_classLoader;
	}

	public UiXmlParser(Application p_application,ClassLoader p_classLoader,HashMap<String, Object> p_nameIndex) {
		super(p_application,p_nameIndex);
		classLoader=p_classLoader;
	}

	@Override
	protected InputStream openFile(String p_fileName) {
		InputStream l_stream=classLoader.getResourceAsStream("../pages/"+p_fileName);

		return l_stream;
	}
 
	@Override
	protected XmlParser createParser() {
		return new UiXmlParser(getApplication(),classLoader,getNameIndex());
	}
	
	private LinkedList<OptionItem> parseOptions(Node p_parent) throws XmlLoadError{
		LinkedList<OptionItem> l_list=new LinkedList<OptionItem>();
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
	}

	@Override
	protected String normalizeClassName(String p_name) throws Exception {
		if(p_name.charAt(0)=='@'){
			return getApplication().getAlias(p_name.substring(1), AliasData.alias_element);
		} 
		return p_name;
	}
	
	protected void AfterCreate(Object p_object) throws Exception
	{
		if(p_object instanceof Element){
			((Element<?>)p_object).setApplication(getApplication());
		}
		if(p_object instanceof Page){
			((Page)p_object).initTheme();
		}
	}

}
