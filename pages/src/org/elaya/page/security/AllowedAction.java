package org.elaya.page.security;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

public class AllowedAction extends Action {

	public AllowedAction() {
		// TODO Auto-generated constructor stub
	}

	public ActionResult execute(ServletRequest p_request, ServletResponse p_response) {
		// TODO Auto-generated method stub
		return ActionResult.NextFilter;
	}

}
