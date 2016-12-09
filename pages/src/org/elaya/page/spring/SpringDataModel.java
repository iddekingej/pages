package org.elaya.page.spring;

import org.elaya.page.data.DataModel;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.jdbc.core.JdbcTemplate;

abstract public class SpringDataModel extends DataModel implements ApplicationContextAware {
	private ApplicationContext applicationContext;
	
	public ApplicationContext getApplicationContext()
	{
		return applicationContext;
	}
	
	public SpringDataModel() {
		super();
	}

	@Override
	public void setApplicationContext(ApplicationContext p_applicationContext) throws BeansException {
		applicationContext=p_applicationContext;
	}

	public JdbcTemplate getJDBCTemplate(String p_name)
	{
			return new JdbcTemplate(getApplication().getDB(p_name));
	}
	
	public JdbcTemplate getJDBCDefaultTemplate() throws Exception
	{
			return new JdbcTemplate(getApplication().getDefaultDB());
	}
	
}
