package org.elaya.page.filter;

import org.elaya.page.core.PageSession;

/**
 * Logout the user (session information is cleared)
 *
 */
public class LogoutAction extends Action {

	/**
	 * To which url to redirect after logout
	 */
	private String redirectUrl;
	
	/**
	 * Set the redirect url (after logout)
	 */
	
	public void setRedirectUrl(String predirectUrl)
	{		
		redirectUrl=predirectUrl;
	}
	
	/**
	 * Get the url to redirect after logout
	 */
	
	public String getRedirectUrl()
	{
		return redirectUrl;
	}
	
	/**
	 * In this action, the session is cleared and the page is redirected to 
	 * the redirect url.
	 */
	@Override
	public ActionResult execute(PageSession session) throws ActionException{
		try{
			session.setAttribute("org.elaya.page.security.SessionData", null);
			session.setAuthorisationData(null);
			session.getHttpSession(true).invalidate();
			if(redirectUrl != null){				
				session.redirect(redirectUrl);
			}
			return ActionResult.NONEXTFILTER;
		}catch(Exception e){
			throw new ActionException(e);
		}
	}

}
