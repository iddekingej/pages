package org.elaya.page;

import java.io.IOException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.elaya.page.application.Application;
import org.springframework.web.util.HtmlUtils;

public class Writer {
	HttpServletRequest  request;
	HttpServletResponse response;
	ServletOutputStream stream;
	Application         application;
//TODO make in configuration.	
	private String jsPath="resources/pages/js/";
	private String cssPath="resources/pages/css/";
	private String imgPath="resources/pages/images/";	
	
	public void setContentType(String p_str)
	{
		response.setContentType(p_str);
	}
	
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

	public String getBasePath(){
		return request.getContextPath();
	}
	
	public String procesUrl(String p_url) throws Exception
	{
		String l_url=p_url;
		if(l_url.startsWith("@")){
			l_url=application.getAlias(l_url.substring(1),AliasData.alias_url,true);
		}
		if(l_url.startsWith("+")){
			return getBasePath()+"/"+l_url.substring(1);
		} else {
			return l_url;
		}
	}
	
	public String getJsPath(String p_file)
	{
		return getBasePath()+"/"+jsPath+p_file;
	}
	
	public String getCssPath(String p_file)
	{
		return getBasePath()+"/"+cssPath+p_file;
	}
	
	public String getImgPath(String p_file)
	{
		return getBasePath()+"/"+imgPath+p_file;
	}
		
	
	public Writer(Application p_application,HttpServletRequest p_request,HttpServletResponse p_response) throws IOException {
		application=p_application;
		response=p_response;
		request=p_request;
		stream=response.getOutputStream();
	}

}
