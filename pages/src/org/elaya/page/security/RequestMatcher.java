package org.elaya.page.security;

import java.util.LinkedList;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

public abstract class RequestMatcher {
	private LinkedList<Action> actions=new  LinkedList<>();
	private LinkedList<RequestMatcher> subMatcher=new LinkedList<>(); 

	protected AuthorisationData getSessionFromRequest(ServletRequest prequest)
	{
		Object object=prequest.getAttribute("org.elaya.page.security.SessionData");
		if(object !=null && object instanceof AuthorisationData){
			return (AuthorisationData)object;
		}

		return null;
	}
	
	
	public final RequestMatcher matchRequest(ServletRequest prequest){
		if(matchOwnRequest(prequest)){
			RequestMatcher found;
			for(RequestMatcher sub:subMatcher){
				found=sub.matchRequest(prequest);
				if(found != null){
					return found;
				}
			}
			return this;
		} else {
			return null;
		}
	}
	abstract boolean matchOwnRequest(ServletRequest prequest);
	
	public MatchActionResult execute(ServletRequest prequest,ServletResponse presponse,Authenticator pauthenticator) throws Exception{
		ActionResult result;
		boolean nextFilter=true;
		for(Action action:actions){
			result=action.execute(prequest, presponse,pauthenticator);
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
