package org.elaya.page.filter;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.elaya.page.core.PageSession;

public class CompareMatcher extends RequestMatcher {
	
	private CompareMatchType matchType=CompareMatchType.EXACT;
	private String matchUrl="";
	private Pattern urlPattern ;
	private HashSet<String> methodFilter;
	
	public void setMethod(String pmethod)
	{
		if("".equals(pmethod)){
			methodFilter=null;
		} else {
			String[] methodList=pmethod.split(",");
			methodFilter=new HashSet<>();
			for(String methodName:methodList){
				methodFilter.add(methodName);
			}
		}
	}
	
	public Set<String> getMethod()
	{
		return methodFilter;
	}
	
	public void setType(CompareMatchType pmatchType){
		Objects.requireNonNull(pmatchType);
		matchType=pmatchType;
	}
	
	public CompareMatchType getMatchType(){
		return matchType;
	}
	
	public void setMatchUrl(String pmatchUrl)
	{
		urlPattern=null;
		matchUrl=pmatchUrl;
	}
	
	public String getMatchUrl(){
		return matchUrl;
	}
	
	private boolean matchRegex(String purl)
	{
		if(matchUrl==null || matchUrl==""){
			return true;
		}
		if(urlPattern==null){
			urlPattern=Pattern.compile(matchUrl);
		}			
	
		Matcher matcher=urlPattern.matcher(purl);
		return matcher.matches();
	}
	
	@Override
	public boolean matchOwnRequest(PageSession session) {
			if(methodFilter != null && !methodFilter.contains(session.getMethod())){
				return false;
			}
			
			String query=session.getPathInfo();
			if(query==null){
				query="";
			}
			switch(matchType){
				case EXACT:
					return query.equals(matchUrl);
				case STARTSWITH:
					return query.startsWith(matchUrl);
				case ENDSWITH:
					return query.endsWith(matchUrl);
				case REGEX:
					return matchRegex(query);
				default:
					return false;
			}
	
	}

}
