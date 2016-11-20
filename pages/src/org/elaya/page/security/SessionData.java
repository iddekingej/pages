package org.elaya.page.security;

import java.util.Map;

abstract public class SessionData {

	
	public SessionData(Integer p_id){
		initSessionData(p_id);
	}
	
	public SessionData(Map<String,Object> p_map)
	{
		initSessionData(p_map);
	}
	
	abstract protected void initSessionData(int p_id);
	abstract protected void initSessionData(Map<String,Object> p_data);
	abstract public int getId();
	abstract public String getAfterLoginPath();
}
