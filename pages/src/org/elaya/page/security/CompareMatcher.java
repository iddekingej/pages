package org.elaya.page.security;

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
	boolean matchOwnRequest(Session session) {
		HttpServletRequest request=session.getHttpRequest(); 
		if(request != null){
			String query=request.getPathInfo();
			if(query==null){
				query="";
			}
 			if(matchType==CompareMatchType.EXACT){
				return query.equals(matchUrl);
			} else if(matchType==CompareMatchType.STARTSWITH){
				return query.startsWith(matchUrl);
			} else if(matchType==CompareMatchType.ENDSWITH){
				return query.endsWith(matchUrl);
			}
		}
		return false;
	}

}
