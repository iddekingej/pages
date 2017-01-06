package org.elaya.page.view;


import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.elaya.page.PageMode;
import org.elaya.page.application.Application;
import org.elaya.page.receiver.Receiver;
import org.elaya.page.receiver.ReceiverParser;
import org.springframework.web.servlet.view.AbstractView;

public class JsonHandlerView extends AbstractView {
	PageMode mode;
	String file;
	Application application;
		
	public JsonHandlerView(PageMode pmode,String pfile,Application papplication) 
	{
		super(); 
		mode=pmode;
		file=pfile;	
		application=papplication;
	}
 
	@Override
	protected void renderMergedOutputModel(Map<String, Object> model, HttpServletRequest prequest,
			HttpServletResponse presponse) throws Exception {		
		ReceiverParser parser=new ReceiverParser(application);
		Receiver<?> rec=parser.parse(file,Receiver.class);
		rec.handleRequest(prequest, presponse);
	}

}
