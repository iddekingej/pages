package org.elaya.page.formula;

import org.elaya.page.core.Data;
import org.elaya.page.core.KeyNotFoundException;

public class VarNode implements FormulaNode {


	private String varName;

	public VarNode(String pvarName)
	{
		varName=pvarName;
	}
	
	@Override
	public Object calculate(Data pdata) throws FormulaExecuteException {
		try{
			return pdata.get(varName);
		} catch(KeyNotFoundException e){
			throw new FormulaExecuteException("Variable "+varName+" doesn't exists",e);
		}
	}

}
