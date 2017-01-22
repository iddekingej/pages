package org.elaya.page;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.parsers.ParserConfigurationException;

import org.elaya.page.Errors.AliasNotFound;
import org.elaya.page.Errors.LoadingAliasFailed;
import org.elaya.page.application.AliasData;
import org.elaya.page.application.Application;
import org.elaya.page.application.Application.InvalidAliasType;
import org.json.JSONException;
import org.json.JSONObject;
import org.xml.sax.SAXException;

public class JSWriter extends AbstractWriter{
	private StringBuilder buffer=new StringBuilder();

	public JSWriter(Application papplication,HttpServletRequest prequest,HttpServletResponse presponse)
	{
		super(papplication,prequest,presponse);
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
	
	public String toJsString(Object pvalue) throws JSONException{
		return JSONObject.valueToString(pvalue);
	}
	
	public void objVar(String varName,String value) throws JSONException 
	{
		setVar("this."+varName,value);
	}

	public void setVar(String varName,String value) throws JSONException {
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
	
	public String processUrl(String purl) throws ParserConfigurationException, SAXException, IOException, InvalidAliasType, AliasNotFound, LoadingAliasFailed 
	{
		String url=purl;
		if(url.startsWith("@")){
			url=getApplication().getAlias(url.substring(1),AliasData.ALIAS_URL,true);
		}
		if(url.startsWith("+")){
			return getRequest().getContextPath()+"/"+url.substring(1);
		} else {
			return url;
		}
	}
}
