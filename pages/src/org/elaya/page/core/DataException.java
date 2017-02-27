package org.elaya.page.core;

public class DataException extends Exception {
	private static final long serialVersionUID = 1L;

	public DataException(String pmessage)
	{
		super(pmessage);
	}
	
	public DataException(String pmessage,Throwable pprevious)
	{
		super(pmessage,pprevious);
	}
}
