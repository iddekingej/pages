package org.elaya.page.security;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

public class DisallowedAction extends Action {

	public DisallowedAction() {
		super();
	}

	@Override
	public ActionResult execute(ServletRequest p_request, ServletResponse p_response, Authenticator p_authenticator)
			throws Exception {
		return ActionResult.SecurityFailed;
	}

}
