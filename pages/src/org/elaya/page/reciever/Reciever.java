package org.elaya.page.reciever;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.LinkedList;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.elaya.page.Application;
import org.elaya.page.Errors;
import org.elaya.page.Errors.InvalidObjectType;
import org.elaya.page.data.Dynamic;
import org.elaya.page.data.DynamicMethod;
import org.elaya.page.data.DynamicObject;


abstract public class Reciever<T extends Dynamic> extends DynamicMethod {

	
	private Constructor<?> dataConstructor;
	private String dataClass;
	private LinkedList<Parameter> parameters=new LinkedList<Parameter>();
	private Application application;
	
	void setApplication(Application p_application)
	{
		application=p_application;
	}
	
	protected Application getApplication()
	{
			return application;
	}
	protected Dynamic getObject() throws NoSuchMethodException, ClassNotFoundException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, InvalidObjectType
	{ 
		dataConstructor=DynamicObject.getConstructorByName(dataClass,new Class<?>[]{},null);
		Object l_object=dataConstructor.newInstance();
		if(l_object instanceof Dynamic){
			return (Dynamic)l_object;
		}
		
		throw new Errors.InvalidObjectType(l_object,"DynamicData");
	}
	
	public void addParameter(Parameter p_parameter)
	{
		parameters.add(p_parameter);
	}
	
	public LinkedList<Parameter> getParameters()
	{
		return parameters;
	}
	
	
	public void setDataClass( String p_dataClass)
	{
		dataClass=p_dataClass;		
	}
	
	public  String getDataClass()
	{
		return dataClass;
	}
	
	

	abstract public void handleRequest(HttpServletRequest p_request,HttpServletResponse p_response ) throws Exception;
		
}
