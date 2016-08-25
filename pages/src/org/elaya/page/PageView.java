package org.elaya.page;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.view.AbstractView;

public class PageView extends AbstractView {

	private String basePath;
	Logger logger = LoggerFactory.getLogger(PageView.class);
	

	public PageView(String p_file) {
		super();
		basePath=p_file;
	}

	@Override
	protected void renderMergedOutputModel(Map<String, Object> p_map, HttpServletRequest p_request, HttpServletResponse p_response)
			throws Exception {
		// TODO Auto-generated method stub
		
	
		Application l_app=new Application(p_request,p_response);
		l_app.setLogger(logger);
		UiXmlParser l_parser=new UiXmlParser(l_app);
		String l_fileName=p_request.getRequestURI().substring(p_request.getContextPath().length());
		Page l_page=l_parser.parseUiXml(basePath+l_fileName+".xml",logger);
		l_page.setUrl(p_request.getRequestURI());
		l_page.setData(p_map);
		//p_request.getPathTranslated()
		//p_request.getRequestURI()		
		l_page.display();
	}

}
