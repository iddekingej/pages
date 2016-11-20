package org.elaya.page.security;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.elaya.page.data.DynamicObject;

public class SecurityManager {
	private String loginCheckUrl="";
	private String loginPageUrl="";
	private String sessionDataClass="";
	private LinkedList<RequestMatcher> requestMatchers=new LinkedList<>();
	private Authenticator authenticator;
	private SessionData sessionData;
	
	public void setSessionDataClass(String p_sessionDataClass){
		sessionDataClass=p_sessionDataClass;
	}
	
	public String getSessionDataClass(){
		return sessionDataClass;
	}
	
	public void setAuthenticator(Authenticator p_authenticator)
	{
			authenticator=p_authenticator;
	}
	
	public Authenticator getAuthenticator()
	{
		return authenticator;
	}
	
	public SecurityManager() {
	}
	
	public void setLoginCheckUrl(String p_url){
		loginCheckUrl=p_url;
	}
	
	public String getLoginCheckUrl(){
		return loginCheckUrl;
	}
	
	public void setLoginPageUrl(String p_url){
		loginPageUrl=p_url;
	}
	
	public String getLoginPageUrl(){
		return loginPageUrl;
	}
	
	protected RequestMatcher matchRequest(ServletRequest p_request)
	{
		RequestMatcher l_found;
		for(RequestMatcher l_requestMatcher:requestMatchers){
			l_found=l_requestMatcher.matchRequest(p_request,sessionData);
			if(l_found != null) return l_found;
		}
		return null;
	}
	
	protected void redirectToPage(String p_url,ServletRequest p_request,ServletResponse p_response) throws ServletException, IOException
	{
		if(p_request instanceof HttpServletRequest){			
			HttpServletResponse l_httpResponse=(HttpServletResponse) p_response;		
			l_httpResponse.sendRedirect(((HttpServletRequest)p_request).getContextPath()+p_url);
		}
	}
	
	private void setupSession(HttpServletRequest p_request) throws NoSuchMethodException, ClassNotFoundException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException{
		HttpSession l_session=p_request.getSession(false);
		if(l_session != null){
			Object l_id=l_session.getAttribute("id");
			Object l_object=DynamicObject.createObjectFromName(sessionDataClass ,new Class<?>[]{Integer.class}, new Object[]{l_id});
			if(l_object instanceof SessionData){
				sessionData=(SessionData)l_object;
				
			} else {
				sessionData=null;
			}
		}
	}
	
	private void initSessionFromLogin(HttpServletRequest p_request,Map<String,Object> p_map) throws NoSuchMethodException, ClassNotFoundException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException{

		Object l_object=DynamicObject.createObjectFromName(sessionDataClass ,new Class<?>[]{Map.class}, new Object[]{p_map});
		if(l_object instanceof SessionData){
			
			sessionData=(SessionData)l_object;
			p_request.getSession(true).setAttribute("id",sessionData.getId());
		} else {
			sessionData=null;
		}		
	}
	
	public boolean execute(ServletRequest p_request,ServletResponse p_response) throws ServletException, IOException, SQLException, NoSuchMethodException, ClassNotFoundException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException
	{
		if(p_request instanceof HttpServletRequest){
			setupSession((HttpServletRequest)p_request);
		}
		RequestMatcher l_requestMatcher=matchRequest(p_request);
		if(l_requestMatcher==null){
			redirectToPage(loginPageUrl,p_request,p_response);			
			return false;
		}
		MatchActionResult l_result=l_requestMatcher.execute(p_request, p_response);
		if(l_result != MatchActionResult.SecurityFailed && p_request instanceof HttpServletRequest){
			HttpServletRequest l_request=(HttpServletRequest)p_request;
;
			if(l_request.getPathInfo().equals(loginCheckUrl)){
				if(authenticator != null){
					Map<String,Object> l_auth=authenticator.getAuthenicate(p_request);
					if(l_auth==null){
						l_result=MatchActionResult.SecurityFailed ;
					} else {
						initSessionFromLogin(l_request,l_auth);
						
						if(sessionData!=null){
							System.out.print("Redirect");
							redirectToPage(sessionData.getAfterLoginPath(),p_request,p_response);
							return false;
						} else {
							l_result=MatchActionResult.SecurityFailed;
						}
					}
				}
			}
		}
		switch(l_result){
			case NextFilter: return true;
			case NoNextFilter: return false;
			case SecurityFailed:
				redirectToPage(loginPageUrl,p_request,p_response);
				return false;
		}
		return false;
		
		
	}
	
	public void addRequestMatcher(RequestMatcher p_requestMatcher){
		requestMatchers.add(p_requestMatcher);
	}
}
