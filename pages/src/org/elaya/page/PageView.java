package org.elaya.page;

import java.util.HashMap;
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
	Application application;
	HashMap<String,String> aliasses=new HashMap<String,String>();
	

	public PageView(String p_file,Logger p_logger,Application p_application) {
		super(); 
		basePath=p_file;
		logger=p_logger;
		application=p_application;
		application.setLogger(logger);
	}
	
	public void addAliasses(String p_fileName) throws Exception
	{
		AliasParser l_parser=new AliasParser(application);
		l_parser.parseAliases(basePath+"/"+p_fileName, aliasses);
		for(String l_error:l_parser.getErrors()){
			logger.info(l_error);
		}
	}

	@Override
	protected void renderMergedOutputModel(Map<String, Object> p_map, HttpServletRequest p_request, HttpServletResponse p_response)
			throws Exception {
		// TODO Auto-generated method stub
		
		application.setRequest(p_request, p_response);
		MapData l_md=new MapData(null);
		l_md.setMap(p_map);
		l_md.setContext(application.getContext());		
		l_md.init();		
		addAliasses("alias.xml");
		UiXmlParser l_parser=new UiXmlParser(application,l_md,aliasses);
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
