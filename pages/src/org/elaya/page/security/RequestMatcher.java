package org.elaya.page.security;

import java.io.IOException;
import java.util.LinkedList;

import javax.servlet.ServletRequest;

import org.elaya.page.security.Errors.AuthenticationException;

public abstract class RequestMatcher {
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
	
	
	public final RequestMatcher matchRequest(Session session){
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
	abstract boolean matchOwnRequest(Session session);
	
	public MatchActionResult execute(Session session,Authenticator authenticator) throws AuthenticationException, IOException {
		ActionResult result;
		boolean nextFilter=true;
		for(Action action:actions){
			result=action.execute(session,authenticator);
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
