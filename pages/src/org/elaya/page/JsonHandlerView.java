package org.elaya.page;

import java.io.BufferedReader;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.springframework.web.servlet.view.AbstractView;

public class JsonHandlerView extends AbstractView {
	PageMode mode;
	String path;
	Logger logger;
	Application application;
	
	public JsonHandlerView(PageMode p_mode,String p_file,Logger p_logger,Application p_application) {
		super(); 
		mode=p_mode;
		path=p_file;
		logger=p_logger;
		application=p_application;
		application.setLogger(logger);
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
		String l_fileName="";
		if(mode.equals(PageMode.path)){
			l_fileName=path+p_request.getRequestURI().substring(p_request.getContextPath().length())+".xml";
		} else if(mode.equals(PageMode.filename)) {
			l_fileName=path;
		}
		p_response.getOutputStream().print("{\"ok\":\"1\"}");   
	}

}
