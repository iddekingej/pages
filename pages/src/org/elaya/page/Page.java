package org.elaya.page;

import java.util.LinkedHashSet;
import java.util.Set;
import org.elaya.page.data.Data;

public class Page extends PageElement<PageThemeItem> {

	private String url;
	private int idCnt=0;
	private boolean toWindowSize=false;
	
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
 
	public void display(Writer p_writer,Data p_data) throws Exception
	{
		Data l_data=getData(p_data);
		Set<String> l_js=new LinkedHashSet<String>();
		Set<String> l_css=new LinkedHashSet<String>();
		l_js.add("pages.js");
		l_js.add("jquery.js");
		l_js.add("jquery-ui.js");
		l_js.add("primeui.js");
		l_css.add("jquery-ui.css");
		l_css.add("jquery-ui.theme.css");
		l_css.add("theme.css");
		l_css.add("primeui.min.css");
		getAllCssFiles(l_css);
		getAllJsFiles(l_js);
		
		themeItem.pageHeader(p_writer,l_js,l_css);	
		displaySubElements(p_writer,l_data);
		p_writer.jsBegin();
		generateJs(p_writer,p_data);
		p_writer.jsEnd();
		

		themeItem.pageFooter(p_writer);
	}	
	
	@Override
	protected void makeJsObject(Writer p_writer,Data p_data)
	{
		
	}

	public void preSubJs(Writer p_writer,Data p_data) throws Exception
	{
		if(toWindowSize){
			p_writer.print("pages.page.initToWindowSize();");
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
		setIsNamespace(true);
	}


}



