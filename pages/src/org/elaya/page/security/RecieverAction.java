package org.elaya.page.security;

import java.io.IOException;

import org.elaya.page.application.Application;
import org.elaya.page.application.PageApplicationAware;
import org.elaya.page.core.PageSession;
import org.elaya.page.receiver.Receiver;
import org.elaya.page.receiver.Receiver.ReceiverException;
import org.elaya.page.receiver.ReceiverParser;
import org.elaya.page.security.Errors.AuthenticationException;
import org.elaya.page.xml.XMLParserBase.XMLLoadException;

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
	public ActionResult execute(PageSession session) throws AuthenticationException, IOException, ReceiverException, XMLLoadException {
		ReceiverParser parser=new ReceiverParser(getApplication());
		Receiver rec=parser.parse(reciever,Receiver.class);
		rec.handleRequest(session);
		System.out.println("Reciever found !!");
		return ActionResult.NONEXTFILTER;
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
