package org.elaya.page;

import java.io.IOException;
import java.util.Set;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.util.HtmlUtils;

public class ThemeItemBase {
	private Theme theme;
	private String classPrefix="";
	protected HttpServletResponse response;
	private ServletOutputStream stream;
	
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
	
	public void print(String p_value) throws IOException
	{
		stream.print(p_value);
		stream.flush();
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
	
	public String js_toString(Object p_value){
		if(p_value==null) return "\"\"";
		return "\""+str(p_value).replace("\"","\\\"")+"\"";
	}
	
	public void jsInclude(String p_path) throws IOException{
		print("<script type='text/javascript' "+property("src",theme.getApplication().getJsPath(p_path))+"></script>");
	}
	
	public void cssInclude(String p_path) throws IOException{
		String l_path=theme.getApplication().getCssPath(p_path);
		print("<link rel='stylesheet' type='text/css' "+property("href",l_path)+"></link>");
	}
	
	public void jsBegin() throws IOException
	{
		print("<script type='text/javascript'>//<![CDATA[\n");
	}	
	
	public void jsEnd() throws IOException
	{
		print("//]]></script>");
	}
	
	protected String classProperty(String p_className)
	{
		return property("class",classPrefix+p_className);
	}
	
	public ThemeItemBase(Theme p_theme) throws IOException{
		theme=p_theme;
		response=p_theme.getResponse();
		stream=response.getOutputStream();
		
	}
}
