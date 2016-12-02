package org.elaya.page.spring;

import org.elaya.page.data.DataModel;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

abstract public class SpringDataModel extends DataModel implements ApplicationContextAware {
	private ApplicationContext applicationContext;
	private ClassPathXmlApplicationContext DBContext=null;
	
	public ApplicationContext getApplicationContext()
	{
		return applicationContext;
	}
	
	public SpringDataModel() {
		super();
	}

	public DriverManagerDataSource getDB(String p_name)
	{
		if(DBContext ==null){
			DBContext=new ClassPathXmlApplicationContext("../database/database.xml");
		}
		return DBContext.getBean(p_name,DriverManagerDataSource.class);
	}
	
	@Override
	public void setApplicationContext(ApplicationContext p_applicationContext) throws BeansException {
		applicationContext=p_applicationContext;
	}

}
