package org.elaya.page.data;

import java.io.IOException;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;

import org.elaya.page.Errors.AliasNotFound;
import org.elaya.page.Errors.InvalidTypeException;
import org.elaya.page.Errors.LoadingAliasFailed;
import org.elaya.page.application.Application.DefaultDBConnectionNotSet;
import org.elaya.page.application.Application.InvalidAliasType;
import org.elaya.page.core.Data.KeyNotFoundException;
import org.xml.sax.SAXException;


public class XMLDataLayer extends DataLayer {
	private LinkedList<XMLDataItem> definition=new LinkedList<>();

	public void addDataItem(XMLDataItem pdataItem)
	{
		definition.add(pdataItem);
	}
	
	public List<XMLDataItem> getDefinition()
	{
		return definition;
	}
	
	/**
	 * After the data is process by the XML definition, this method can be used
	 * to process this data further by java.
	 * 
	 * @param pdata Data Container, source and destination of data.
	 */
	protected void processDataAfter(MapData pdata) throws SQLException, DefaultDBConnectionNotSet, ParserConfigurationException, SAXException, IOException, InvalidAliasType, AliasNotFound, LoadingAliasFailed, KeyNotFoundException
	{
	//This is a dummy method
	}
	
	/**
	 * Process data by the xml definition
	 */
	@Override
	protected void processOwnData(MapData pdata) throws InvalidTypeException, KeyNotFoundException, SQLException, DefaultDBConnectionNotSet, ParserConfigurationException, SAXException, IOException, InvalidAliasType, AliasNotFound, LoadingAliasFailed{
		for(XMLDataItem dataItem:definition){
			dataItem.processData(pdata);
		}
		processDataAfter(pdata);
	}

	
}
