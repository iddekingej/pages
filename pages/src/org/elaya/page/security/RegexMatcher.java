package org.elaya.page.security;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;

public class RegexMatcher extends RequestMatcher {
	private String urlRegex="";
	private Pattern  urlPattern=null;
	
	public void setUrlRegex(String purlRegex)
	{
		urlRegex=purlRegex;
		urlPattern=null;
	}
	
	public String getUrlRegex(){
		return urlRegex;
	}
	@Override
	boolean matchOwnRequest(ServletRequest prequest) {
		if(prequest instanceof HttpServletRequest){
			HttpServletRequest request=(HttpServletRequest)prequest;			
			if(urlRegex==null || urlRegex==""){
				return true;
			}
			if(urlPattern==null){
				urlPattern=Pattern.compile(urlRegex);
			}			
		
			String url=request.getPathInfo();
			if(url==null){
				url="";
			}
			
			Matcher matcher=urlPattern.matcher(url);
			return matcher.matches();			
		}
		return false;
	}

}
