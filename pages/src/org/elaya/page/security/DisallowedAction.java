package org.elaya.page.security;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

public class DisallowedAction extends Action {

	@Override
	public ActionResult execute(ServletRequest prequest, ServletResponse presponse, Authenticator pauthenticator)	 {
		return ActionResult.NOTAUTHORISED;
	}

}
