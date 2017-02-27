package org.elaya.page.data;

import org.elaya.page.data.XMLBaseDataItem.XMLDataException;

public interface XMLDataItem {
	public boolean executeThis(MapData pdata) throws XMLDataException;
	public void processData(MapData pdata) throws XMLDataException;
}
