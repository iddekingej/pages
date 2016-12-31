package org.elaya.page.reciever;

import java.io.IOException;
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
import org.json.JSONException;


public abstract  class Reciever<T extends Dynamic> extends DynamicMethod {
	public static class FatalFailureException extends Exception{
		private static final long serialVersionUID = -8412859746278588435L;
		public FatalFailureException(Throwable cause){
			super(cause);
		}
	}
	
	protected class RecieverData{
		private final T data;
		private final String cmd;
		
		public RecieverData(T pdata,String pcmd)
		{
			data=pdata;
			cmd=pcmd;
		}
		
		public T getData(){ return data;}
		public String getCmd(){ return cmd;}

	}
	
	private String dataClass;
	private LinkedList<Parameter> parameters=new LinkedList<>();
	private Application application;

	protected abstract void sendFailure(HttpServletResponse response,Exception e) throws JSONException, IOException;
	protected abstract RecieverData convertRequestToData(HttpServletRequest request,HttpServletResponse response) throws Exception;
	protected abstract void handleData(HttpServletResponse response,RecieverData data) throws  Exception;
	
	
	protected void validate(Result result,RecieverData data) throws JSONException, DynamicException
	{
		Object value;
		for(Parameter parameter:parameters){
			value=data.getData().get(parameter.getName());
			if(parameter.getIsMandatory() && (value==null||"".equals(value.toString()))){
				result.addError(parameter.getName(),"Is mandatory");
			}
		}
	}
	void setApplication(Application papplication)
	{
		application=papplication;
	}
	
	protected Application getApplication()
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
		parameters.add(parameter);
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
			 RecieverData data=convertRequestToData(request,response);
			 handleData(response,data);
		 } catch(Exception e){
			 failure(response,e);
		 }
	 }
		
}
