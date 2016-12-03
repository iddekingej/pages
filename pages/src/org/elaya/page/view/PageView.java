package org.elaya.page.view;

import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.elaya.page.Page;
import org.elaya.page.PageMode;
import org.elaya.page.Writer;
import org.elaya.page.application.Application;
import org.elaya.page.data.MapData;
import org.slf4j.Logger;
import org.springframework.web.servlet.view.AbstractView;

public class PageView extends AbstractView {

	
	private String path;	
	PageMode mode;
	Logger logger ;
	Application application;
	
	public PageView()
	{
		
	}
	public PageView(PageMode p_mode,String p_file,Logger p_logger,Application p_application) {
		super(); 
		mode=p_mode;
		path=p_file;
		logger=p_logger;
		application=p_application;
	}


	@Override
	protected void renderMergedOutputModel(Map<String, Object> p_map, HttpServletRequest p_request, HttpServletResponse p_response)
			throws Exception {
		// TODO Auto-generated method stub
		application.setLogger(logger);
		MapData l_md=new MapData("___TOP",null);
		l_md.setByMap(p_map);
		String l_fileName="";
		
		if(mode.equals(PageMode.path)){
			l_fileName=path+p_request.getRequestURI().substring(p_request.getContextPath().length())+".xml";
		} else if(mode.equals(PageMode.filename)) {
			l_fileName=path;
		}

		Page l_page=application.loadPage(l_fileName,true);

		Writer l_writer=new Writer(application,p_request,p_response);
		if(l_page != null){
			l_page.calculateData(l_md);
			l_page.setUrl(p_request.getRequestURI());
			//p_response.setContentType("application/xml;charset=UTF-8");
			l_page.display(l_writer,l_md);
			l_writer.flush();
		} else {
			logger.info("Page =null");
		}
	}

}
