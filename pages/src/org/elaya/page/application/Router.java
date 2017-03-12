package org.elaya.page.application;

import java.util.LinkedList;
import java.util.List;
import org.elaya.page.Errors.ContentException;
import org.elaya.page.core.PageSession;
import org.elaya.page.Errors;


public class Router implements PageApplicationAware {
	private LinkedList<Route> routes=new LinkedList<>();
	private Application application;

	@Override
	public Application getApplication() {
		return application;
	}

	@Override
	public void setApplication(Application papplication) {
		application=papplication;
		
	}
	
	public void addRoute(Route proute)
	{
		routes.add(proute);
	}
	
	public List<Route> getRoutes()
	{
		return routes;
	}
	
	public Route getRoute(String pmethod,String purl)
	{
		
		for(Route route:routes)
		{
			if(route.getMethod().contains(pmethod) && route.getUrl().equals(purl)){
				return route;
			}
		}
		return null;
	}
	
	public boolean handleRoute(PageSession psession) throws ContentException{		
		Route route=getRoute(psession.getMethod(),psession.getURIPath());
		if(route != null){
			try{
				route.handleRoute(psession, application);
			}catch(Exception e){
				throw new Errors.ContentException(e.getMessage(),e);
			}
			return true;
		}
		return false;
	}


}
