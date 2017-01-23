package org.elaya.page.receiver;

import java.util.HashMap;

import org.json.JSONException;

public class POSTResult implements Result {

	HashMap<String,String> errors=null;
	
	@Override
	public void addError(String pfield, String perror) throws JSONException {
		if(errors==null){
			errors=new HashMap<>();
		}
		errors.put(pfield,perror);
	}

	@Override
	public boolean hasErrors() {
		return errors != null;
	}

	@Override
	public void put(String pfield, String pvalue) throws JSONException {
	}

	@Override
	public void put(String pfield, Integer pvalue) throws JSONException {

	}

}
