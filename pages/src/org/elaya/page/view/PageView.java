package org.elaya.page.view;

import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.elaya.page.Page;
import org.elaya.page.PageMode;
import org.elaya.page.Writer;
import org.elaya.page.application.Application;
import org.elaya.page.data.MapData;
import org.springframework.web.servlet.view.AbstractView;

public class PageView extends AbstractView {

	
	private String path;	
	PageMode mode;	
	Application application;
	
	public PageView(PageMode pmode,String pfile,Application papplication) {
		super(); 
		mode=pmode;
		path=pfile;
		application=papplication;
	}


	@Override
	protected void renderMergedOutputModel(Map<String, Object> pmap, HttpServletRequest prequest, HttpServletResponse presponse)
			throws Exception {
		MapData md=new MapData("___TOP",null);
		md.setByMap(pmap);
		String fileName="";
		
		
		if(mode.equals(PageMode.path)){
			fileName=path+prequest.getRequestURI().substring(prequest.getContextPath().length())+".xml";
		} else if(mode.equals(PageMode.filename)) {
			fileName=path;
		}

		Page page=application.loadPage(fileName,true);

		Writer writer=new Writer(application,prequest,presponse);
		if(page != null){
			page.calculateData(md);
			page.setUrl(prequest.getRequestURI());
			page.display(writer,md);
			writer.flush();
		} else {
			logger.info("Page =null");
		}
	}

}
