package org.elaya.page.core;
/**
 *  Key already exists (for example in a hash map)
 *
 */
public class KeyAlreadyExists extends DataException {

	private static final long serialVersionUID = 1425312325711132034L;

	public KeyAlreadyExists(String pkey){
		super("Key '"+pkey+"' allready exists");
	}
}
