package org.elaya.page.receiver;

import java.lang.reflect.InvocationTargetException;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.util.List;
import java.util.Map;

import org.elaya.page.Errors;
import org.elaya.page.Errors.InvalidObjectType;
import org.elaya.page.UniqueNamedObjectList;
import org.elaya.page.UniqueNamedObjectList.DuplicateItemName;
import org.elaya.page.data.Dynamic;
import org.elaya.page.data.Dynamic.DynamicException;
import org.elaya.page.data.DynamicObject;
import org.json.JSONException;

public class Command {
	private String cmd="";
	/**
	 * Method of the reciever class used to handle the request data
	 */
	private String handler;
	private String validation;
	/**
	 * 
	 */
	private String dataClass;
	private boolean single;
	private Set<String> cmdSet;
	private UniqueNamedObjectList<Parameter> parameters;
	private LinkedList<Validator> validators=new LinkedList<>();

	public void setHandler(String phandler)
	{
		handler=phandler;
	}
	
	public String getHandler()
	{
		return handler;
	}
	
	public void setValidation(String pvalidation){
		validation=pvalidation;
	}
	
	public String getValidation()
	{
		return validation;
	}
	
	public void setDataClass(String pdataClass)
	{
		dataClass=pdataClass;
	}
	
	public String getDataClass()
	{
		return dataClass;
	}
	
	public void setCmd(String pcmd)
	{
		cmd=pcmd;
		single=pcmd.indexOf(",")>=0;
	}
	
	public String getCmd()
	{
		return cmd;
	}
	
	/**
	 *  When  cmd contains multiple command (cmd is seperated by ","), the
	 *  string is split and converted to a set.
	 *  This is called when isCmd is called.
	 */
	private void makeCmdSet()
	{
		cmdSet=new HashSet<>();
		for(String l_item:cmd.split(",")){
			cmdSet.add(l_item);
		}		
	}
	public boolean isCmd(String pcmd)
	{
		if(cmd.isEmpty()){
			return true;
		}
		if(single){
			return cmd.equals(pcmd);
		}
		if(cmdSet==null){
			makeCmdSet();
		}
		return cmdSet.contains(pcmd);
	}
	
	public void addParameter(Parameter parameter) throws DuplicateItemName
	{
		parameters.put(parameter);
	}
	
	public UniqueNamedObjectList<Parameter> getParameters()
	{
		return parameters;
	}
	
	public void addValidator(Validator validator)
	{
		validators.add(validator);
	}

	public List<Validator> getValidators()
	{
		return validators;
	}
	
	public Dynamic getObject() throws InstantiationException, IllegalAccessException, InvocationTargetException, NoSuchMethodException, ClassNotFoundException, InvalidObjectType
	{
		Object object=DynamicObject.createObjectFromName(dataClass);
		if(object instanceof Dynamic){
			return (Dynamic)object;
		}
		throw new Errors.InvalidObjectType(object,"Dynamic");

	}
	
	protected void validate(Result result,ReceiverData data) throws DynamicException, JSONException
	{
		Object value;		
		String name;
		for(Map.Entry<String,Parameter> parEnt:parameters){
			name=parEnt.getKey();
			value=data.getData().get(name);
			parEnt.getValue().validate(result, value);
		}
		for(Validator validator:validators){
			validator.validate(result, data);
		}
	}
	
	protected void handleRequest(Object object,HttpServletRequest request,HttpServletResponse response,ReceiverData data,Result result) throws ClassNotFoundException, NoSuchMethodException, IllegalAccessException, InvocationTargetException, DynamicException, JSONException{
		Class<?> dataClassObject=Class.forName(dataClass);
		validate(result,data);
		if(result.hasErrors()){
			return;
		}
		if(validation != null){
			DynamicObject.call(object,validation,new Class<?>[]{String.class,dataClassObject,result.getClass()},new Object[]{data.getCmd(),data.getData(),result});
			if(result.hasErrors()){
				return;
			}
		}
		DynamicObject.call(object, handler, new Class<?>[]{HttpServletRequest.class,HttpServletResponse.class,String.class,dataClassObject,result.getClass()}, new Object[]{request,response,data.getCmd(),data.getData(),result});
	}
}
