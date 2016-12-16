package org.elaya.page.reciever;

import java.io.BufferedReader;
import java.io.IOException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.elaya.page.data.Dynamic;
import org.json.JSONException;
import org.json.JSONObject;

public abstract class JsonReciever<T extends Dynamic> extends Reciever<T> {
	protected void validateRequest(JSONResult presult,T pdata,String pcmd) throws JSONException
	{
		
	}
	protected abstract  void handleJson(JSONResult presult,T pdata,String pcmd) throws Exception;
	
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
	
	public void failure(HttpServletResponse presponse, Exception e) throws IOException, JSONException 
	{
		System.out.print(e.toString());

		JSONResult result=new JSONResult();
		result.addError("", "Internal error:"+e.toString());
		presponse.setContentType("application/json");
		presponse.getOutputStream().print(result.toString());
	}
	@SuppressWarnings("unchecked")
	@Override
	public void handleRequest(HttpServletRequest prequest,HttpServletResponse presponse ) throws Throwable
	{
		try{
			Object value;
			Dynamic object=getObject();
			//TODO fail when mandatory and parameter is not given				

			JSONObject json=getJson(prequest);
			String cmd=json.getString("cmd");
			JSONObject data=json.getJSONObject("data");
			//TODO Handle exception and when parameter does not exists
			for(Parameter parameter:getParameters()){
				value=data.get(parameter.getName());
				if(parameter.getType()==ParameterType.INTEGER && "".equals(value)){
					value=null;
				}
				object.put(parameter.getName(),value);
			}

			T information;

			information=(T)object;
			JSONResult result=new JSONResult();
			validateRequest(result,information,cmd);
			if(!result.hasErrors()){
				handleJson(result,information,cmd);
			}
			presponse.setContentType("application/json");
			presponse.getOutputStream().print(result.toString());
		} catch(Exception e){
			failure(presponse,e);
		}
	}
	
}
