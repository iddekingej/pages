package org.elaya.page.data;


import java.util.LinkedList;
import java.util.List;

import org.elaya.page.data.XMLBaseDataItem.XMLDataException;

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
	protected void processDataAfter(MapData pdata) throws XMLDataException
	{
	//This is a dummy method
	}
	
	/**
	 * Process data by the xml definition
	 * @param pdata Data used for processing.
	 * @throws XMLDataException 
	 */
	@Override
	protected void processOwnData(MapData pdata) throws XMLDataException {
		
		for(XMLDataItem dataItem:definition){
			if(dataItem.executeThis(pdata)){
				dataItem.processData(pdata);
			}
		}
		
		processDataAfter(pdata);
		
	}

	
}
