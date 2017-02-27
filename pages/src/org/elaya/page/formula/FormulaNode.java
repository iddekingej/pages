package org.elaya.page.formula;

import org.elaya.page.core.Data;
/**
 * A FormulaNode represent an operation or a value inside a formula 
 * (for example add or variable node)
 *
 */
@FunctionalInterface
public interface FormulaNode {	
	/**
	 * Execute calculation
	 * 
	 * @param pdata  Data store used for calculation
	 * @return  Result of calculation
	 */
	public Object calculate(Data pdata) throws FormulaExecuteException;
}
