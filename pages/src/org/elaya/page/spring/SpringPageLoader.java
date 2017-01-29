package org.elaya.page.spring;

import org.elaya.page.PageLoader;
import org.elaya.page.application.Application;
import org.elaya.page.ElementParser;
import org.springframework.context.ApplicationContext;

public class SpringPageLoader extends PageLoader  {
	ApplicationContext applicationContext;

	protected void initUiParser(ElementParser pparser)
	{
		pparser.addInitializer(new ApplicationContextInitializer(applicationContext));
	}
	
	public SpringPageLoader(Application papplication,ApplicationContext papplicationContext){
		super(papplication);
		applicationContext=papplicationContext;
	}
	
	
}
