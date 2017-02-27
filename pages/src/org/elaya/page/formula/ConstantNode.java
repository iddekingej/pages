package org.elaya.page.formula;

import org.elaya.page.core.Data;

public class ConstantNode implements FormulaNode {

	private Object value;
	
	public ConstantNode(Object pvalue)
	{
		value=pvalue;
	}

	@Override
	public Object calculate(Data pdata) {
		return value;
	}

}
