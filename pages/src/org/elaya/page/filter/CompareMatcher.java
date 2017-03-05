package org.elaya.page.filter;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.elaya.page.core.PageSession;

/**
 * Matches the use by comparing it to a matchUrl. 
 *
 */
public class CompareMatcher extends RequestMatcher {
	
	private CompareMatchType matchType=CompareMatchType.EXACT;
	private String matchUrl="";
	private Pattern urlPattern ;
	private HashSet<String> methodFilter;
	/**
	 * Set the request method (put,post etc..) filter. 
	 * This is a comma separated list of  methods to match
	 * An empty string means all methods.
	 * 
	 * @param pmethod
	 */
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
	
	/**
	 * The http request should match the methods inside this Set.
	 * This method can return nul. 
	 * @return
	 */
	public Set<String> getMethod()
	{
		return methodFilter;
	}
	
	/**
	 * Set how to match the  url:
	 * EXACT - url must match exactly
	 * STARTSWITH - url starts with  thegiven value	
	 * ENDSWITH - url ends with the given value
	 * REGEX - Use regular matching
	 * @param pmatchType
	 */
	public void setMatchType(CompareMatchType pmatchType){
		Objects.requireNonNull(pmatchType);
		matchType=pmatchType;
	}
	
	/**
	 * Get how the url is match
	 * @return
	 */
	public CompareMatchType getMatchType(){
		return matchType;
	}
	
	/**
	 * The url is match to this url.
	 * 
	 * @param pmatchUrl
	 */
	public void setMatchUrl(String pmatchUrl)
	{
		urlPattern=null;
		matchUrl=pmatchUrl;
	}
	
	/**
	 * Get the url text to which the request is matched.
	 * @return 
	 */
	public String getMatchUrl(){
		return matchUrl;
	}
	
	/**
	 * Uses regex to match the url. The compiled regex is cached
	 * 
	 * @param purl
	 * @return true:The url matches the given matchUrl false: doesn't match
	 */
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
	
	/**
	 * Matches the request by url
	 * @Return true:The url matches the given matchUrl false: doesn't match
	 */
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
					System.out.println(query+" ** "+matchUrl);
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
