package org.elaya.page.receiver;

import java.io.BufferedReader;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.util.Map;
import org.elaya.page.Errors.InvalidObjectType;
import org.elaya.page.application.Application.DefaultDBConnectionNotSet;
import org.elaya.page.core.Dynamic;
import org.elaya.page.core.PageSession;
import org.json.JSONException;
import org.json.JSONObject;

public abstract class JsonReceiver extends Receiver {


	private JSONObject getJson(PageSession psession) throws IOException, JSONException
	{
		StringBuilder data=new StringBuilder();
		String part;
		BufferedReader reader=psession.getReader(); 
		while((part=reader.readLine())!= null){
			data.append(part);
		}
		return new JSONObject(data.toString());
	}
	
	
	@Override
	protected void sendFailure(PageSession psession,Exception e) throws JSONException, IOException
	{
		JSONResult result=new JSONResult();
		result.addError("", "Internal error:"+e.toString());
		e.printStackTrace();
		psession.setContentType("application/json");
		psession.getOutputStream().print(result.toString());
	}
	
	
	private Object convertValue(Object inputValue,Parameter parameter)
	{
		Object value=inputValue;
		if(parameter.getType()==ParameterType.BOOLEAN){
			if(value !=null && (value.equals(1)||"1".equals(value))){
				value=true;
			}else {
				value=false;
			}
		} else if(parameter.getType()==ParameterType.INTEGER && "".equals(value)){
			value=null;
		}
		return value;
	}

	@Override
	protected final  void handleData(PageSession psession) throws IOException, ClassNotFoundException, NoSuchMethodException, IllegalAccessException, InvocationTargetException, DynamicException, JSONException, InstantiationException, InvalidObjectType, ReceiverException, DefaultDBConnectionNotSet, SQLException 
	{
		JSONObject json=getJson(psession);
		String cmd=json.getString(getCmdField());
		Command command=getCommand(cmd);
		if(command==null){
			throw new ReceiverException("Invalid command '"+cmd+"'");
		}
		Dynamic data=convertRequestToData(command,json);
		ReceiverData recieverData=new ReceiverData(data,cmd);
		JSONResult result=new JSONResult();
		command.handleRequest(this, psession, recieverData, result);

		psession.setContentType("application/json");
		psession.getOutputStream().print(result.toString());		
	}
		
	protected Dynamic convertRequestToData(Command command,JSONObject json) throws JSONException, NoSuchMethodException, ClassNotFoundException, InstantiationException, IllegalAccessException, InvocationTargetException, InvalidObjectType, IOException, DynamicException    
	{
			Object value;
			Dynamic object=command.getObject();
			JSONObject data=json;
			String name;
			for(Map.Entry<String,Parameter> paramEnt :command.getParameters()){
				name=paramEnt.getKey();
				if(!name.equals(getCmdField())){
					if(data.has(name)){
						value=data.get(name);	
					} else {
						value=null; //TODO When parameter is mandatory
					}
					value=convertValue(value,paramEnt.getValue());
					object.put(name,value);
				}
			}

			return object;
	}
	
}
