package org.elaya.page.receiver;

import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.elaya.page.Errors;
import org.elaya.page.Errors.InvalidObjectType;
import org.elaya.page.application.Application;
import org.elaya.page.application.Application.DefaultDBConnectionNotSet;
import org.elaya.page.application.PageApplicationAware;
import org.elaya.page.data.Dynamic;
import org.elaya.page.data.DynamicMethod;
import org.elaya.page.data.DynamicObject;
import org.json.JSONException;


public abstract  class Receiver<T extends Dynamic> extends DynamicMethod implements PageApplicationAware {
	
	public static class FatalFailureException extends Exception{
		private static final long serialVersionUID = -8412859746278588435L;
		public FatalFailureException(Throwable cause){
			super(cause);
		}
	}
	
	public static class DuplicateParameter extends RuntimeException{
		private static final long serialVersionUID = 103474339716771399L;
		public DuplicateParameter(String name)
		{
			super("Duplicate parameter '"+name+"'");
		}
		
	}
	
	private String dataClass;
	private HashMap<String,Parameter> parameters=new HashMap<>();
	private Application application;
	private LinkedList<Validator<T>> validators=new LinkedList<>();

	protected abstract void sendFailure(HttpServletResponse response,Exception e) throws JSONException, IOException;
	protected abstract ReceiverData<T> convertRequestToData(HttpServletRequest request,HttpServletResponse response) throws NoSuchMethodException, ClassNotFoundException, InstantiationException, IllegalAccessException, InvocationTargetException, InvalidObjectType, DynamicException, JSONException, IOException ;
	protected abstract void handleData(HttpServletResponse response,ReceiverData<T> data) throws JSONException, DynamicException, IOException, SQLException, DefaultDBConnectionNotSet ;
	
	public void addValidator(Validator<T> validator)
	{
		validators.add(validator);
	}
	
	public List<Validator<T>> getValidators()
	{
		return validators;
	}
	
	protected void validate(Result result,ReceiverData<T> data) throws DynamicException, JSONException
	{
		Object value;		
		String name;
		for(Map.Entry<String,Parameter> parEnt:parameters.entrySet()){
			name=parEnt.getKey();
			value=data.getData().get(name);
			parEnt.getValue().validate(result, value);
		}
		for(Validator<T> validator:validators){
			validator.validate(result, data);
		}
	}
	@Override
	public void setApplication(Application papplication)
	{
		application=papplication;
	}
	
	@Override
	public Application getApplication()
	{
			return application;
	}
	
	@SuppressWarnings("unchecked")
	protected T getObject() throws NoSuchMethodException, ClassNotFoundException, InstantiationException, IllegalAccessException,  InvocationTargetException, InvalidObjectType
	{ 
		Constructor<?> dataConstructor=DynamicObject.getConstructorByName(dataClass,new Class<?>[]{});
		Object object=dataConstructor.newInstance();
		if(object instanceof Dynamic){
			return (T)object;
		}
		
		throw new Errors.InvalidObjectType(object,"DynamicData");
	}
	
	public void addParameter(Parameter parameter)
	{
		if(parameter.containsKey(parameter.getName())){
			throw new DuplicateParameter(parameter.getName());
		}
		parameters.put(parameter.getName(),parameter);
	}
	
	public Map<String,Parameter> getParameters()
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
	
	public final void failure(HttpServletResponse response ,Exception e) throws FatalFailureException
	{
		try{
			sendFailure(response,e);
		} catch(Exception f){
			throw new FatalFailureException(f);
		}
	}
	
	

	public final void handleRequest(HttpServletRequest request,HttpServletResponse response) throws FatalFailureException{
		 try{
			 ReceiverData<T> data=convertRequestToData(request,response);
			 handleData(response,data);
		 } catch(Exception e){
			 failure(response,e);
		 }
	 }
		
}
