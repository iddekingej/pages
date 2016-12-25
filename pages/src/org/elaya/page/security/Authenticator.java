package org.elaya.page.security;

import java.sql.SQLException;
import java.util.Map;

@FunctionalInterface
public abstract interface Authenticator {
	public abstract Map<String,Object> getAuthenicate(Session session) throws SQLException, ClassNotFoundException;
}
