package org.elaya.page.filter;

import org.elaya.page.application.Application;
import org.elaya.page.application.PageApplicationAware;
import org.elaya.page.application.Router;
import org.elaya.page.application.RouterParser;
import org.elaya.page.core.PageSession;

public class RouteAction extends Action implements PageApplicationAware{
	
	Router router;
	Application application;
	private String xmlFile;
	
	@Override
	public Application getApplication() {
		return application;
	}

	@Override
	public void setApplication(Application papplication) {
		application=papplication;		
	}	
	public String getXMLFile()
	{
		return xmlFile;
	}
	
	public void setXmlFile(String pxmlFile)
	{
		xmlFile=pxmlFile;
	}
	

	/**
	 * Routes current session(=request) and handle the request.
	 * The router is only read on demand (whe
	 * 
	 * @param  session current session to route
	 */
	@Override

	public ActionResult execute(PageSession psession) throws ActionException 
	{
		try{
			if(router==null){
				RouterParser parser=new RouterParser(getApplication());
				router=parser.parse(xmlFile,Router.class);
			}
			router.handleRoute(psession);
			
			return ActionResult.NONEXTFILTER;
		}catch(Exception e){
			throw new ActionException(e);
		}
	}



}
