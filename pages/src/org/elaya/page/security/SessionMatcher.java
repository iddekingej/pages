package org.elaya.page.security;

import org.elaya.page.core.AuthorizationData;
import org.elaya.page.core.PageSession;

public class SessionMatcher extends RequestMatcher {

	public SessionMatcher() {
		super();
	}

	@Override
	boolean matchOwnRequest(PageSession session) {
		AuthorizationData sessionData=session.getAuthorisationData();
		return sessionData != null;
	}

}
