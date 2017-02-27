package org.elaya.page.formula;

import org.elaya.page.core.Data;

public abstract class DualOperatorNode implements FormulaNode {
	private FormulaNode  leftNode;
	private FormulaNode  rightNode;
	private FormulaIdent operator;
	
	public DualOperatorNode(FormulaIdent poperator,FormulaNode prightNode,FormulaNode pleftNode)
	{
		leftNode=pleftNode;
		rightNode=prightNode;
		operator=poperator;
	}
	
	protected abstract Object dualOperator(FormulaIdent operator,Object leftValue,Object rightValue) throws FormulaExecuteException;
	
	
	@Override
	public final  Object calculate(Data pdata) throws FormulaExecuteException {
		Object leftValue=leftNode.calculate(pdata);
		Object rightValue=rightNode.calculate(pdata);
		return dualOperator(operator,leftValue,rightValue);
	}

}
