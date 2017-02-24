package org.elaya.page.filter;

import java.io.NotSerializableException;
import java.io.Serializable;
import java.util.Map;

import org.elaya.page.core.AuthorizationData;

public class DefaultAuthorizationData implements AuthorizationData {

	private Serializable id;
	
	@Override
	public void initSessionData(Serializable pid) {
		id=pid;

	}

	@Override
	public void initSessionData(Map<String, Object> pdata) throws NotSerializableException {
		Object data=pdata.get("id");
		if(data instanceof Serializable){
			id=(Serializable) data;
		}//TODO when data is not Serializable
	}

	@Override
	public Serializable getId() {
		return id;
	}

	@Override
	public String getAfterLoginPath() {
		return null;
	}

	@Override
	public boolean isAuthenticated() {
		return id != null?true:false;
	}

}
