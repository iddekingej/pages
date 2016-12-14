package org.elaya.page.security;

import javax.servlet.ServletRequest;

public class SessionMatcher extends RequestMatcher {

	public SessionMatcher() {
		super();
	}

	@Override
	boolean matchOwnRequest(ServletRequest prequest) {
		AuthorisationData sessionData=getSessionFromRequest(prequest);
		return sessionData != null;
	}

}
