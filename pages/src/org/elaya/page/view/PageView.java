package org.elaya.page.view;


import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.parsers.ParserConfigurationException;

import org.elaya.page.Element.DisplayException;
import org.elaya.page.Errors.AliasNotFound;
import org.elaya.page.Errors.LoadingAliasFailed;
import org.elaya.page.Page;
import org.elaya.page.PageMode;
import org.elaya.page.Writer;
import org.elaya.page.application.Application;
import org.elaya.page.application.Application.DefaultDBConnectionNotSet;
import org.elaya.page.application.Application.InvalidAliasType;
import org.elaya.page.data.Data.KeyNotFoundException;
import org.elaya.page.data.MapData;
import org.elaya.page.xml.XMLParserBase.XMLLoadException;
import org.xml.sax.SAXException;

public class PageView implements AbstractView{

	private String path;	
	private PageMode mode;	
	private Application application;
	
	public PageView(PageMode pmode,String pfile,Application papplication) {
		mode=pmode;
		path=pfile;
		application=papplication;
	}

	public void render(MapData mapData, HttpServletRequest prequest, HttpServletResponse presponse) throws IOException, DisplayException, SQLException, DefaultDBConnectionNotSet, KeyNotFoundException, ParserConfigurationException, SAXException, InvalidAliasType, AliasNotFound, LoadingAliasFailed, XMLLoadException {

		String fileName="";
				
		if(mode.equals(PageMode.PATH)){
			fileName=path+prequest.getRequestURI().substring(prequest.getContextPath().length())+".xml";
		} else if(mode.equals(PageMode.FILENAME)) {
			fileName=path;
		}

		Page page=application.loadPage(fileName,true);
		Writer writer=new Writer(application,prequest,presponse);

		page.calculateData(mapData);
		page.setUrl(prequest.getRequestURI());
		page.display(writer,mapData);
		writer.flush();
	}
	
}
