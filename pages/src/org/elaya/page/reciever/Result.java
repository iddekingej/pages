package org.elaya.page.reciever;

import org.json.JSONException;

public interface Result {
	public void addError(String pfield,String perror) throws JSONException;
	public boolean hasErrors();
	public void put(String pfield,String pvalue) throws JSONException;
	public void put(String pfield,Integer pvalue) throws JSONException;

}
