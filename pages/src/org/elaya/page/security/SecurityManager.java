package org.elaya.page.security;

import java.io.IOException;
import java.io.NotSerializableException;
import java.lang.reflect.InvocationTargetException;
import java.util.LinkedList;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.elaya.page.security.Errors.AuthenticationException;
import org.elaya.page.security.Session.InvalidSessionData;

public class SecurityManager {
	
	private String loginCheckUrl="";
	private String loginPageUrl="";
	private LinkedList<RequestMatcher> requestMatchers=new LinkedList<>();
	

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
	
	protected RequestMatcher matchRequest(Session session)
	{
		RequestMatcher found=null;
		for(RequestMatcher requestMatcher:requestMatchers){
			found=requestMatcher.matchRequest(session);			
			if(found != null){
				return found;
			}
		}
		return found;
	}
	
	protected Session createSession(ServletRequest servletRequest,ServletResponse servletResponse) throws NoSuchMethodException, ClassNotFoundException, InstantiationException, IllegalAccessException,  InvocationTargetException, NotSerializableException, InvalidSessionData
	{
		Session session=new Session(servletRequest,servletResponse);
		session.initSession();
		return session;
	}
	
	
	public boolean execute(ServletRequest request,ServletResponse response) throws NoSuchMethodException, ClassNotFoundException, InstantiationException, IllegalAccessException, InvocationTargetException, InvalidSessionData, IOException, AuthenticationException 
	{
		Session session=createSession(request,response);
		RequestMatcher requestMatcher=matchRequest(session);
		if(requestMatcher==null){			
			session.redirect(loginPageUrl);			
			return false;
		}
		
		MatchActionResult result=requestMatcher.execute(session);		
		switch(result){
			case NEXTFILTER: return true;
			case NONEXTFILTER: return false;
			case NOTAUTHORIZED:				
			case SECURITYFAILED:
				session.redirect(loginPageUrl);
				return false;
		}
		return false;
		
	}
	
	public void addRequestMatcher(RequestMatcher prequestMatcher){
		requestMatchers.add(prequestMatcher);
	}
}
