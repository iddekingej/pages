package org.elaya.page.core;

/**
 * Exception for invalid type
 *
 */
public class InvalidTypeException extends DataException {

	private static final long serialVersionUID = 7804259665032868135L;

	public InvalidTypeException(Class<?> pexpected,Object pfound)
	{
		super("Invalid type, '"+pexpected.getClass().getName()+"' epected but '"+pfound.getClass().getName()+"' found");
	}
}
