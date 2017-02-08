package org.elaya.page.security;

import java.io.IOException;
import java.sql.SQLException;
import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.parsers.ParserConfigurationException;
import org.elaya.page.Errors.AliasNotFound;
import org.elaya.page.Errors.LoadingAliasFailed;
import org.elaya.page.application.Application.DefaultDBConnectionNotSet;
import org.elaya.page.application.Application.InvalidAliasType;
import org.elaya.page.core.AuthorizationData;
import org.elaya.page.core.PageSession;
import org.elaya.page.data.Data.KeyNotFoundException;
import org.elaya.page.receiver.Receiver.ReceiverException;
import org.elaya.page.security.Errors.AuthenticationException;
import org.elaya.page.widget.Element.DisplayException;
import org.elaya.page.xml.XMLParserBase.XMLLoadException;
import org.xml.sax.SAXException;


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
	
	public abstract ActionResult execute(PageSession session) throws AuthenticationException, IOException, ReceiverException, XMLLoadException, SQLException, DefaultDBConnectionNotSet, KeyNotFoundException, ParserConfigurationException, SAXException, InvalidAliasType, AliasNotFound, LoadingAliasFailed, DisplayException ;
	
}
