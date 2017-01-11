package org.elaya.page.security;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

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
	public ActionResult execute(Session session) throws IOException{
		HttpServletRequest request=session.getHttpRequest();		
		if(request != null){			
			request.setAttribute("org.elaya.page.security.SessionData", null);
			session.setAuthorisationData(null);
			request.getSession().invalidate();
			if(redirectUrl != null){				
				session.redirect(redirectUrl);
			}
			return ActionResult.NONEXTFILTER;
		}
		return ActionResult.NEXTFILTER;
	}

}
