package org.elaya.page.spring;

import org.elaya.page.application.Application.DefaultDBConnectionNotSet;
import org.elaya.page.data.XMLDataLayer;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.jdbc.core.JdbcTemplate;

public class SpringXMLDataLayer extends XMLDataLayer  implements ApplicationContextAware {
	private ApplicationContext applicationContext;
	
	public ApplicationContext getApplicationContext()
	{
		return applicationContext;
	}
	
	@Override
	public void setApplicationContext(ApplicationContext papplicationContext) throws BeansException {
		applicationContext=papplicationContext;
	}

	public JdbcTemplate getJDBCTemplate(String pname)
	{
			return new JdbcTemplate(getApplication().getDB(pname));
	}
	
	public JdbcTemplate getJDBCDefaultTemplate() throws DefaultDBConnectionNotSet 
	{
			return new JdbcTemplate(getApplication().getDefaultDB());
	}
}
