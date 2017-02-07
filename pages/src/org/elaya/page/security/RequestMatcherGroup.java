package org.elaya.page.security;

import java.io.IOException;
import java.sql.SQLException;
import java.util.LinkedList;

import javax.xml.parsers.ParserConfigurationException;

import org.elaya.page.Errors.AliasNotFound;
import org.elaya.page.Errors.LoadingAliasFailed;
import org.elaya.page.application.Application.DefaultDBConnectionNotSet;
import org.elaya.page.application.Application.InvalidAliasType;
import org.elaya.page.data.Data.KeyNotFoundException;
import org.elaya.page.receiver.Receiver.ReceiverException;
import org.elaya.page.security.Errors.AuthenticationException;
import org.elaya.page.widget.Element.DisplayException;
import org.elaya.page.xml.XMLParserBase.XMLLoadException;
import org.xml.sax.SAXException;

public class RequestMatcherGroup implements HasRequestMatchers{
	private LinkedList<RequestMatcher> requestMatchers=new LinkedList<>();
	
	protected RequestMatcher matchRequest(Session session)
	{
		RequestMatcher found=null;
		for(RequestMatcher requestMatcher:requestMatchers){
			found=requestMatcher.matchRequest(session);			
			if(found != null){
				return found;
			}
		}
		return found;
	}	
	
	public MatchActionResult execute(Session psession) throws AuthenticationException, IOException, ReceiverException, XMLLoadException, SQLException, DefaultDBConnectionNotSet, KeyNotFoundException, ParserConfigurationException, SAXException, InvalidAliasType, AliasNotFound, LoadingAliasFailed, DisplayException 
	{
		RequestMatcher requestMatcher=matchRequest(psession);	
		if(requestMatcher==null){
			return MatchActionResult.NEXTFILTER;
		}
		return requestMatcher.execute(psession);				
	}
	
	public void addRequestMatcher(RequestMatcher prequestMatcher)
	{
		requestMatchers.add(prequestMatcher);
	}
}
