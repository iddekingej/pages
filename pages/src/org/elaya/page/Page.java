package org.elaya.page;

import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Set;
import org.elaya.page.Errors.duplicateElementOnPage;
import org.elaya.page.data.Data;

public class Page extends PageElement<PageThemeItem> {

	private String url;
	private int idCnt=0;
	private boolean toWindowSize=false;
	private HashMap<String,Element<?>> nameIndex=new HashMap<String,Element<?>>();
	
	public String getUrl(){ return url;}
	
	public void setToWindowSize(Boolean p_flag)
	{
		toWindowSize=p_flag;
	}
	
	public boolean getToWindowSize()
	{
		return toWindowSize;
	}
	
	public int newId()
	{
		idCnt++;
		return idCnt;
	}
	
	void addToNameIndex(Element<?> p_element) throws duplicateElementOnPage
	{
		if(nameIndex.containsKey(p_element.getName())){
			throw new Errors.duplicateElementOnPage(p_element.getName());
		}
		nameIndex.put(p_element.getName(),p_element);
	}
	
	public Page()
	{
		super();
	}
	
	void setUrl(String p_url)
	{		
		url=p_url;
	}
	
	public String getJsName()
	{
		return "pages.page";
	}
	void dummy()
	{
	} 
 
	public void display(Data p_data) throws Exception
	{
		Data l_data=getData(p_data);
		Set<String> l_js=new LinkedHashSet<String>();
		Set<String> l_css=new LinkedHashSet<String>();
		l_js.add("pages.js");
		l_js.add("jquery.js");
		l_js.add("jquery-ui.js");
		l_css.add("jquery-ui.css");
		l_css.add("jquery-ui.theme.css");
		getAllCssFiles(l_css);
		getAllJsFiles(l_js);
		
		themeItem.pageHeader(l_js,l_css);	
		displaySubElements(l_data);
		themeItem.jsBegin();
		generateJs(p_data);
		themeItem.jsEnd();
		

		themeItem.pageFooter();
	}	
	
	protected void makeJsObject(Data p_data)
	{
		
	}
	
	public void preSubJs(Data p_data) throws Exception
	{
		if(toWindowSize){
			themeItem.print("pages.page.initToWindowSize();");
		}
	}
	
	public String getThemeName()
	{
			return "PageThemeItem";
	}
	
	public boolean checkElement(Element<?> p_element){
		return p_element instanceof PageElement;
	}
	
	public Page(Application p_application) throws Exception
	{
		setTheme(new Theme(p_application));
	}


}



