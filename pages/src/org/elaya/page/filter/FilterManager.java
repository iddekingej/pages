package org.elaya.page.filter;

import java.io.IOException;
import java.io.NotSerializableException;
import java.lang.reflect.InvocationTargetException;
import java.util.LinkedList;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.elaya.page.core.PageSession;
import org.elaya.page.core.PageSession.InvalidSessionData;

/**
 * Manage the filters
 *
 */
public class FilterManager  {
	
	/**
	 * Url to which login data must be submitted
	 */
	private String loginPageUrl="";	
	/**
	 * The request filter consists of a matcher and a action. 
	 * The matchers are grouped in a requestMatchterGroup. 
	 */
	private LinkedList<RequestMatcherGroup> requestMatcherGroups=new LinkedList<>();


	public void setLoginPageUrl(String purl){
		loginPageUrl=purl;
	}
	
	public String getLoginPageUrl(){
		return loginPageUrl;
	}
	
	/**
	 * Create a pageSession from a request and response object
	 *  
	 * @param servletRequest
	 * @param servletResponse
	 * @return
	 */
	protected PageSession createSession(HttpServletRequest servletRequest,HttpServletResponse servletResponse) throws NotSerializableException, InstantiationException, IllegalAccessException, InvocationTargetException, NoSuchMethodException, ClassNotFoundException, InvalidSessionData  
	{
		PageSession session=new PageSession(servletRequest,servletResponse);
		session.initSession();
		return session;
	}
	
	/**
	 * Execute the defined filters
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	
	public boolean execute(ServletRequest request,ServletResponse response) throws InstantiationException, IllegalAccessException, InvocationTargetException, NoSuchMethodException, ClassNotFoundException, InvalidSessionData, ActionException, IOException 
	{
		if(!(request instanceof HttpServletRequest) || !(response instanceof HttpServletResponse)  ){
			return false;
		}
		PageSession session=createSession((HttpServletRequest)request,(HttpServletResponse)response);
		MatchActionResult result;
		boolean nextFilter=true;
		for(RequestMatcherGroup rmGroup:requestMatcherGroups){
			
			result=rmGroup.execute(session);
			if(result==MatchActionResult.NONEXTFILTER){
				
				nextFilter=false;
			} else if((result==MatchActionResult.NOTAUTHORIZED) ||
			   (result==MatchActionResult.SECURITYFAILED)){				
				session.redirect(loginPageUrl);
				return false;
			}
		}
		return nextFilter;
		
	}
	
	/**
	 * Add a requestMatcherGroup to the Filter manager
	 * @param prequestMatcherGroup
	 */
	public void addRequestMatcherGroup(RequestMatcherGroup prequestMatcherGroup){
		requestMatcherGroups.add(prequestMatcherGroup);
	}

}
