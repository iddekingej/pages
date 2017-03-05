package org.elaya.page.application;

import java.io.IOException;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;
import javax.xml.parsers.ParserConfigurationException;
import org.elaya.page.Errors.AliasNotFound;
import org.elaya.page.Errors.LoadingAliasFailed;
import org.elaya.page.application.Application.DefaultDBConnectionNotSet;
import org.elaya.page.application.Application.InvalidAliasType;
import org.elaya.page.application.Route.InvalidRouteTypeException;
import org.elaya.page.core.DataException;
import org.elaya.page.core.PageSession;
import org.elaya.page.data.XMLBaseDataItem.XMLDataException;
import org.elaya.page.formula.FormulaException;
import org.elaya.page.receiver.Receiver.ReceiverException;
import org.elaya.page.widget.Element.DisplayException;
import org.elaya.page.xml.XMLParserBase.XMLLoadException;
import org.xml.sax.SAXException;

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
			System.out.println(purl+" "+route.getUrl()); 
			if(route.getMethod().contains(pmethod) && route.getUrl().equals(purl)){
				return route;
			}
		}
		return null;
	}
	
	public boolean handleRoute(PageSession psession) throws IOException, DisplayException, SQLException, DefaultDBConnectionNotSet, ParserConfigurationException, SAXException, InvalidAliasType, AliasNotFound, LoadingAliasFailed, XMLLoadException, ReceiverException, InvalidRouteTypeException, DataException, ClassNotFoundException, FormulaException, XMLDataException{		
		Route route=getRoute(psession.getMethod(),psession.getURIPath());
		if(route != null){
			route.handleRoute(psession, application);
			return true;
		}
		return false;
	}


}
