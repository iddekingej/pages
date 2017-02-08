package org.elaya.page.core;

import java.io.NotSerializableException;
import java.io.Serializable;
import java.util.Map;

public interface AuthorizationData {
	public abstract void initSessionData(Serializable pid);
	public abstract void initSessionData(Map<String,Object> pdata) throws NotSerializableException;
	public abstract Serializable getId();
	public abstract String getAfterLoginPath();
	public abstract boolean isAuthenticated();
}
