package org.elaya.page;

import java.util.LinkedList;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.elaya.page.data.MapData;
import org.slf4j.Logger;
import org.springframework.web.servlet.view.AbstractView;

public class PageView extends AbstractView {

	private String basePath;
	Logger logger ;
	

	public PageView(String p_file,Logger p_logger) {
		super();
		basePath=p_file;
		logger=p_logger;
	}

	@Override
	protected void renderMergedOutputModel(Map<String, Object> p_map, HttpServletRequest p_request, HttpServletResponse p_response)
			throws Exception {
		// TODO Auto-generated method stub
		
	
		Application l_app=new Application(p_request,p_response);
		logger.info("Bla bla");
		l_app.setLogger(logger);
		MapData l_md=new MapData(null,p_map);
		l_md.makeData();		
		UiXmlParser l_parser=new UiXmlParser(l_app,l_md);
		String l_fileName=p_request.getRequestURI().substring(p_request.getContextPath().length());
		Page l_page=l_parser.parseUiXml(basePath+l_fileName+".xml");
		LinkedList<String> l_errors=l_parser.getErrors();
		for(String l_error:l_errors){
			logger.info(l_error);
		}
		l_page.setUrl(p_request.getRequestURI());
		//p_request.getPathTranslated()
		//p_request.getRequestURI()		
		l_page.display();
	}

}
