package org.elaya.page.security;

@FunctionalInterface
public interface HasRequestMatchers {
	public void addRequestMatcher(RequestMatcher prequestMatcher);
}
