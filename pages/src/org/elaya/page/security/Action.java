package org.elaya.page.security;

import java.io.IOException;
import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.elaya.page.receiver.Receiver.ReceiverException;
import org.elaya.page.security.Errors.AuthenticationException;
import org.elaya.page.xml.XMLParserBase.XMLLoadException;


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
	
	public abstract ActionResult execute(Session session) throws AuthenticationException, IOException, ReceiverException, XMLLoadException ;
	
}
