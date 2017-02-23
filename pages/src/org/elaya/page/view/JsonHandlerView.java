package org.elaya.page.view;


import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.elaya.page.application.Application;
import org.elaya.page.core.PageSession;
import org.elaya.page.receiver.Receiver;
import org.elaya.page.receiver.ReceiverParser;

public class JsonHandlerView{
	String file;
	Application application;
		
	public JsonHandlerView(String pfile,Application papplication) 
	{
		super(); 
		file=pfile;	
		application=papplication;
	}
 
	protected void renderMergedOutputModel(Map<String, Object> model, HttpServletRequest prequest,
			HttpServletResponse presponse) throws Exception {		
		ReceiverParser parser=new ReceiverParser();
		parser.setApplication(application);
		Receiver rec=parser.parse(file,Receiver.class);
		rec.handleRequest(new PageSession(prequest, presponse));
	}

}
