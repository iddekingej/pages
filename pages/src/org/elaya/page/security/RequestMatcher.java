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

	
	final public RequestMatcher matchRequest(ServletRequest p_request,SessionData p_sessionData){
		if(matchOwnRequest(p_request,p_sessionData)){
			RequestMatcher l_found;
			for(RequestMatcher l_sub:subMatcher){
				l_found=l_sub.matchRequest(p_request,p_sessionData);
				if(l_found != null) return l_found;
			}
			return this;
		} else {
			return null;
		}
	}
	abstract boolean matchOwnRequest(ServletRequest p_request,SessionData p_sessionData);
	
	public MatchActionResult execute(ServletRequest p_request,ServletResponse p_response){
		ActionResult l_result;
		boolean l_nextFilter=true;
		for(Action l_action:actions){
			l_result=l_action.execute(p_request, p_response);
			if(l_result==ActionResult.SecurityFailed) return MatchActionResult.SecurityFailed;
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
