package org.elaya.page.security;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;

public class RegexMatcher extends RequestMatcher {
	private String urlRegex="";
	private Pattern  urlPattern=null;
	
	public void setUrlRegex(String p_urlRegex)
	{
		urlRegex=p_urlRegex;
		urlPattern=null;
	}
	
	public String getUrlRegex(){
		return urlRegex;
	}
	
	public RegexMatcher() {
		// TODO Auto-generated constructor stub
	}

	@Override
	boolean matchOwnRequest(ServletRequest p_request) {
		if(p_request instanceof HttpServletRequest){
			HttpServletRequest l_request=(HttpServletRequest)p_request;			
			if(urlRegex==null || urlRegex=="") return true;
			if(urlPattern==null){
				urlPattern=Pattern.compile(urlRegex);
			}			
			String l_url=l_request.getPathInfo();
			if(l_url==null) l_url="";
			Matcher l_matcher=urlPattern.matcher(l_url);
			return l_matcher.matches();
		}
		return false;
	}

}
