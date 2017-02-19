package org.elaya.page.filter;

import java.io.IOException;
import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.elaya.page.core.AuthorizationData;
import org.elaya.page.core.PageSession;



public abstract class Action {
	
	protected void redirect(HttpServletRequest prequest,HttpServletResponse presponse,String purl) throws IOException{
		presponse.sendRedirect(prequest.getContextPath()+purl);
	}
	
	protected AuthorizationData getSessionFromRequest(ServletRequest prequest)
	{
		Object object=prequest.getAttribute("org.elaya.page.security.SessionData");
		
		if((object != null) && (object instanceof AuthorizationData)){
			return (AuthorizationData)object;
		}

		return null;
	}
	
	public abstract ActionResult execute(PageSession session) throws ActionException;
	
}