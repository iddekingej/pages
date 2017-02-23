package org.elaya.page.application;

import org.elaya.page.UniqueNamedObjectList;
import org.elaya.page.UniqueNamedObjectList.DuplicateItemName;

public class DatabaseConnections {
	private UniqueNamedObjectList <DatabaseConnection> connections=new UniqueNamedObjectList<>();

	void addConnection(DatabaseConnection pconnection) throws DuplicateItemName
	{
		connections.put(pconnection);
	}
	
	DatabaseConnection getConnection(String pname)
	{
		return connections.get(pname);
	}
}
