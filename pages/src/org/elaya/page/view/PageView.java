package org.elaya.page.view;


import java.io.IOException;
import java.sql.SQLException;
import javax.xml.parsers.ParserConfigurationException;

import org.elaya.page.Errors.AliasNotFound;
import org.elaya.page.Errors.InvalidTypeException;
import org.elaya.page.Errors.LoadingAliasFailed;
import org.elaya.page.application.Application;
import org.elaya.page.application.Application.DefaultDBConnectionNotSet;
import org.elaya.page.application.Application.InvalidAliasType;
import org.elaya.page.core.PageSession;
import org.elaya.page.core.Writer;
import org.elaya.page.core.Data.KeyNotFoundException;
import org.elaya.page.widget.Page;
import org.elaya.page.widget.Element.DisplayException;
import org.elaya.page.data.MapData;
import org.elaya.page.xml.XMLParserBase.XMLLoadException;
import org.xml.sax.SAXException;

public class PageView implements AbstractView{

	private String path;	
	private Application application;
	private boolean cache=true;
	
	public PageView(String pfile,Application papplication) {		
		path=pfile;
		application=papplication;
	}
	
	public void setCache(Boolean pcache)
	{
		cache=pcache;
	}

	/**
	 * Get if page needs te be cached
	 * 
	 * @return true  - page needs to be cached
	 *         false - page is allways read from the xml file
	 */
	public Boolean getCache()
	{
		return cache;
	}
	
	@Override
	public void render(MapData mapData, PageSession psession) throws IOException, DisplayException, SQLException, DefaultDBConnectionNotSet, KeyNotFoundException, ParserConfigurationException, SAXException, InvalidAliasType, AliasNotFound, LoadingAliasFailed, XMLLoadException, InvalidTypeException {

		Page page=application.loadPage(path,cache);
		Writer writer=new Writer(application,psession);
		mapData.put("pageSession", psession);
		page.calculateData(mapData);
		page.setUrl(psession.getRequestURI());
		page.display(writer,mapData);
		writer.flush();
	}
	
}
