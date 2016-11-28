package org.elaya.page;

import java.io.IOException;
import java.util.Set;

import org.elaya.page.application.Application;
import org.springframework.web.util.HtmlUtils;

public class ThemeItemBase {
	private Theme theme;
	private String classPrefix="";

	public void setClassPrefix(String p_classPrefix)
	{
		classPrefix=p_classPrefix;	
	}

	public String getClassPrefix()
	{
		return classPrefix;
	}
	
	public Application getApplication()
	{
		return theme.getApplication();
	}
	
	public void getCssFiles(Set<String> p_files)
	{
		
	}

	
	public String str(Object p_value){
		if(p_value==null){
			return "";
		}
		return p_value.toString();
	}
	
	public String escape(String p_value)
	{
		return HtmlUtils.htmlEscape(str(p_value));
	}
	
	public String escape(Object p_value){
		return escape(str(p_value));
	}
	
	public String property(String p_name,Object p_value)
	{
		return p_name+"=\""+escape(p_value)+"\" ";
	}
	
	public String propertyF(String p_name,Object p_value)
	{
		String l_str=str(p_value);
		if(l_str.length()>0){
			return property(p_name,p_value);
		}
		return "";
	}
	
	protected String classProperty(String p_className)
	{
		return property("class",classPrefix+p_className);
	}


	
	public ThemeItemBase(Theme p_theme) throws IOException{
		theme=p_theme;		
	}
}
