package org.elaya.page.security;

import javax.servlet.ServletRequest;

public class AlwaysMatcher extends RequestMatcher {

	public AlwaysMatcher() {
		super();
	}

	@Override
	boolean matchOwnRequest(ServletRequest p_request,SessionData p_sessionData) {
		return true;
	}

}
