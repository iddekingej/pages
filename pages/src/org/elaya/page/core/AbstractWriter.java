package org.elaya.page.core;

import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;
import org.elaya.page.Errors.AliasNotFound;
import org.elaya.page.Errors.LoadingAliasFailed;
import org.elaya.page.Errors.ReplaceVarException;
import org.elaya.page.application.AliasNamespace;
import org.elaya.page.application.Application;
import org.elaya.page.application.Application.InvalidAliasType;
import org.xml.sax.SAXException;

/**
 *  Base object for output writer.
 *  Used of Writer (html) and JSWriter (Js outputting)
 *
 */

public abstract class AbstractWriter {
/**
 * Application object 
 */
	private Application application;
/**
 * Session object belonging to the request/response
 */
	private PageSession session;
	
/**
 * Set  Writer
 * 
 * @param papplication
 * @param psession
 */
	public AbstractWriter(Application papplication,PageSession psession) {
		application=papplication;
		session=psession;
	}
/**
 * Get the application object 
 * 
 * @return Application object
 */
	public Application getApplication()
	{
		return application;
	}

	public PageSession getSession()
	{
		return session;
	}
	
/**
 * Base/Root  path of the application
 * 
 * @return Base path (without the host name) of the site
 */
	
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
    /**
     * Convert an url expression o the real path:
     * -Replace variables is string
     * -Replace alias value is the value starts with a "@"
     * -If url start with a "+" than the url is a application relative url. The
     *  root path is added to the url.
     *  
     * @param data  Current data set used for replacing variables
     * @param purl  The url to parse
     * @return The url string
     */
	
	public String processUrl(Data data,String purl) throws ParserConfigurationException, SAXException, IOException, InvalidAliasType, AliasNotFound, LoadingAliasFailed, ReplaceVarException 
	{
		String url=replaceVariables(data,purl);
		if(url.startsWith("@")){
			url=application.getAlias(url.substring(1),AliasNamespace.URL,true);
		} 
		if(url.startsWith("+")){
			return getBasePath()+"/"+url.substring(1);
		} else {
			return url;
		}
	}
	
	/**
	 * Convert java object:
	 * - when null , return an empty string
	 * - otherwise call object.toString()
	 * 
	 * @param pvalue
	 * @return
	 */
	
	public String str(Object pvalue){
		if(pvalue==null){
			return "";
		}
		return pvalue.toString();
	}
	
}
