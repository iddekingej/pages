package org.elaya.page.security;

import java.io.IOException;
import java.io.NotSerializableException;
import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.util.LinkedList;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.elaya.page.data.DynamicObject;

public class SecurityManager {
	
	class InvalidSessionData extends Exception{

		private static final long serialVersionUID = 1L;
		public InvalidSessionData(String p_message){
			super(p_message);
		}
	}
	
	private String loginCheckUrl="";
	private String loginPageUrl="";
	private LinkedList<RequestMatcher> requestMatchers=new LinkedList<>();
	private Authenticator authenticator;
	
	public void setAuthenticator(Authenticator p_authenticator)
	{
			authenticator=p_authenticator;
	}
	
	public Authenticator getAuthenticator()
	{
		return authenticator;
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
			l_found=l_requestMatcher.matchRequest(p_request);
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
	
	protected void afterCreateSession(AuthorisationData p_authorisationData)
	{
		
	}
	
	private void createStoredSession(ServletRequest p_request) throws NoSuchMethodException, ClassNotFoundException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, InvalidSessionData, NotSerializableException
	{
		if(p_request instanceof HttpServletRequest){
			HttpServletRequest l_request=(HttpServletRequest)p_request;
			HttpSession l_session=l_request.getSession(false);
			if(l_session != null){
				Object l_typeObject=l_session.getAttribute("type");
				Object l_objectId=l_session.getAttribute("id");
				if(l_objectId != null && l_typeObject != null){
					Serializable l_id;
					if(l_objectId instanceof Serializable ){
						l_id=(Serializable)l_objectId;
					} else {
						throw new NotSerializableException("ID attribute of session is not Serializable (type="+l_objectId.getClass().getName()+")");
					}
					Object l_object=DynamicObject.createObjectFromName(l_typeObject.toString());
					if(l_object instanceof AuthorisationData){
						AuthorisationData l_authorisationData=(AuthorisationData)l_object;
						p_request.setAttribute("org.elaya.page.security.SessionData", l_object);
						afterCreateSession(l_authorisationData);
						l_authorisationData.initSessionData(l_id);
					} else {
						throw new InvalidSessionData("Sessiondata object (type="+l_object.getClass().getName()+") doesn't descent from SessionData");
					}			
				}
			}
		}
	}
	
	
	public boolean execute(ServletRequest p_request,ServletResponse p_response) throws Exception
	{
		createStoredSession(p_request);
		RequestMatcher l_requestMatcher=matchRequest(p_request);
		if(l_requestMatcher==null){			
			redirectToPage(loginPageUrl,p_request,p_response);			
			return false;
		}
		
		MatchActionResult l_result=l_requestMatcher.execute(p_request, p_response,authenticator);		
		switch(l_result){
			case NextFilter: return true;
			case NoNextFilter: return false;
			case NotAuthorised:				
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
