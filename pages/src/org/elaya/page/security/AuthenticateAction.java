package org.elaya.page.security;


import java.io.NotSerializableException;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.elaya.page.data.DynamicObject;
import org.elaya.page.security.Errors.AuthenticationException;

/**
 * Handle authentication. A authentication token(username/password for example) is posted to a url
 * The request is intercepted by a filter  and the AuthenticaeteAction is used to check the authentication and
 * start a session 
 *
 */
public class AuthenticateAction extends Action {

	class InvalidSessionData extends Exception{

		private static final long serialVersionUID = 1L;
		public InvalidSessionData(String pmessage){
			super(pmessage);
		}
	}
	
	
	
	private String sessionDataClass;
	private String failedLoginUrl="";
	
	public AuthenticateAction() {
		super();
	}
	
	public void setSessionDataClass(String psessionDataClass)
	{
		sessionDataClass=psessionDataClass;
	}
	
	public String getSessionDataClass()
	{
		return sessionDataClass;
	}
	
	/**
	 * Set redirection url after failed login. When URL is not set a "SecurityFailed"
	 * status is returned
	 * 
	 * @param pfailedLoginUrl - URL to redirect after failed authentication
	 */
	public void setFailedLoginUrl(String pfailedLoginUrl)
	{
		failedLoginUrl=pfailedLoginUrl;
	}
	
	public String getFailedLoginUrl()
	{
		return failedLoginUrl;
	}
		
	protected void afterCreateSession(AuthorisationData pauthorisationData)
	{
		/*By default no setup us necessary */
	}
	
	private AuthorisationData createSessionDataGen(ServletRequest prequest,Map<String,Object>pdata) throws InvalidSessionData, NoSuchMethodException, ClassNotFoundException, InstantiationException, IllegalAccessException,  InvocationTargetException, NotSerializableException{
		Object object=DynamicObject.createObjectFromName(sessionDataClass);
		if(object instanceof AuthorisationData){
			AuthorisationData authorisationData=(AuthorisationData)object;
			prequest.setAttribute("org.elaya.page.security.SessionData", object);
			afterCreateSession(authorisationData);
			authorisationData.initSessionData(pdata);
			
			return (AuthorisationData)object;
		} else {
			throw new InvalidSessionData("Sessiondata object (type="+object.getClass().getName()+") doesn't descent from SessionData");
		}	
	}
	
	

	
	protected AuthorisationData createSessionData(ServletRequest prequest,Map<String,Object> pdata) throws NoSuchMethodException, ClassNotFoundException, InstantiationException, IllegalAccessException,  InvocationTargetException, InvalidSessionData, NotSerializableException{
		return createSessionDataGen(prequest,pdata);
	}
	
	private  ActionResult checkAuthentication(ServletRequest prequest,ServletResponse presponse,Authenticator pauthenticator) throws AuthenticationException
	{
		try{
			HttpServletRequest request=(HttpServletRequest)prequest;
			AuthorisationData sessionData;
			if(pauthenticator != null){
				Map<String,Object> auth=pauthenticator.getAuthenicate(prequest);
				if(auth==null){
					return ActionResult.SECURITYFAILED;
				}else {
					sessionData=createSessionData(prequest,auth);
					HttpSession session=request.getSession();
					session.setAttribute("id", sessionData.getId());
					session.setAttribute("type",sessionData.getClass().getName());
					redirect(request,(HttpServletResponse)presponse,sessionData.getAfterLoginPath());
					return ActionResult.NONEXTFILTER;
				}
			} else {
				if(failedLoginUrl != null && failedLoginUrl.length()>0){
					redirect(request,(HttpServletResponse)presponse,failedLoginUrl);
				} else {
					return ActionResult.SECURITYFAILED;
				}
			}		
		} catch(Exception e){
			throw new Errors.AuthenticationException(e);	
		}
		return ActionResult.SECURITYFAILED;
	}
	
	@Override
	public ActionResult execute(ServletRequest prequest, ServletResponse presponse,Authenticator pauthenticator) throws AuthenticationException 
	{
			if(prequest instanceof HttpServletRequest ){
				return checkAuthentication(prequest,presponse,pauthenticator);
			} 

			return ActionResult.SECURITYFAILED;//TODO: Raise exception?
		
	}

}
