package org.elaya.page.security;

import javax.servlet.ServletRequest;

public class SessionMatcher extends RequestMatcher {

	public SessionMatcher() {
		super();
	}

	@Override
	boolean matchOwnRequest(ServletRequest p_request, SessionData p_sessionData) {
		return (p_sessionData != null);
	}

}
