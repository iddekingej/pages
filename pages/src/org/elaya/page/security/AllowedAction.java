package org.elaya.page.security;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

public class AllowedAction extends Action {

	public AllowedAction() {
	}

	public ActionResult execute(ServletRequest p_request, ServletResponse p_response,Authenticator p_authenticator) 
	{
		return ActionResult.NextFilter;
	}

}
