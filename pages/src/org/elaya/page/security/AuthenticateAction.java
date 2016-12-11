package org.elaya.page.security;

import java.io.IOException;
import java.io.NotSerializableException;
import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.util.Map;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.elaya.page.data.DynamicObject;

/**
 * Handle authentication. A authentication token(username/password for example) is posted to a url
 * The request is intercepted by a filter  and the AuthenticaeteAction is used to check the authentication and
 * start a session 
 *
 */
public class AuthenticateAction extends Action {

	class InvalidSessionData extends Exception{

		private static final long serialVersionUID = 1L;
		public InvalidSessionData(String p_message){
			super(p_message);
		}
	}
	
	private String sessionDataClass;
	private String failedLoginUrl="";
	
	public AuthenticateAction() {
		super();
	}
	
	public void setSessionDataClass(String p_sessionDataClass)
	{
		sessionDataClass=p_sessionDataClass;
	}
	
	public String getSessionDataClass()
	{
		return sessionDataClass;
	}
	
	/**
	 * Set redirection url after failed login. When URL is not set a "SecurityFailed"
	 * status is returned
	 * 
	 * @param p_failedLoginUrl - URL to redirect after failed authentication
	 */
	public void setFailedLoginUrl(String p_failedLoginUrl)
	{
		failedLoginUrl=p_failedLoginUrl;
	}
	
	public String getFailedLoginUrl()
	{
		return failedLoginUrl;
	}
		
	protected void afterCreateSession(AuthorisationData p_authorisationData)
	{
		/*By default no setup us necessary */
	}
	
	private AuthorisationData createSessionDataGen(ServletRequest p_request,Class<?> p_type,Map<String,Object>p_data) throws InvalidSessionData, NoSuchMethodException, ClassNotFoundException, InstantiationException, IllegalAccessException,  InvocationTargetException, NotSerializableException{
		Object l_object=DynamicObject.createObjectFromName(sessionDataClass);
		if(l_object instanceof AuthorisationData){
			AuthorisationData l_authorisationData=(AuthorisationData)l_object;
			p_request.setAttribute("org.elaya.page.security.SessionData", l_object);
			afterCreateSession(l_authorisationData);
			l_authorisationData.initSessionData(p_data);
			
			return (AuthorisationData)l_object;
		} else {
			throw new InvalidSessionData("Sessiondata object (type="+l_object.getClass().getName()+") doesn't descent from SessionData");
		}	
	}
	
	

	
	protected AuthorisationData createSessionData(ServletRequest p_request,Map<String,Object> p_data) throws NoSuchMethodException, ClassNotFoundException, InstantiationException, IllegalAccessException,  InvocationTargetException, InvalidSessionData, NotSerializableException{
		return createSessionDataGen(p_request,Map.class,p_data);
	}
	
		
	@Override
	public ActionResult execute(ServletRequest p_request, ServletResponse p_response,Authenticator p_authenticator) throws ClassNotFoundException, SQLException, IOException, NoSuchMethodException, InstantiationException, IllegalAccessException,  InvocationTargetException, InvalidSessionData 
	{
		if(p_request instanceof HttpServletRequest ){
			HttpServletRequest l_request=(HttpServletRequest)p_request;
			AuthorisationData l_sessionData;
			if(p_authenticator != null){
				Map<String,Object> l_auth=p_authenticator.getAuthenicate(p_request);
				if(l_auth==null){
						return ActionResult.SecurityFailed;
				}else {
					l_sessionData=createSessionData(p_request,l_auth);
					HttpSession l_session=l_request.getSession();
					l_session.setAttribute("id", l_sessionData.getId());
					l_session.setAttribute("type",l_sessionData.getClass().getName());
					redirect(l_request,(HttpServletResponse)p_response,l_sessionData.getAfterLoginPath());
					return ActionResult.NoNextFilter;
				}
			} else {
				if(failedLoginUrl != null && failedLoginUrl.length()>0){
					redirect(l_request,(HttpServletResponse)p_response,failedLoginUrl);
				} else {
					return ActionResult.SecurityFailed;
				}
			}
		} 
		
		return ActionResult.SecurityFailed;//TODO: Raise exception?
	}

}
