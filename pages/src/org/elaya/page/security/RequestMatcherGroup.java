package org.elaya.page.security;

import java.io.IOException;
import java.util.LinkedList;
import org.elaya.page.security.Errors.AuthenticationException;

public class RequestMatcherGroup implements HasRequestMatchers{
	private LinkedList<RequestMatcher> requestMatchers=new LinkedList<>();
	
	protected RequestMatcher matchRequest(Session session)
	{
		RequestMatcher found=null;
		for(RequestMatcher requestMatcher:requestMatchers){
			found=requestMatcher.matchRequest(session);			
			if(found != null){
				return found;
			}
		}
		return found;
	}	
	
	public MatchActionResult execute(Session psession) throws AuthenticationException, IOException 
	{
		RequestMatcher requestMatcher=matchRequest(psession);			
		return requestMatcher.execute(psession);				
	}
	
	public void addRequestMatcher(RequestMatcher prequestMatcher)
	{
		requestMatchers.add(prequestMatcher);
	}
}
