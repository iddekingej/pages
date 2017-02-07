package org.elaya.page.security;

import java.io.IOException;
import java.sql.SQLException;

import javax.xml.parsers.ParserConfigurationException;

import org.elaya.page.Errors.AliasNotFound;
import org.elaya.page.Errors.LoadingAliasFailed;
import org.elaya.page.application.Application;
import org.elaya.page.application.Application.DefaultDBConnectionNotSet;
import org.elaya.page.application.Application.InvalidAliasType;
import org.elaya.page.application.PageApplicationAware;
import org.elaya.page.core.PageSession;
import org.elaya.page.core.Writer;
import org.elaya.page.data.Data.KeyNotFoundException;
import org.elaya.page.data.MapData;
import org.elaya.page.widget.Element.DisplayException;
import org.elaya.page.widget.Page;
import org.elaya.page.xml.XMLParserBase.XMLLoadException;
import org.xml.sax.SAXException;

public class ViewAction extends Action implements PageApplicationAware {

	private Application application;
	private String pageFile;
	private boolean cache=true;

	@Override
	public void setApplication(Application papplication)
	{
		application=papplication;
	}
	
	@Override
	public Application getApplication() {
		return application;
	}
	
	public void setPageFile(String ppageFile)
	{
		pageFile=ppageFile;
	}
	
	public String getPageFile()
	{
		return pageFile;
	}
	
	public void setCache(Boolean pcache){
		cache=pcache;
	}
	
	boolean getCache()
	{
		return cache;
	}
	@Override
	public ActionResult execute(Session psession) throws XMLLoadException, IOException, SQLException, DefaultDBConnectionNotSet, KeyNotFoundException, ParserConfigurationException, SAXException, InvalidAliasType, AliasNotFound, LoadingAliasFailed, DisplayException{
		if(psession.getHttpRequest() !=null && psession.getHttpResponse() != null){
			PageSession pageSession=new PageSession(psession.getHttpRequest(),psession.getHttpResponse());
			Page page=getApplication().loadPage(pageFile,cache);
			Writer writer=new Writer(application,pageSession);
			MapData data=new MapData();
			data.put("pageSession",pageSession);
			page.calculateData(data);
			page.setUrl(pageSession.getRequestURI());
			page.display(writer,data);
			writer.flush();
		} else{
			//todo exception
		}
		return ActionResult.NONEXTFILTER;

	}


}
