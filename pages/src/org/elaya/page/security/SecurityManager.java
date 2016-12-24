package org.elaya.page.security;

import java.io.NotSerializableException;
import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.util.LinkedList;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
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
	
	protected void afterCreateSession(AuthorisationData pauthorisationData)
	{
		
	}
	
	private AuthorisationData initAuthorisationData(Object objectId,Object typeObject,ServletRequest request) throws InvalidSessionData, NotSerializableException, InstantiationException, IllegalAccessException,  InvocationTargetException, NoSuchMethodException,  ClassNotFoundException
	{
		Serializable id;
		AuthorisationData authorisationData=null;
		if(objectId instanceof Serializable ){
			id=(Serializable)objectId;
		} else {
			throw new NotSerializableException("ID attribute of session is not Serializable (type="+objectId.getClass().getName()+")");
		}
		Object object=DynamicObject.createObjectFromName(typeObject.toString());
		if(object instanceof AuthorisationData){
			authorisationData=(AuthorisationData)object;
			request.setAttribute("org.elaya.page.security.SessionData", object);
			afterCreateSession(authorisationData);
			authorisationData.initSessionData(id);
		} else {
			throw new InvalidSessionData("Sessiondata object (type="+object.getClass().getName()+") doesn't descent from SessionData");
		}
		return authorisationData;
	}
	
	private Session createStoredSession(ServletRequest servletRequest,ServletResponse servletResponse) throws NoSuchMethodException, ClassNotFoundException, InstantiationException, IllegalAccessException,  InvocationTargetException, InvalidSessionData, NotSerializableException
	{
		Session session=new Session(servletRequest,servletResponse);
		HttpServletRequest request=session.getHttpRequest();		
		if(request != null){			
			HttpSession httpSession=request.getSession(false);
			if(httpSession != null){
				Object typeObject=httpSession.getAttribute("type");
				Object objectId=httpSession.getAttribute("id");
				if(objectId != null && typeObject != null){
					AuthorisationData authorisation=initAuthorisationData(objectId,typeObject,request);
					session.setAuthorisationData(authorisation);
				}
			}
		}
		return session;
	}
	
	
	public boolean execute(ServletRequest request,ServletResponse response) throws Exception
	{
		Session session=createStoredSession(request,response);
		RequestMatcher requestMatcher=matchRequest(session);
		if(requestMatcher==null){			
			session.redirect(loginPageUrl);			
			return false;
		}
		
		MatchActionResult result=requestMatcher.execute(session,authenticator);		
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
