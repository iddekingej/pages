package org.elaya.page.security;

import java.io.NotSerializableException;
import java.io.Serializable;
import java.util.Map;

public interface AuthorisationData {
	public abstract void initSessionData(Serializable p_id);
	public abstract void initSessionData(Map<String,Object> p_data) throws NotSerializableException;
	public abstract Serializable getId();
	public abstract String getAfterLoginPath();
	public abstract boolean isAuthenticated();
}
