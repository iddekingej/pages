package org.elaya.page.spring;

import org.elaya.page.data.DataModel;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.jdbc.core.JdbcTemplate;

public abstract class SpringDataModel extends DataModel implements ApplicationContextAware {
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
	
	public JdbcTemplate getJDBCDefaultTemplate() throws Exception
	{
			return new JdbcTemplate(getApplication().getDefaultDB());
	}
	
}
