package org.elaya.page.core;

import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;
import org.elaya.page.Errors.AliasNotFound;
import org.elaya.page.Errors.LoadingAliasFailed;
import org.elaya.page.Errors.ReplaceVarException;
import org.elaya.page.application.AliasData;
import org.elaya.page.application.Application;
import org.elaya.page.application.Application.InvalidAliasType;
import org.xml.sax.SAXException;

public abstract class AbstractWriter {

	private Application application;
	private PageSession session;
	
	public AbstractWriter(Application papplication,PageSession psession) {
		application=papplication;
		session=psession;
	}

	public Application getApplication()
	{
		return application;
	}

	public PageSession getSession()
	{
		return session;
	}
	
	
	public String getBasePath(){
		return session.getBasePath();
	}
	
	
	public String replaceVariables(Data pdata,String pstring) throws ReplaceVarException
	{
		int pos=0;
		int newPos;
		StringBuilder returnValue=new StringBuilder();
		String varName;
		Object value;
		String string=pstring==null?"":pstring;
		
			while(true){
				newPos=string.indexOf("${",pos);
				if(newPos==-1){
					returnValue.append(string.substring(pos));
					break;
				}
				returnValue.append(string.substring(pos,newPos));
				pos=string.indexOf('}',newPos+2);
				if(pos==-1){
					throw new ReplaceVarException("Missing '}'"); 				
				}
				varName=string.substring(newPos+2,pos);
				try{
					value=pdata.get(varName);
				}catch(Exception e){
					throw new ReplaceVarException("In String "+pstring,e);
				}
							
				if(value != null){
					returnValue.append(value.toString());
				}			
				pos++;
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
	
	public String str(Object pvalue){
		if(pvalue==null){
			return "";
		}
		return pvalue.toString();
	}
	
}
