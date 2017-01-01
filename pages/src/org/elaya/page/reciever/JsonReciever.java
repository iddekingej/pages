package org.elaya.page.reciever;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.elaya.page.data.Dynamic;
import org.json.JSONException;
import org.json.JSONObject;

public abstract class JsonReciever<T extends Dynamic> extends Reciever<T> {

	protected abstract void validateRequest(JSONResult presult,T pdata,String pcmd) throws JSONException;
	protected abstract void handleJson(JSONResult presult,T pdata,String pcmd) throws Exception;
	
	private JSONObject getJson(HttpServletRequest prequest) throws IOException, JSONException
	{
		StringBuilder data=new StringBuilder();
		String part;
		BufferedReader reader=prequest.getReader(); 
		while((part=reader.readLine())!= null){
			data.append(part);
		}
		return new JSONObject(data.toString());
	}
	
	
	@Override
	protected void sendFailure(HttpServletResponse response,Exception e) throws JSONException, IOException
	{
		JSONResult result=new JSONResult();
		result.addError("", "Internal error:"+e.toString());
		e.printStackTrace();
		response.setContentType("application/json");
		response.getOutputStream().print(result.toString());
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
	protected final  void handleData(HttpServletResponse response,RecieverData<T> data) throws Exception
	{
		JSONResult result=new JSONResult();
		validate(result,data);
		validateRequest(result,data.getData(),data.getCmd());		
		if(!result.hasErrors()){
			handleJson(result,data.getData(),data.getCmd());
		}
		response.setContentType("application/json");
		response.getOutputStream().print(result.toString());		
	}
	
	@Override
	protected RecieverData<T> convertRequestToData(HttpServletRequest request,HttpServletResponse response) throws Exception   
	{
			Object value;
			T object=getObject();


			JSONObject json=getJson(request);
			String cmd=json.getString("cmd");
			JSONObject data=json.getJSONObject("data");
			String name;
			for(Map.Entry<String,Parameter> paramEnt :getParameters().entrySet()){
				name=paramEnt.getKey();
				value=data.get(name);//TODO: when data doesn't exists
				value=convertValue(value,paramEnt.getValue());
				object.put(name,value);
			}

			
			return new RecieverData<>(object,cmd);
	}
	
}
