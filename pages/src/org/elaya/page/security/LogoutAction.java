package org.elaya.page.security;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

import org.elaya.page.core.PageSession;

public class LogoutAction extends Action {

	private String redirectUrl;
	
	public void setRedirectUrl(String predirectUrl)
	{		
		redirectUrl=predirectUrl;
	}
	
	public String getRedirectUrl()
	{
		return redirectUrl;
	}
	@Override
	public ActionResult execute(PageSession session) throws IOException{
		session.setAttribute("org.elaya.page.security.SessionData", null);
		session.setAuthorisationData(null);
		session.getHttpSession(true).invalidate();
		if(redirectUrl != null){				
			session.redirect(redirectUrl);
		}
		return ActionResult.NONEXTFILTER;
	}

}
