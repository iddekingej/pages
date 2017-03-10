package org.elaya.page.filter;

import javax.servlet.ServletRequest;
import org.elaya.page.application.Application;
import org.elaya.page.application.PageApplicationAware;
import org.elaya.page.core.AuthorizationData;
import org.elaya.page.core.PageSession;

/**
 * A filter consist of a matcher(condition) and a action
 * When a request matches the condition of a matcher the action is executed. 
 *
 */

public abstract class Action implements PageApplicationAware{
	/**
	 * Object representing the web application
	 */
	private Application application;
	
	@Override
	public Application getApplication()
	{
		return application;
	}

	@Override
	public void setApplication(Application papplication)
	{
		application=papplication;
	}
	
	/**
	 * Get the session object from a request.
	 * 
	 * @param prequest
	 * @return
	 */
	protected AuthorizationData getSessionFromRequest(ServletRequest prequest)
	{
		Object object=prequest.getAttribute("org.elaya.page.security.SessionData");
		
		if((object != null) && (object instanceof AuthorizationData)){
			return (AuthorizationData)object;
		}

		return null;
	}
	
	public abstract ActionResult execute(PageSession session) throws ActionException;
	
}
