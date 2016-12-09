package org.elaya.page.reciever;

import java.io.BufferedReader;
import java.io.IOException;
import java.sql.SQLException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.elaya.page.data.Dynamic;
import org.json.JSONException;
import org.json.JSONObject;

public abstract class JsonReciever<T extends Dynamic> extends Reciever<T> {
	protected void validateRequest(JSONResult p_result,T p_data,String p_cmd) throws JSONException
	{
		
	}
	abstract protected void handleJson(JSONResult p_result,T p_data,String p_cmd) throws Exception;
	
	private JSONObject getJson(HttpServletRequest p_request) throws IOException, JSONException
	{
		StringBuffer l_data=new StringBuffer();
		String l_part;
		BufferedReader l_reader=p_request.getReader(); 
		while((l_part=l_reader.readLine())!= null) l_data.append(l_part);
		return new JSONObject(l_data.toString());
	}
	
	public void failure(HttpServletResponse p_response, Exception l_e) throws IOException, JSONException
	{
		if(getLogger()!=null){
			getLogger().info(l_e.toString());
		} else{
		}			
		System.out.print("***"+l_e.toString());

		JSONResult l_result=new JSONResult();
		l_result.addError("", "Internal error:"+l_e.toString());
		p_response.setContentType("application/json");
		p_response.getOutputStream().print(l_result.toString());
	}
	@SuppressWarnings("unchecked")
	@Override
	public void handleRequest(HttpServletRequest p_request,HttpServletResponse p_response ) throws Throwable
	{
		try{
			Object l_value;
			Dynamic l_object=getObject();
			//TODO fail when mandatory and parameter is not given				

			JSONObject l_json=getJson(p_request);
			String l_cmd=l_json.getString("cmd");
			JSONObject l_data=l_json.getJSONObject("data");
			//TODO Handle exception and when parameter does not exists
			for(Parameter l_parameter:getParameters()){
				l_value=l_data.get(l_parameter.getName());
				if(l_parameter.getType()==ParameterType.integer && l_value.equals("")){
					l_value=null;
				}
				l_object.put(l_parameter.getName(),l_value);
			}

			T l_information;

			l_information=(T)l_object;
			JSONResult l_result=new JSONResult();
			validateRequest(l_result,l_information,l_cmd);
			if(!l_result.hasErrors()){
				handleJson(l_result,l_information,l_cmd);
			}
			p_response.setContentType("application/json");
			p_response.getOutputStream().print(l_result.toString());
		} catch(Exception l_e)
		{
			failure(p_response,l_e);
		}
	}
	
}
