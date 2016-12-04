package org.elaya.page.view;

import java.util.LinkedList;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.elaya.page.PageMode;
import org.elaya.page.application.Application;
import org.elaya.page.reciever.Reciever;
import org.elaya.page.reciever.RecieverParser;
import org.slf4j.Logger;
import org.springframework.web.servlet.view.AbstractView;

public class JsonHandlerView extends AbstractView {
	PageMode mode;
	String file;
	Logger logger;
	Application application;
		
	public JsonHandlerView(PageMode p_mode,String p_file,Logger p_logger,Application p_application) 
	{
		super(); 
		mode=p_mode;
		file=p_file;
		logger=p_logger;
		application=p_application;
	}
 
	@Override
	protected void renderMergedOutputModel(Map<String, Object> model, HttpServletRequest p_request,
			HttpServletResponse p_response) throws Exception {
		application.setLogger(logger);
		RecieverParser l_parser=new RecieverParser(application);
		Reciever<?> l_rec=l_parser.parseXml(file);
		LinkedList<String> l_errors=l_parser.getErrors();
		if(l_errors.size()>0){
			for(String l_error:l_errors){
				logger.info(l_error);
			}
		} else {
			l_rec.setLogger(logger);
			try{
				l_rec.handleRequest(p_request, p_response);
			}catch(Throwable l_e)
			{
				logger.info(l_e.getMessage());
			}
		}
	}

}