package org.elaya.page.reciever;

import java.io.BufferedReader;
import java.io.IOException;
import java.sql.SQLException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.elaya.page.data.Dynamic;
import org.json.JSONObject;

public abstract class JsonReciever<T extends Dynamic> extends Reciever<T> {

	abstract protected void handleJson(JSONResult p_result,T p_data) throws SQLException;
	
	private JSONObject getJson(HttpServletRequest p_request) throws IOException
	{
		StringBuffer l_data=new StringBuffer();
		String l_part;
		BufferedReader l_reader=p_request.getReader(); 
		while((l_part=l_reader.readLine())!= null) l_data.append(l_part);
		return new JSONObject(l_data.toString());
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public void handleRequest(HttpServletRequest p_request,HttpServletResponse p_response ) throws Exception
	{
		Dynamic l_object=getObject();
		//TODO fail when mandatory and parameter is not given				
				
		JSONObject l_json=getJson(p_request);
		JSONObject l_data=l_json.getJSONObject("data");
		//TODO Handle exception and when parameter does not exists
		for(Parameter l_parameter:getParameters()){
			l_object.put(l_parameter.getName(),l_data.getString(l_parameter.getName()));
		}
		
		T l_information;
		
		l_information=(T)l_object;
		JSONResult l_result=new JSONResult();
		handleJson(l_result,l_information);
		p_response.setContentType("application/json");
		p_response.getOutputStream().print(l_result.toString());
	}
	
}
