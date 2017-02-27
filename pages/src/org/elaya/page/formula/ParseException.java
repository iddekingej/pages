package org.elaya.page.formula;

public class ParseException extends FormulaException {

	private static final long serialVersionUID = -5795382004171161242L;

	public ParseException(String pmessage,int position)
	{
		super(pmessage+" at "+position);
	}
}
