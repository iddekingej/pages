package org.elaya.page.security;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

public abstract class Action {

	
	public Action() {
		// TODO Auto-generated constructor stub
	}

	abstract public ActionResult execute(ServletRequest p_request,ServletResponse p_response);
	
}
