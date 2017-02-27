package org.elaya.page.formula;

public class FormulaException extends Exception {

	private static final long serialVersionUID = 2326133970486554466L;

	public FormulaException(String pmessage)
	{
		super(pmessage);
	}
	
	public FormulaException(String pmessage,Throwable pprevious)
	{
		super(pmessage,pprevious);
	}
}
