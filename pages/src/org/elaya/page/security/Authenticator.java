package org.elaya.page.security;

import java.sql.SQLException;
import java.util.Map;

import javax.servlet.ServletRequest;
@FunctionalInterface
public abstract interface Authenticator {
	public abstract Map<String,Object> getAuthenicate(ServletRequest prequest) throws SQLException, ClassNotFoundException;
}
