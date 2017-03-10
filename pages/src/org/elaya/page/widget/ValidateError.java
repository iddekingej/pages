package org.elaya.page.widget;

public class ValidateError extends Exception {

	private static final long serialVersionUID = 8131523979726049197L;

	public ValidateError(String pmsg)
	{
		super(pmsg);
	}
	
	public ValidateError(String pmsg,Throwable e)
	{
		super(pmsg,e);
	}
}
