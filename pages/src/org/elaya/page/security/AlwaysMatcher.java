package org.elaya.page.security;

import org.elaya.page.core.PageSession;

public class AlwaysMatcher extends RequestMatcher {

	@Override
	boolean matchOwnRequest(PageSession session) {
		return true;
	}

}
