package org.elaya.page.security;

import java.io.IOException;
import java.sql.SQLException;
import java.util.LinkedList;

import javax.servlet.ServletRequest;
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

public abstract class RequestMatcher implements HasRequestMatchers{
	private LinkedList<Action> actions=new  LinkedList<>();
	private LinkedList<RequestMatcher> subMatcher=new LinkedList<>(); 

	protected AuthorizationData getSessionFromRequest(ServletRequest prequest)
	{
		Object object=prequest.getAttribute("org.elaya.page.security.SessionData");
		if(object !=null && object instanceof AuthorizationData){
			return (AuthorizationData)object;
		}

		return null;
	}
	
	
	public final RequestMatcher matchRequest(PageSession session){
		if(matchOwnRequest(session)){
			RequestMatcher found;
			for(RequestMatcher sub:subMatcher){
				found=sub.matchRequest(session);
				if(found != null){
					return found;
				}
			}
			return this;
		} else {
			return null;
		}
	}
	abstract boolean matchOwnRequest(PageSession session);
	
	public MatchActionResult execute(PageSession session) throws AuthenticationException, IOException, ReceiverException, XMLLoadException, SQLException, DefaultDBConnectionNotSet, KeyNotFoundException, ParserConfigurationException, SAXException, InvalidAliasType, AliasNotFound, LoadingAliasFailed, DisplayException {
		ActionResult result;
		boolean nextFilter=true;
		for(Action action:actions){
			result=action.execute(session);
			if(result==ActionResult.SECURITYFAILED){
				return MatchActionResult.SECURITYFAILED;
			}
			if(result==ActionResult.NOTAUTHORISED){
				return MatchActionResult.NOTAUTHORIZED;
			}
			if(result==ActionResult.STOPACTIONS){
				break;
			}
			if(result==ActionResult.NONEXTFILTER){
				nextFilter=false;
			}
		}
		return nextFilter?MatchActionResult.NEXTFILTER:MatchActionResult.NONEXTFILTER;
	}
	
	public void addAction(Action paction){
		actions.add(paction);
	}
	
	public void addRequestMatcher(RequestMatcher prequestMatcher){
		subMatcher.add(prequestMatcher);
	}
}
