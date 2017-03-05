package org.elaya.page.filter;

import java.sql.SQLException;
import java.util.Map;

import org.elaya.page.core.PageSession;
/**
 * Authenticator
 * Interface used for authentication a login request.
 * @Return Data used for initializing the authentication session
 */
@FunctionalInterface
public interface Authenticator {
	public Map<String,Object> getAuthenicate(PageSession session) throws SQLException, ClassNotFoundException;
}
