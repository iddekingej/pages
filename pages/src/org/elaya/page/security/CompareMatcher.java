package org.elaya.page.security;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;

public class CompareMatcher extends RequestMatcher {
	

	
	private CompareMatchType matchType;
	private String matchUrl="";
	
	public void setType(CompareMatchType p_matchType){
		matchType=p_matchType;
	}
	
	public CompareMatchType getMatchType(){
		return matchType;
	}
	
	public void setMatchUrl(String p_matchUrl)
	{
		matchUrl=p_matchUrl;
	}
	
	public String getMatchUrl(){
		return matchUrl;
	}
	
	
	public CompareMatcher() {
	}

	@Override
	boolean matchOwnRequest(ServletRequest p_request) {
		if(p_request instanceof HttpServletRequest){
			String l_query=((HttpServletRequest)p_request).getPathInfo();
			if(l_query==null)l_query="";
 			if(matchType==CompareMatchType.exact){
				return l_query.equals(matchUrl);
			} else if(matchType==CompareMatchType.startsWith){
				return l_query.startsWith(matchUrl);
			} else if(matchType==CompareMatchType.endsWith){
				return l_query.endsWith(matchUrl);
			}
		}
		return false;
	}

}
