package org.elaya.page.security;

import javax.servlet.ServletRequest;

public class AlwaysMatcher extends RequestMatcher {

	@Override
	boolean matchOwnRequest(ServletRequest prequest) {
		return true;
	}

}
