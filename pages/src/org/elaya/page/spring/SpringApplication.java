package org.elaya.page.spring;

import javax.servlet.ServletContext;

import org.elaya.page.application.Application;
import org.springframework.web.context.ServletContextAware;

public class SpringApplication extends Application implements ServletContextAware {
	private ServletContext servletContext;
	public SpringApplication() {
		super();
	}

	@Override
	public void setServletContext(ServletContext p_servletContext) {
		servletContext=p_servletContext;
		servletContext.setAttribute("application",this);
	}

}
