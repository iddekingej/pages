package org.elaya.page.filter;

@FunctionalInterface
public interface HasRequestMatchers {
	public void addRequestMatcher(RequestMatcher prequestMatcher);
}
