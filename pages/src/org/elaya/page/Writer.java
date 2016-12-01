package org.elaya.page;

import java.io.IOException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import org.springframework.web.util.HtmlUtils;

public class Writer {
	HttpServletResponse response;
	ServletOutputStream stream;
	
	public void print(String p_str) throws IOException
	{
		stream.print(p_str);
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

	public void jsInclude(String p_path) throws IOException{
		print("<script type='text/javascript' "+property("src",p_path)+"></script>");
	}
	
	public void cssInclude(String p_path) throws IOException{
		print("<link rel='stylesheet' type='text/css' "+property("href",p_path)+"></link>");
	}
	
	public void jsBegin() throws IOException
	{
		print("<script type='text/javascript'>//<![CDATA[\n");
	}	
	
	public void jsEnd() throws IOException
	{
		print("//]]></script>");
	}
	
	public void flush() throws IOException
	{
		stream.flush();
	}
	
	
	public String js_toString(Object p_value){
		if(p_value==null) return "\"\"";
		return "\""+str(p_value).replace("\"","\\\"")+"\"";
	}
	
	public void objVar(String p_var,String p_value) throws IOException
	{
		print("this."+p_var+"="+js_toString(p_value)+";");
	}
	
	
	public Writer(HttpServletResponse p_response) throws IOException {
		response=p_response;
		stream=response.getOutputStream();
	}

}
