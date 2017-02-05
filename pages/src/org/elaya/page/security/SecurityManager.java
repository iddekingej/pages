package org.elaya.page.security;

import java.io.IOException;
import java.io.NotSerializableException;
import java.lang.reflect.InvocationTargetException;
import java.util.LinkedList;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.elaya.page.application.Application;
import org.elaya.page.application.PageApplicationAware;
import org.elaya.page.receiver.Receiver.ReceiverException;
import org.elaya.page.security.Errors.AuthenticationException;
import org.elaya.page.security.Session.InvalidSessionData;
import org.elaya.page.xml.XMLParserBase.XMLLoadException;

public class SecurityManager  {
	
	private String loginCheckUrl="";
	private String loginPageUrl="";	
	private LinkedList<RequestMatcherGroup> requestMatcherGroups=new LinkedList<>();

	public void setLoginCheckUrl(String purl){
		loginCheckUrl=purl;
	}
	
	public String getLoginCheckUrl(){
		return loginCheckUrl;
	}
	
	public void setLoginPageUrl(String purl){
		loginPageUrl=purl;
	}
	
	public String getLoginPageUrl(){
		return loginPageUrl;
	}
	
	
	protected Session createSession(ServletRequest servletRequest,ServletResponse servletResponse) throws NoSuchMethodException, ClassNotFoundException, InstantiationException, IllegalAccessException,  InvocationTargetException, NotSerializableException, InvalidSessionData
	{
		Session session=new Session(servletRequest,servletResponse);
		session.initSession();
		return session;
	}
	
	
	public boolean execute(ServletRequest request,ServletResponse response) throws NoSuchMethodException, ClassNotFoundException, InstantiationException, IllegalAccessException, InvocationTargetException, InvalidSessionData, IOException, AuthenticationException, ReceiverException, XMLLoadException 
	{
		Session session=createSession(request,response);
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
