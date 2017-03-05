package org.elaya.page.core;

import java.io.NotSerializableException;
import java.io.Serializable;
import java.util.Map;
/**
 * Data used for authorization/user handling
 *
 */
public interface AuthorizationData {
	/**
	 * Initialize data by id (for example user id)
	 * 
	 * @param pid
	 */
	public abstract void initSessionData(Serializable pid);
	/**
	 * Initialize data by a map
	 * @param pdata Data set for initializing the object
	 * 
	 * @throws NotSerializableException
	 */
	
	public abstract void initSessionData(Map<String,Object> pdata) throws NotSerializableException;
	public abstract Serializable getId();
	/**
	 * Redirect to this path after login
	 *  
	 * @return
	 */
	public abstract String getAfterLoginPath();
	/**
	 * Checks if session is authorized (user is logged in)
	 * @return True: User is logged in. False: User is not authorized.
	 */
	public abstract boolean isAuthenticated();
}
