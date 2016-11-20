package org.elaya.page.security;

import java.sql.SQLException;
import java.util.Map;

import javax.servlet.ServletRequest;

abstract public class Authenticator {

	public Authenticator() {
		// TODO Auto-generated constructor stub
	}

	abstract public Map<String,Object> getAuthenicate(ServletRequest p_request) throws SQLException, ClassNotFoundException;
}
