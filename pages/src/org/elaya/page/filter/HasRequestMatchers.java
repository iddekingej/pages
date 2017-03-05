package org.elaya.page.filter;
/**
 * Indicate that the object can contain request matchers 
  *
 */
@FunctionalInterface
public interface HasRequestMatchers {
	public void addRequestMatcher(RequestMatcher prequestMatcher);
}
