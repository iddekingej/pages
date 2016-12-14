package org.elaya.page.security;

import java.io.IOException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public abstract class Action {

	protected void redirect(HttpServletRequest prequest,HttpServletResponse presponse,String purl) throws IOException{
		presponse.sendRedirect(prequest.getContextPath()+purl);
	}
	
	protected AuthorisationData getSessionFromRequest(ServletRequest prequest)
	{
		Object object=prequest.getAttribute("org.elaya.page.security.SessionData");
		
		if((object != null) && (object instanceof AuthorisationData)){
			return (AuthorisationData)object;
		}

		return null;
	}
	
	public abstract ActionResult execute(ServletRequest prequest,ServletResponse presponse,Authenticator pauthenticator) throws Exception;
	
}
