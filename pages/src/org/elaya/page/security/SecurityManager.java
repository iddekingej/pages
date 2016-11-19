package org.elaya.page.security;

import java.io.IOException;
import java.util.LinkedList;

import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class SecurityManager {
	private String loginCheckUrl;
	private String loginPageUrl;
	private LinkedList<RequestMatcher> requestMatchers=new LinkedList<>();
	public SecurityManager() {
	}
	
	public void setLoginCheckUrl(String p_url){
		loginCheckUrl=p_url;
	}
	
	public String getLoginCheckUrl(){
		return loginCheckUrl;
	}
	
	public void setLoginPageUrl(String p_url){
		loginPageUrl=p_url;
	}
	
	public String getLoginPageUrl(){
		return loginPageUrl;
	}
	
	protected RequestMatcher matchRequest(ServletRequest p_request)
	{
		RequestMatcher l_found;
		for(RequestMatcher l_requestMatcher:requestMatchers){
			l_found=l_requestMatcher.matchRequest(p_request);
			if(l_found != null) return l_found;
		}
		return null;
	}
	
	protected void redirectToLoginPage(ServletRequest p_request,ServletResponse p_response) throws ServletException, IOException
	{
		if(p_request instanceof HttpServletRequest){			
			HttpServletResponse l_httpResponse=(HttpServletResponse) p_response;		
			l_httpResponse.sendRedirect(((HttpServletRequest)p_request).getContextPath()+loginPageUrl);
		}
	}
	
	public boolean execute(ServletRequest p_request,ServletResponse p_response) throws ServletException, IOException
	{
		RequestMatcher l_requestMatcher=matchRequest(p_request);
		if(l_requestMatcher==null){
			redirectToLoginPage(p_request,p_response);			
			return false;
		}
		MatchActionResult l_result=l_requestMatcher.execute(p_request, p_response);
		switch(l_result){
			case NextFilter: return true;
			case NoNextFilter: return false;
			case SecurityFailed:
				redirectToLoginPage(p_request,p_response);
				return false;
		}
		return false;
		
		
	}
	
	public void addRequestMatcher(RequestMatcher p_requestMatcher){
		requestMatchers.add(p_requestMatcher);
	}
}
