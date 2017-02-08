package org.elaya.page.security;

import java.io.IOException;
import java.io.NotSerializableException;
import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.util.LinkedList;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.parsers.ParserConfigurationException;

import org.elaya.page.Errors.AliasNotFound;
import org.elaya.page.Errors.LoadingAliasFailed;
import org.elaya.page.application.Application.DefaultDBConnectionNotSet;
import org.elaya.page.application.Application.InvalidAliasType;
import org.elaya.page.core.PageSession;
import org.elaya.page.core.PageSession.InvalidSessionData;
import org.elaya.page.data.Data.KeyNotFoundException;
import org.elaya.page.receiver.Receiver.ReceiverException;
import org.elaya.page.security.Errors.AuthenticationException;
import org.elaya.page.widget.Element.DisplayException;
import org.elaya.page.xml.XMLParserBase.XMLLoadException;
import org.xml.sax.SAXException;

public class FilterManager  {
	
	private String loginPageUrl="";	
	private LinkedList<RequestMatcherGroup> requestMatcherGroups=new LinkedList<>();


	public void setLoginPageUrl(String purl){
		loginPageUrl=purl;
	}
	
	public String getLoginPageUrl(){
		return loginPageUrl;
	}
	
	
	protected PageSession createSession(HttpServletRequest servletRequest,HttpServletResponse servletResponse) throws NotSerializableException, InstantiationException, IllegalAccessException, InvocationTargetException, NoSuchMethodException, ClassNotFoundException, InvalidSessionData  
	{
		PageSession session=new PageSession(servletRequest,servletResponse);
		session.initSession();
		return session;
	}
	
	
	
	public boolean execute(ServletRequest request,ServletResponse response) throws NoSuchMethodException, ClassNotFoundException, InstantiationException, IllegalAccessException, InvocationTargetException, InvalidSessionData, IOException, AuthenticationException, ReceiverException, XMLLoadException, SQLException, DefaultDBConnectionNotSet, KeyNotFoundException, ParserConfigurationException, SAXException, InvalidAliasType, AliasNotFound, LoadingAliasFailed, DisplayException 
	{
		if(!(request instanceof HttpServletRequest) || !(response instanceof HttpServletResponse)  ){
			return false;
		}
		PageSession session=createSession((HttpServletRequest)request,(HttpServletResponse)response);
		MatchActionResult result;
		boolean nextFilter=true;
		for(RequestMatcherGroup rmGroup:requestMatcherGroups){
			result=rmGroup.execute(session);
			if(result==MatchActionResult.NONEXTFILTER){
				nextFilter=false;
			} else if((result==MatchActionResult.NOTAUTHORIZED) ||
			   (result==MatchActionResult.SECURITYFAILED)){
				session.redirect(loginPageUrl);
				return false;
			}
		}
		return nextFilter;
		
	}
	
	public void addRequestMatcherGroup(RequestMatcherGroup prequestMatcherGroup){
		requestMatcherGroups.add(prequestMatcherGroup);
	}

}
