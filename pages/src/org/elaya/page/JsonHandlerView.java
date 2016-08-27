package org.elaya.page;

import java.io.BufferedReader;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;
import org.springframework.web.servlet.view.AbstractView;

public class JsonHandlerView extends AbstractView {

	public JsonHandlerView() {
		super();
	}
 
	@Override
	protected void renderMergedOutputModel(Map<String, Object> model, HttpServletRequest p_request,
			HttpServletResponse p_response) throws Exception {
		// TODO Auto-generated method stub
		StringBuffer l_data=new StringBuffer();
		String l_part;
		BufferedReader l_reader=p_request.getReader(); 
		while((l_part=l_reader.readLine())!= null) l_data.append(l_part);
		JSONObject l_json=new JSONObject(l_data.toString());
		p_response.getOutputStream().print("{\"ok\":\"1\"}");   
	}

}
