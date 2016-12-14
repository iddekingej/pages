package org.elaya.page.security;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;

public class CompareMatcher extends RequestMatcher {
	
	private CompareMatchType matchType;
	private String matchUrl="";
	
	public void setType(CompareMatchType pmatchType){
		matchType=pmatchType;
	}
	
	public CompareMatchType getMatchType(){
		return matchType;
	}
	
	public void setMatchUrl(String pmatchUrl)
	{
		matchUrl=pmatchUrl;
	}
	
	public String getMatchUrl(){
		return matchUrl;
	}
	
	@Override
	boolean matchOwnRequest(ServletRequest prequest) {
		if(prequest instanceof HttpServletRequest){
			String query=((HttpServletRequest)prequest).getPathInfo();
			if(query==null){
				query="";
			}
 			if(matchType==CompareMatchType.exact){
				return query.equals(matchUrl);
			} else if(matchType==CompareMatchType.startsWith){
				return query.startsWith(matchUrl);
			} else if(matchType==CompareMatchType.endsWith){
				return query.endsWith(matchUrl);
			}
		}
		return false;
	}

}
