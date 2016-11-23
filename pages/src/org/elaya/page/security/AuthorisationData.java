package org.elaya.page.security;

import java.util.Map;

abstract public class AuthorisationData {

	
	public AuthorisationData(){
	}
	

	abstract public void initSessionData(Object p_id);
	abstract public void initSessionData(Map<String,Object> p_data);
	abstract public Object getId();
	abstract public String getAfterLoginPath();
	abstract public boolean isAuthenticated();
}
