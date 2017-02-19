package org.elaya.page.core;

import java.io.IOException;
import javax.servlet.ServletOutputStream;
import org.elaya.page.application.Application;
import org.springframework.web.util.HtmlUtils;

public class Writer extends AbstractWriter{
	ServletOutputStream stream;
	JSWriter            jswriter;
	int idCnt=0;


	public Writer(Application papplication,PageSession psession) throws IOException {
		super(papplication,psession);
		stream=psession.getOutputStream();		
	}	
	
	public JSWriter getJSWriter()
	{
		if(jswriter==null){
			jswriter=new JSWriter(getApplication(),getSession());
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
		getSession().setContentType(pstr);
	}
	
	public void print(String pstr) throws IOException
	{
		stream.print(pstr);
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
		
	public String getImgUrl(String pfile)
	{
		return getBasePath()+"/"+getApplication().getImageUrl()+pfile;
	}
		
	
	public void generateJs() throws IOException
	{
		jsBegin();
		print(jswriter.toString());
		jsEnd();
	}
	
	public String getJSPath(String pfile)
	{
		return getBasePath()+"/"+getApplication().getJSPath() +pfile;
	}
	
	public String getCSSPath(String pfile)
	{
		return getBasePath()+"/"+getApplication().getCSSPath()+pfile;
	}
	
	
	
}