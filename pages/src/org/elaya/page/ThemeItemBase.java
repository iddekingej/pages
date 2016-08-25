package org.elaya.page;

import java.io.IOException;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.util.HtmlUtils;

public class ThemeItemBase {
	private Theme theme;
	protected HttpServletResponse response;
	private ServletOutputStream stream;
	
	
	public Application getApplication()
	{
		return theme.getApplication();
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
		return HtmlUtils.htmlEscape(p_value);
	}
	
	public String escape(Object p_value){
		return escape(str(p_value));
	}
	
	public String property(String p_name,Object p_value)
	{
		return p_name+"=\""+escape(p_value)+"\" ";
	}
	
	public String js_toString(Object p_value){
		return "\""+p_value.toString().replace("\"","\\\"")+"\"";
	}
	
	public void jsInclude(String p_path) throws IOException{
		print("<script type='text/javascript' src='"+escape(theme.getApplication().getJsPath(p_path))+"'></script>");
	}
	
	public void jsBegin() throws IOException
	{
		print("<script type='text/javascript'>");
	}	
	
	public void jsEnd() throws IOException
	{
		print("</script>");
	}
	
	
	public ThemeItemBase(Theme p_theme) throws IOException{
		theme=p_theme;
		response=p_theme.getResponse();
		stream=response.getOutputStream();
	}
}
