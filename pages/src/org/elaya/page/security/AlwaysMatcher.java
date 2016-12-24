package org.elaya.page.security;

public class AlwaysMatcher extends RequestMatcher {

	@Override
	boolean matchOwnRequest(Session session) {
		return true;
	}

}
