package org.elaya.page.application;

import java.io.IOException;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;

import javax.xml.parsers.ParserConfigurationException;

import org.elaya.page.Errors.AliasNotFound;
import org.elaya.page.Errors.LoadingAliasFailed;
import org.elaya.page.application.Application.DefaultDBConnectionNotSet;
import org.elaya.page.application.Application.InvalidAliasType;
import org.elaya.page.core.PageSession;
import org.elaya.page.data.Data.KeyNotFoundException;
import org.elaya.page.receiver.Receiver;
import org.elaya.page.receiver.Receiver.ReceiverException;
import org.elaya.page.receiver.ReceiverParser;
import org.elaya.page.data.MapData;
import org.elaya.page.view.PageView;
import org.elaya.page.widget.Element.DisplayException;
import org.elaya.page.xml.XMLParserBase.XMLLoadException;
import org.xml.sax.SAXException;

public class Route {
	public static class RouteException extends Exception{
		private static final long serialVersionUID = 1L;
		
		public RouteException(String pmessage){
			super(pmessage);
		}
	}

	public static class InvalidRouteTypeException extends RouteException{
		private static final long serialVersionUID = 1L;
		
		public InvalidRouteTypeException(String pmessage){
			super(pmessage);
		}
	}
	
	private Set<String> method=new HashSet<>();
	private String url;
	private RouteType routeType;
	private String xmlFile;
	private boolean cache; 
		
	public void setCache(Boolean pcache)
	{
		cache=pcache;
	}
	
	public boolean getCache()
	{
		return cache;
	}
	
	public void setMethod(String pmethod)
	{
		String[] methodSplit=pmethod.split(",");
		for(int iter=0;iter<methodSplit.length;iter++){
			method.add(methodSplit[iter]);
		}
	}
	
	public Set<String> getMethod()
	{
		return method;
	}
	
	public void setUrl(String purl)
	{
		url=purl;
	}
	
	public String getUrl()
	{
		return url;
	}
	
	public void setRouteType(RouteType prouteType)
	{
		routeType=prouteType;
	}
	
	public RouteType getRouteType()
	{
		return routeType;
	}
	
	public String getXmlFile()
	{
		return xmlFile;
	}
	
	public void setXmlFile(String pxmlFile)
	{
		xmlFile=pxmlFile;
	}
	
	private void handlePage(PageSession psession,Application papplication) throws IOException, DisplayException, SQLException, DefaultDBConnectionNotSet, KeyNotFoundException, ParserConfigurationException, SAXException, InvalidAliasType, AliasNotFound, LoadingAliasFailed, XMLLoadException
	{
		PageView view=new PageView(xmlFile,papplication);
		view.setCache(cache);
		MapData data=new MapData();
		view.render(data, psession);	
	}
	
	private void handleReciever(PageSession psession,Application papplication) throws XMLLoadException, ReceiverException
	{
		ReceiverParser parser=new ReceiverParser(papplication);
		Receiver rec=parser.parse(xmlFile,Receiver.class);
		rec.handleRequest(psession);
	}
	
	public void handleRoute(PageSession psession,Application papplication) throws IOException, DisplayException, SQLException, DefaultDBConnectionNotSet, KeyNotFoundException, ParserConfigurationException, SAXException, InvalidAliasType, AliasNotFound, LoadingAliasFailed, XMLLoadException, ReceiverException, InvalidRouteTypeException
	{
		if(routeType==RouteType.PAGE){
			handlePage(psession,papplication);
		} else if(routeType==RouteType.RECIEVER){
			handleReciever(psession,papplication);
		} else {
			throw new InvalidRouteTypeException("Invalid route type:"+((routeType==null)?"Rout type is null":routeType.toString()));
		}
	}
}
