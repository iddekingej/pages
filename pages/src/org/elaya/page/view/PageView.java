package org.elaya.page.view;


import java.io.IOException;
import org.elaya.page.application.Application;
import org.elaya.page.core.KeyNotFoundException;
import org.elaya.page.core.PageSession;
import org.elaya.page.core.Writer;
import org.elaya.page.widget.Page;
import org.elaya.page.widget.Element.DisplayException;
import org.elaya.page.data.MapData;
import org.elaya.page.data.XMLBaseDataItem.XMLDataException;
import org.elaya.page.formula.FormulaException;
import org.elaya.page.xml.XMLParserBase.XMLLoadException;

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
	public void render(MapData mapData, PageSession psession) throws XMLLoadException, IOException, KeyNotFoundException, XMLDataException, FormulaException, DisplayException {

		Page page=application.loadPage(path,cache);
		Writer writer=new Writer(application,psession);
		mapData.put("pageSession", psession);
		page.calculateData(mapData);
		page.setUrl(psession.getRequestURI());
		page.display(writer,mapData);
		writer.flush();
	}
	
}
