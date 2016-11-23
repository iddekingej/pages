package org.elaya.page.security;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class LogoutAction extends Action {

	private String redirectUrl;
	
	public void setRedirectUrl(String p_redirectUrl)
	{		
		redirectUrl=p_redirectUrl;
	}
	
	public String getRedirectUrl()
	{
		return redirectUrl;
	}
	
	public LogoutAction() {
		super();
	}

	@Override
	public ActionResult execute(ServletRequest p_request, ServletResponse p_response, Authenticator p_authenticator)
			throws Exception {
		if(p_request instanceof HttpServletRequest){
			HttpServletRequest l_request=(HttpServletRequest)p_request;
			l_request.setAttribute("org.elaya.page.security.SessionData", null);
			l_request.getSession().invalidate();
			HttpServletResponse l_httpResponse=(HttpServletResponse) p_response;		
			if(redirectUrl != null){
				l_httpResponse.sendRedirect(((HttpServletRequest)p_request).getContextPath()+redirectUrl);
			}
			return ActionResult.NoNextFilter;
		}
		return ActionResult.NextFilter;
	}

}
