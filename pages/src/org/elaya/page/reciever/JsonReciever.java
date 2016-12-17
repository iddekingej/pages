package org.elaya.page.reciever;

import java.io.BufferedReader;
import java.io.IOException;
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
	protected final  void handleData(HttpServletResponse response,RecieverData data) throws Exception
	{
		JSONResult result=new JSONResult();
		validateRequest(result,data.getData(),data.getCmd());
		if(!result.hasErrors()){
			handleJson(result,data.getData(),data.getCmd());
		}
		response.setContentType("application/json");
		response.getOutputStream().print(result.toString());		
	}
	
	@Override
	protected RecieverData convertRequestToData(HttpServletRequest request,HttpServletResponse response) throws Exception   
	{
			Object value;
			T object=getObject();
			//TODO fail when mandatory and parameter is not given				

			JSONObject json=getJson(request);
			String cmd=json.getString("cmd");
			JSONObject data=json.getJSONObject("data");
			//TODO Handle exception and when parameter does not exists
			for(Parameter parameter:getParameters()){
				value=data.get(parameter.getName());
				value=convertValue(value,parameter);
				object.put(parameter.getName(),value);
			}

			
			return new RecieverData(object,cmd);
	}
	
}
