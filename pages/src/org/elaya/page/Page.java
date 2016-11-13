package org.elaya.page;

import java.util.LinkedHashSet;
import org.elaya.page.data.Data;

public class Page extends PageElement<PageThemeItem> {

	private String url;
	private int idCnt=0;
	private boolean toWindowSize=false;
	
	public String getUrl(){ return url;}
	
	void setUrl(String p_url)
	{		
		url=p_url;
	}
	
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

	
	
	public String getJsName()
	{
		return "pages.page";
	}
	void dummy()
	{
	} 
 
	private LinkedHashSet<String> processSetList(LinkedHashSet<String> p_in,String p_type) throws Exception
	{
		LinkedHashSet<String> l_return=new LinkedHashSet<String>();
		for(String l_value:p_in){
			if(l_value.charAt(0)=='@'){
				l_value=getApplication().getAlias(l_value.substring(1),p_type,true);
				String[] l_list=l_value.split(",");
				for(String l_part:l_list){
					l_return.add(l_part);
				}
			} else {
				l_return.add(l_value);
			}
		}
		return l_return;
	}
	
	public void display(Writer p_writer,Data p_data) throws Exception
	{
		Data l_data=getData(p_data);
		LinkedHashSet<String> l_js=new LinkedHashSet<String>();
		LinkedHashSet<String> l_css=new LinkedHashSet<String>();
		
		l_js.add("@pagejs");
		l_css.add("@pagecss");
		getAllCssFiles(l_css);
		getAllJsFiles(l_js);
		
		LinkedHashSet<String> l_procCss=processSetList(l_css,AliasData.alias_cssfile);
		LinkedHashSet<String> l_procJs=processSetList(l_js,AliasData.alias_jsfile);
		
		themeItem.pageHeader(p_writer,l_procJs,l_procCss);	
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

	public void initTheme() throws Exception
	{
		setTheme(new Theme(getApplication()));
	}
	
	public Page()
	{		
		setIsNamespace(true);
	}


}



