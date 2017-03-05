package org.elaya.page.filter;

import org.elaya.page.core.PageSession;

/**
 * This object matches always
 * 
 */
public class AlwaysMatcher extends RequestMatcher {

	@Override
	boolean matchOwnRequest(PageSession session) {
		return true;
	}

}
