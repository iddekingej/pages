package org.elaya.page.reciever;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.LinkedList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.elaya.page.Errors;
import org.elaya.page.Errors.InvalidObjectType;
import org.elaya.page.application.Application;
import org.elaya.page.data.Dynamic;
import org.elaya.page.data.DynamicMethod;
import org.elaya.page.data.DynamicObject;


public abstract  class Reciever<T extends Dynamic> extends DynamicMethod {
	private String dataClass;
	private List<Parameter> parameters=new LinkedList<>();
	private Application application;

	void setApplication(Application papplication)
	{
		application=papplication;
	}
	
	protected Application getApplication()
	{
			return application;
	}
	protected Dynamic getObject() throws NoSuchMethodException, ClassNotFoundException, InstantiationException, IllegalAccessException,  InvocationTargetException, InvalidObjectType
	{ 
		Constructor<?> dataConstructor=DynamicObject.getConstructorByName(dataClass,new Class<?>[]{});
		Object object=dataConstructor.newInstance();
		if(object instanceof Dynamic){
			return (Dynamic)object;
		}
		
		throw new Errors.InvalidObjectType(object,"DynamicData");
	}
	
	public void addParameter(Parameter pparameter)
	{
		parameters.add(pparameter);
	}
	
	public List<Parameter> getParameters()
	{
		return parameters;
	}
	
	
	public void setDataClass( String pdataClass)
	{
		dataClass=pdataClass;		
	}
	
	public  String getDataClass()
	{
		return dataClass;
	}
	
	 public abstract void handleRequest(HttpServletRequest prequest,HttpServletResponse presponse ) throws  Throwable;
		
}
