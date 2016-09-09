package org.elaya.page.reciever;

import java.io.BufferedReader;
import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.elaya.page.data.DynamicData;
import org.json.JSONException;
import org.json.JSONObject;

public abstract class JsonReciever<T extends DynamicData> extends Reciever<T> {

	private JSONObject getJson(HttpServletRequest p_request) throws IOException
	{
		StringBuffer l_data=new StringBuffer();
		String l_part;
		BufferedReader l_reader=p_request.getReader(); 
		while((l_part=l_reader.readLine())!= null) l_data.append(l_part);
		return new JSONObject(l_data.toString());
	}
	
	abstract public JSONObject handleRequest(T p_data) throws JSONException;
	
	@SuppressWarnings("unchecked")
	public void handleRequest(HttpServletRequest p_request,HttpServletResponse p_response ) throws Exception
	{
		DynamicData l_object=getObject();
		//TODO fail when mandatory and parameter is not given				
				
		JSONObject l_json=getJson(p_request);
		JSONObject l_data=l_json.getJSONObject("data");
		//TODO Handle exception and when parameter does not exists
		for(Parameter l_parameter:getParameters()){
			l_object.put(l_parameter.getName(),new String(l_data.getString(l_parameter.getName())));
		}
		
		T l_information;
		l_information=(T)l_object;
		JSONObject l_jsonSend=handleRequest(l_information);
		if(l_jsonSend !=null){
			p_response.getOutputStream().print(l_jsonSend.toString());
		} else {
			p_response.getOutputStream().print("{}");
		}
	}
	
}
