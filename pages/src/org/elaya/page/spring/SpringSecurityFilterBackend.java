package org.elaya.page.spring;

import org.elaya.page.security.SecurityFilterBackend;
import org.elaya.page.security.XmlSecurityParser;

public class SpringSecurityFilterBackend extends SecurityFilterBackend {

	public SpringSecurityFilterBackend() {
		super();
	}
	protected XmlSecurityParser newParser()
	{
		return new SpringXmlSecurityParser();
	}	

}
