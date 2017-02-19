package org.elaya.page.filter;

import java.util.LinkedList;
import org.elaya.page.core.PageSession;


public class RequestMatcherGroup implements HasRequestMatchers{
	private LinkedList<RequestMatcher> requestMatchers=new LinkedList<>();
	
	protected RequestMatcher matchRequest(PageSession session)
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
	
	public MatchActionResult execute(PageSession psession) throws ActionException 
	{
		RequestMatcher requestMatcher=matchRequest(psession);	
		if(requestMatcher==null){
			return MatchActionResult.NEXTFILTER;
		}
		return requestMatcher.execute(psession);				
	}
	
	@Override
	public void addRequestMatcher(RequestMatcher prequestMatcher)
	{
		requestMatchers.add(prequestMatcher);
	}
}
