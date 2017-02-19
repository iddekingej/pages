package org.elaya.page.filter;

public class ActionException extends Exception {
	private static final long serialVersionUID = 1L;

	public ActionException(String pmessage)
	{
		super(pmessage);
	}
	
	public ActionException(Throwable pprevious)
	{
		super(pprevious);
	}

}
