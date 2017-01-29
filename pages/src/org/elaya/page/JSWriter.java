package org.elaya.page;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.elaya.page.application.Application;
import org.json.JSONException;
import org.json.JSONObject;

public class JSWriter extends AbstractWriter{
	private StringBuilder buffer=new StringBuilder();

	public JSWriter(Application papplication,HttpServletRequest prequest,HttpServletResponse presponse)
	{
		super(papplication,prequest,presponse);
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
	
	public void setVar(String varName,String value) throws JSONException {
		printNl(varName+"="+toJsString(value)+";");
	}
	
	public void objVar(String varName,String value) throws JSONException 
	{
		setVar("this."+varName,value);
	}

	public void setFromOther(String varName,String other){
		printNl(varName+"="+other+";");
	}
	
	@Override
	public String toString()
	{
		return buffer.toString();
	}
	
}
