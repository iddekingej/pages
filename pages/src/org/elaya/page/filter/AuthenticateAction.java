package org.elaya.page.filter;


import java.io.NotSerializableException;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;
import javax.servlet.http.HttpSession;
import org.elaya.page.core.AuthorizationData;
import org.elaya.page.core.DynamicObject;
import org.elaya.page.core.PageSession;
import org.elaya.page.filter.Errors.AuthenticationException;

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
	private Authenticator authenticator=null;
	private String nextUrl;
	
	/**
	 * After successful authentication, first the next URL from sessionDataClass
	 * is used. When this value is null, nextUrl from this object is used.
	 * 
	 * @return
	 */
	public String getNextUrl()
	{
		return nextUrl;
	}
	
	public void setNextUrl(String pnextUrl)
	{
		nextUrl=pnextUrl;
	}
	
	public void setAuthenticator(Authenticator pauthenticator)
	{
		authenticator=pauthenticator;
	}
	
	public Authenticator getAuthenticator()
	{
		return authenticator;
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
		
	protected void afterCreateSession(AuthorizationData pauthorisationData)
	{
		/*By default no setup us necessary */
	}
	
	/**
	 * When authentication is successful the session is created in this method.
	 *  
	 * @param session  Current session
	 * @param pdata    Data used to setup session    
	 * 
	 * @return A AuthorizationData object.
	 */
	private AuthorizationData createSessionData(PageSession session,Map<String,Object>pdata) throws InvalidSessionData, NoSuchMethodException, ClassNotFoundException, InstantiationException, IllegalAccessException,  InvocationTargetException, NotSerializableException{
		Object object=DynamicObject.createObjectFromName(sessionDataClass);
		if(object instanceof AuthorizationData){
			AuthorizationData authorisationData=(AuthorizationData)object;
			session.setAttribute("org.elaya.page.security.SessionData", object);
			afterCreateSession(authorisationData);
			authorisationData.initSessionData(pdata);
			
			return (AuthorizationData)object;
		} else {
			throw new InvalidSessionData("Sessiondata object (type="+object.getClass().getName()+") doesn't descent from SessionData");
		}	
	}
	
	/**
	 * Checks the authentication and setup the authentication and session object
	 * 
	 * @param session
	 * @return 
	 */
	private  ActionResult checkAuthentication(PageSession session) throws AuthenticationException
	{
		try{
			AuthorizationData sessionData;
			if(authenticator != null){
				Map<String,Object> auth=authenticator.getAuthenicate(session);
				if(auth==null){
					return ActionResult.SECURITYFAILED;
				}else {
					sessionData=createSessionData(session,auth);
					HttpSession httpSession=session.getHttpSession(true);
					httpSession.setAttribute("id", sessionData.getId());
					httpSession.setAttribute("type",sessionData.getClass().getName());
					String url=sessionData.getAfterLoginPath();
					if(url==null){
						url=nextUrl;
					}
					session.redirect(url);
					return ActionResult.NONEXTFILTER;
				}
			} else {
				if(failedLoginUrl != null && failedLoginUrl.length()>0){
					session.redirect(failedLoginUrl);
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
	public ActionResult execute(PageSession session) throws ActionException  
	{
		try{
			return checkAuthentication(session);
		}catch(Exception e){
			throw new ActionException(e);
		}
		
	}

}
