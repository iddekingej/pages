package org.elaya.page;

import java.io.IOException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.elaya.page.application.Application;
import org.springframework.web.util.HtmlUtils;

public class Writer extends AbstractWriter{
	ServletOutputStream stream;
	JSWriter            jswriter;
	int idCnt=0;
//TODO make in configuration.	
	private String jsPath="resources/pages/js/";
	private String cssPath="resources/pages/css/";
	private String imgPath="resources/pages/images/";	

	public Writer(Application papplication,HttpServletRequest prequest,HttpServletResponse presponse) throws IOException {
		super(papplication,prequest,presponse);
		stream=getResponse().getOutputStream();		
	}	
	
	public JSWriter getJSWriter()
	{
		if(jswriter==null){
			jswriter=new JSWriter(getApplication(),getRequest(),getResponse());
		}
		return jswriter;
	}
	
	public int newId()
	{
		idCnt++;
		return idCnt;
	}
	
	public void setContentType(String pstr)
	{
		getResponse().setContentType(pstr);
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
		
	public String getJsPath(String pfile)
	{
		return getBasePath()+"/"+jsPath+pfile;
	}
	
	public String getCssPath(String pfile)
	{
		return getBasePath()+"/"+cssPath+pfile;
	}
	
	public String getImgUrl(String pfile)
	{
		return getBasePath()+"/"+getApplication().getImageUrl()+pfile;
	}
		
	
	public void generateJs() throws IOException
	{
		jsBegin();
		print(getJSWriter().toString());
		jsEnd();
	}
}
