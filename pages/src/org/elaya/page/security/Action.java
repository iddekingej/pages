package org.elaya.page.security;

import java.io.IOException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public abstract class Action {

	
	public Action() {
		// TODO Auto-generated constructor stub
	}

	protected void redirect(HttpServletRequest p_request,HttpServletResponse p_response,String p_url) throws IOException{
		p_response.sendRedirect(p_request.getContextPath()+p_url);
	}
	
	protected SessionData getSessionFromRequest(ServletRequest p_request)
	{
		Object l_object=p_request.getAttribute("org.elaya.page.security.SessionData");
		if(l_object != null){
			if(l_object instanceof SessionData){
				return (SessionData)l_object;
			}
		}
		return null;
	}
	
	abstract public ActionResult execute(ServletRequest p_request,ServletResponse p_response,Authenticator p_authenticator) throws Exception;
	
}
