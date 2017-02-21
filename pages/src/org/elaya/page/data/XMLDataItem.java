package org.elaya.page.data;

import org.elaya.page.Errors.InvalidTypeException;
import org.elaya.page.core.Data.KeyNotFoundException;

@FunctionalInterface
public interface XMLDataItem {
	void processData(MapData pdata) throws InvalidTypeException, KeyNotFoundException;
}
