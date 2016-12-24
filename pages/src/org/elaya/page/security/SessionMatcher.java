package org.elaya.page.security;

public class SessionMatcher extends RequestMatcher {

	public SessionMatcher() {
		super();
	}

	@Override
	boolean matchOwnRequest(Session session) {
		AuthorisationData sessionData=session.getAuthorisationData();
		return sessionData != null;
	}

}
