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
		public InvalidSessionData(String pmessage){
			super(pmessage);
		}
	}
	
	private String loginCheckUrl="";
	private String loginPageUrl="";
	private LinkedList<RequestMatcher> requestMatchers=new LinkedList<>();
	private Authenticator authenticator;
	
	public void setAuthenticator(Authenticator pauthenticator)
	{
			authenticator=pauthenticator;
	}
	
	public Authenticator getAuthenticator()
	{
		return authenticator;
	}

	

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
	
	protected RequestMatcher matchRequest(ServletRequest prequest)
	{
		RequestMatcher found=null;
		for(RequestMatcher requestMatcher:requestMatchers){
			found=requestMatcher.matchRequest(prequest);			
			if(found != null){
				return found;
			}
		}
		return found;
	}
	
	protected void redirectToPage(String purl,ServletRequest prequest,ServletResponse presponse) throws ServletException, IOException
	{
		if(prequest instanceof HttpServletRequest){			
			HttpServletResponse httpResponse=(HttpServletResponse) presponse;		
			httpResponse.sendRedirect(((HttpServletRequest)prequest).getContextPath()+purl);
		}
	}
	
	protected void afterCreateSession(AuthorisationData pauthorisationData)
	{
		
	}
	
	private void initSessionById(Object objectId,Object typeObject,ServletRequest request) throws InvalidSessionData, NotSerializableException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException, ClassNotFoundException
	{
		Serializable id;
		if(objectId instanceof Serializable ){
			id=(Serializable)objectId;
		} else {
			throw new NotSerializableException("ID attribute of session is not Serializable (type="+objectId.getClass().getName()+")");
		}
		Object object=DynamicObject.createObjectFromName(typeObject.toString());
		if(object instanceof AuthorisationData){
			AuthorisationData authorisationData=(AuthorisationData)object;
			request.setAttribute("org.elaya.page.security.SessionData", object);
			afterCreateSession(authorisationData);
			authorisationData.initSessionData(id);
		} else {
			throw new InvalidSessionData("Sessiondata object (type="+object.getClass().getName()+") doesn't descent from SessionData");
		}		
	}
	
	private void createStoredSession(ServletRequest servletRequest) throws NoSuchMethodException, ClassNotFoundException, InstantiationException, IllegalAccessException,  InvocationTargetException, InvalidSessionData, NotSerializableException
	{
		if(servletRequest instanceof HttpServletRequest){
			HttpServletRequest request=(HttpServletRequest)servletRequest;
			HttpSession session=request.getSession(false);
			if(session != null){
				Object typeObject=session.getAttribute("type");
				Object objectId=session.getAttribute("id");
				if(objectId != null && typeObject != null){
					initSessionById(objectId,typeObject,request);
				}
			}
		}
	}
	
	
	public boolean execute(ServletRequest request,ServletResponse response) throws Exception
	{
		createStoredSession(request);
		RequestMatcher requestMatcher=matchRequest(request);
		if(requestMatcher==null){			
			redirectToPage(loginPageUrl,request,response);			
			return false;
		}
		
		MatchActionResult result=requestMatcher.execute(request, response,authenticator);		
		switch(result){
			case NEXTFILTER: return true;
			case NONEXTFILTER: return false;
			case NOTAUTHORIZED:				
			case SECURITYFAILED:
				redirectToPage(loginPageUrl,request,response);
				return false;
		}
		return false;
		
		
	}
	
	public void addRequestMatcher(RequestMatcher prequestMatcher){
		requestMatchers.add(prequestMatcher);
	}
}
