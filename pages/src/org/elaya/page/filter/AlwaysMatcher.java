package org.elaya.page.filter;

import org.elaya.page.core.PageSession;

public class AlwaysMatcher extends RequestMatcher {

	@Override
	boolean matchOwnRequest(PageSession session) {
		return true;
	}

}
