package org.elaya.page;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.xml.parsers.ParserConfigurationException;

import org.elaya.page.Errors.AliasNotFound;
import org.elaya.page.Errors.LoadingAliasFailed;
import org.elaya.page.application.Application;
import org.elaya.page.application.Application.InvalidAliasType;
import org.xml.sax.SAXException;

public class JSWriter {
	private StringBuilder buffer=new StringBuilder();
	private Application application;
	private HttpServletRequest request;
	
	public JSWriter(Application papplication,HttpServletRequest prequest)
	{
		application=papplication;
		request=prequest;
	}
	public String str(Object pvalue){
		if(pvalue==null){
			return "";
		}
		return pvalue.toString();
	}

	public void print(String text)
	{
		buffer.append(text);
	}
	
	public void printNl(String text)
	{
		buffer.append(text).append("\n");
	}
	
	public String toJsString(Object pvalue){
		if(pvalue==null){
			return "\"\"";
		}
		return "\""+str(pvalue).replace("\"","\\\"")+"\"";
	}
	
	public void objVar(String varName,String value) 
	{
		setVar("this."+varName,value);
	}

	public void setVar(String varName,String value) {
		printNl(varName+"="+toJsString(value)+";");
	}
	
	public void setFromOther(String varName,String other){
		printNl(varName+"="+other+";");
	}
	
	@Override
	public String toString()
	{
		return buffer.toString();
	}
	
	public String procesUrl(String purl) throws ParserConfigurationException, SAXException, IOException, InvalidAliasType, AliasNotFound, LoadingAliasFailed 
	{
		String url=purl;
		if(url.startsWith("@")){
			url=application.getAlias(url.substring(1),AliasData.ALIAS_URL,true);
		}
		if(url.startsWith("+")){
			return request.getContextPath()+"/"+url.substring(1);
		} else {
			return url;
		}
	}
}
