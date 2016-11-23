package org.elaya.page.security;

import java.util.LinkedList;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

public abstract class RequestMatcher {
	private LinkedList<Action> actions=new  LinkedList<>();
	private LinkedList<RequestMatcher> subMatcher=new LinkedList<>(); 
	
	public RequestMatcher() {
		// TODO Auto-generated constructor stub
	}
	
	protected AuthorisationData getSessionFromRequest(ServletRequest p_request)
	{
		Object l_object=p_request.getAttribute("org.elaya.page.security.SessionData");
		if(l_object != null){
			if(l_object instanceof AuthorisationData){
				return (AuthorisationData)l_object;
			}
		}
		return null;
	}
	
	
	final public RequestMatcher matchRequest(ServletRequest p_request){
		if(matchOwnRequest(p_request)){
			RequestMatcher l_found;
			for(RequestMatcher l_sub:subMatcher){
				l_found=l_sub.matchRequest(p_request);
				if(l_found != null) return l_found;
			}
			return this;
		} else {
			return null;
		}
	}
	abstract boolean matchOwnRequest(ServletRequest p_request);
	
	public MatchActionResult execute(ServletRequest p_request,ServletResponse p_response,Authenticator p_authenticator) throws Exception{
		ActionResult l_result;
		boolean l_nextFilter=true;
		for(Action l_action:actions){
			l_result=l_action.execute(p_request, p_response,p_authenticator);
			if(l_result==ActionResult.SecurityFailed) return MatchActionResult.SecurityFailed;
			if(l_result==ActionResult.NotAuthorised) return MatchActionResult.NotAuthorised;
			if(l_result==ActionResult.StopActions) break;
			if(l_result==ActionResult.NoNextFilter) l_nextFilter=false;
		}
		return l_nextFilter?MatchActionResult.NextFilter:MatchActionResult.NoNextFilter;
	}
	
	public void addAction(Action p_action){
		actions.add(p_action);
	}
	
	public void addRequestMatcher(RequestMatcher p_requestMatcher){
		subMatcher.add(p_requestMatcher);
	}
}
