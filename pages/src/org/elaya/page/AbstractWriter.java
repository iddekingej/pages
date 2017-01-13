package org.elaya.page;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.parsers.ParserConfigurationException;

import org.elaya.page.Element.InvalidVariableNameException;
import org.elaya.page.Errors.AliasNotFound;
import org.elaya.page.Errors.LoadingAliasFailed;
import org.elaya.page.Errors.ReplaceVarException;
import org.elaya.page.application.Application;
import org.elaya.page.application.Application.InvalidAliasType;
import org.elaya.page.data.Data;
import org.xml.sax.SAXException;

public class AbstractWriter {

	private Application application;
	private HttpServletRequest  request;
	private HttpServletResponse response;
	
	public AbstractWriter(Application papplication,HttpServletRequest   prequest,HttpServletResponse presponse) {
		application=papplication;
		request=prequest;
		response=presponse;
	}

	public Application getApplication()
	{
		return application;
	}
	
	public HttpServletRequest  getRequest(){
		return request;
	}
	
	public HttpServletResponse getResponse(){
		return response;
	}
	
	
	public String getBasePath(){
		return request.getContextPath();
	}
	
	
	public String replaceVariables(Data pdata,String pstring) throws ReplaceVarException
	{
		int pos=0;
		int newPos;
		StringBuilder returnValue=new StringBuilder();
		String varName;
		Object value;
		String string=pstring==null?"":pstring;
		try{
			while(true){
				newPos=string.indexOf("${",pos);
				if(newPos==-1){
					returnValue.append(string.substring(pos));
					break;
				}
				returnValue.append(string.substring(pos,newPos));
				pos=string.indexOf('}',newPos);
				if(pos==-1){
					throw new InvalidVariableNameException("Missing '}' after position "+newPos); 				
				}
				varName=string.substring(newPos+2,pos);	
				value=pdata.get(varName);				
				if(value != null){
					returnValue.append(value.toString());
				}			
				pos++;
			}
		}catch(Exception e){
			throw new ReplaceVarException("In String "+pstring,e);
		}
		return returnValue.toString();
	}
	
	public String processUrl(Data data,String purl) throws ParserConfigurationException, SAXException, IOException, InvalidAliasType, AliasNotFound, LoadingAliasFailed, ReplaceVarException 
	{
		String url=replaceVariables(data,purl);
		if(url.startsWith("@")){
			url=application.getAlias(url.substring(1),AliasData.ALIAS_URL,true);
		}
		if(url.startsWith("+")){
			return getBasePath()+"/"+url.substring(1);
		} else {
			return url;
		}
	}
	
	
}
