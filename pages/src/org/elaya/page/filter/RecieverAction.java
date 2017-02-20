package org.elaya.page.filter;

import org.elaya.page.application.Application;
import org.elaya.page.application.PageApplicationAware;
import org.elaya.page.core.PageSession;
import org.elaya.page.receiver.Receiver;
import org.elaya.page.receiver.ReceiverParser;

public class RecieverAction extends Action implements PageApplicationAware {
	private String reciever;
	private Application application;
	
	public void setReciever(String preciever)
	{
		reciever=preciever;
	}
	
	public String getReciever()
	{
		return reciever;
	}
	
	@Override
	public ActionResult execute(PageSession session) throws ActionException  {
		try{
			ReceiverParser parser=new ReceiverParser();
			parser.setApplication(application);
			Receiver rec=parser.parse(reciever,Receiver.class);
			rec.handleRequest(session);
			return ActionResult.NONEXTFILTER;
		}catch(Exception e){
			throw new ActionException(e);
		}
	}

	@Override
	public Application getApplication() {
		return application;
	}

	@Override
	public void setApplication(Application papplication) {
		application=papplication;		
	}

}
