package org.elaya.page.security;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
	public ActionResult execute(ServletRequest prequest, ServletResponse presponse, Authenticator pauthenticator)
			throws Exception {
		if(prequest instanceof HttpServletRequest){
			HttpServletRequest request=(HttpServletRequest)prequest;
			request.setAttribute("org.elaya.page.security.SessionData", null);
			request.getSession().invalidate();
			HttpServletResponse httpResponse=(HttpServletResponse) presponse;		
			if(redirectUrl != null){
				httpResponse.sendRedirect(((HttpServletRequest)prequest).getContextPath()+redirectUrl);
			}
			return ActionResult.NONEXTFILTER;
		}
		return ActionResult.NEXTFILTER;
	}

}
