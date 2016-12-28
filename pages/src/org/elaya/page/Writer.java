package org.elaya.page;

import java.io.IOException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.parsers.ParserConfigurationException;

import org.elaya.page.Errors.AliasNotFound;
import org.elaya.page.Errors.LoadingAliasFailed;
import org.elaya.page.application.Application;
import org.elaya.page.application.Application.InvalidAliasType;
import org.springframework.web.util.HtmlUtils;
import org.xml.sax.SAXException;

public class Writer {
	HttpServletRequest  request;
	HttpServletResponse response;
	ServletOutputStream stream;
	Application         application;
	int idCnt=0;
//TODO make in configuration.	
	private String jsPath="resources/pages/js/";
	private String cssPath="resources/pages/css/";
	private String imgPath="resources/pages/images/";	

	public Writer(Application papplication,HttpServletRequest prequest,HttpServletResponse presponse) throws IOException {
		application=papplication;
		response=presponse;
		request=prequest;
		stream=response.getOutputStream();
	}	
	
	public int newId()
	{
		idCnt++;
		return idCnt;
	}
	
	public void setContentType(String pstr)
	{
		response.setContentType(pstr);
	}
	
	public void print(String pstr) throws IOException
	{
		stream.print(pstr);
	}
	
	public String str(Object pvalue){
		if(pvalue==null){
			return "";
		}
		return pvalue.toString();
	}
	
	public String escape(String pvalue)
	{
		return HtmlUtils.htmlEscape(str(pvalue));
	}
	
	public String escape(Object pvalue){
		return escape(str(pvalue));
	}
	
	public String property(String pname,Object pvalue)
	{
		return pname+"=\""+escape(pvalue)+"\" ";
	}
	
	public String propertyF(String pname,Object pvalue)
	{
		String str=str(pvalue);
		if(str.length()>0){
			return property(pname,pvalue);
		}
		return "";
	}

	public void jsInclude(String ppath) throws IOException{
		print("<script type='text/javascript' "+property("src",ppath)+"></script>");
	}
	
	public void cssInclude(String ppath) throws IOException{
		print("<link rel='stylesheet' type='text/css' "+property("href",ppath)+"></link>");
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
	
	
	public String js_toString(Object pvalue){
		if(pvalue==null){
			return "\"\"";
		}
		return "\""+str(pvalue).replace("\"","\\\"")+"\"";
	}
	
	public void objVar(String varName,String value) throws IOException
	{
		setVar("this."+varName,value);
	}

	public void setVar(String varName,String value) throws IOException{
		print(varName+"="+js_toString(value)+";\n");
	}
	
	public void setFromOther(String varName,String other) throws IOException{
		print(varName+"="+other+";\n");
	}
	
	public String getBasePath(){
		return request.getContextPath();
	}
	
	public String procesUrl(String purl) throws ParserConfigurationException, SAXException, IOException, InvalidAliasType, AliasNotFound, LoadingAliasFailed 
	{
		String url=purl;
		if(url.startsWith("@")){
			url=application.getAlias(url.substring(1),AliasData.ALIAS_URL,true);
		}
		if(url.startsWith("+")){
			return getBasePath()+"/"+url.substring(1);
		} else {
			return url;
		}
	}
	
	public String getJsPath(String pfile)
	{
		return getBasePath()+"/"+jsPath+pfile;
	}
	
	public String getCssPath(String pfile)
	{
		return getBasePath()+"/"+cssPath+pfile;
	}
	
	public String getImgPath(String pfile)
	{
		return getBasePath()+"/"+imgPath+pfile;
	}
		

}
