package org.elaya.page;

import java.io.IOException;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.util.HtmlUtils;

public class ThemeItemBase {
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
	
	public ThemeItemBase(HttpServletResponse p_response) throws IOException{
		response=p_response;
		stream=response.getOutputStream();
	}
}
