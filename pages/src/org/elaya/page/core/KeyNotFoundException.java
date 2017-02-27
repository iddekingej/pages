package org.elaya.page.core;

public class KeyNotFoundException extends DataException{
	private static final long serialVersionUID = 8614663798348780748L;
	
	public KeyNotFoundException(String pkey){
		super("Key '"+pkey+"' not found");
	}
}