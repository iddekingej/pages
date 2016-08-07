package org.elaya.page;

import java.io.IOException;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.util.HtmlUtils;

public class ThemeItemBase {
	private Theme theme;
	protected HttpServletResponse response;
	private ServletOutputStream stream;
	public void print(String p_value) throws IOException
	{
		stream.print(p_value);
	}
	
	public String escape(String p_value)
	{
		return HtmlUtils.htmlEscape(p_value);
	}
	
	public String property(String p_name,String p_value)
	{
		return p_name+"=\""+escape(p_value)+"\" ";
	}
	
	public ThemeItemBase(Theme p_theme) throws IOException{
		theme=p_theme;
		response=p_theme.getResponse();
		stream=response.getOutputStream();
	}
}
