package org.elaya.page.spring;

import org.elaya.page.PageLoader;
import org.elaya.page.UiXmlParser;
import org.springframework.context.ApplicationContext;

public class SpringPageLoader extends PageLoader  {
	ApplicationContext applicationContext;

	protected void initUiParser(UiXmlParser pparser)
	{
		pparser.addInitializer(new ApplicationContextInitializer(applicationContext));
	}
	
	public SpringPageLoader(ApplicationContext papplicationContext){
		applicationContext=papplicationContext;
	}
	
	
}
