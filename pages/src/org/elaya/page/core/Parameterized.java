package org.elaya.page.core;

import java.util.Map;

@FunctionalInterface
public interface Parameterized {
	/**
	 * Interface indicate objets send json data through json xmprpc
	 * With this interface default data can be set with <data> tag
	 * 
	 * @param \page\php\Map $p_data 
	 */
	void setParameters(Map<String,Object> data);
}
