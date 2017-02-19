package org.elaya.page.filter;

import java.sql.SQLException;
import java.util.Map;

import org.elaya.page.core.PageSession;

@FunctionalInterface
public interface Authenticator {
	public Map<String,Object> getAuthenicate(PageSession session) throws SQLException, ClassNotFoundException;
}
