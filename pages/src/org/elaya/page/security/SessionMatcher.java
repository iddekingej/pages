package org.elaya.page.security;

import javax.servlet.ServletRequest;

public class SessionMatcher extends RequestMatcher {

	public SessionMatcher() {
		super();
	}

	@Override
	boolean matchOwnRequest(ServletRequest p_request) {
		SessionData l_sessionData=getSessionFromRequest(p_request);
		return l_sessionData != null;
	}

}
