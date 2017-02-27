package org.elaya.page.formula;

public class AddOperatorNode extends DualOperatorNode {

	public AddOperatorNode(FormulaNode prightNode, FormulaNode pleftNode) {
		super(FormulaIdent.CHAR_PLUS, prightNode, pleftNode);
	}

	@Override
	protected Object dualOperator(FormulaIdent operator, Object leftValue, Object rightValue)
			throws FormulaExecuteException {
		if(leftValue instanceof Integer){
			return ((Integer)leftValue)+Integer.valueOf(rightValue.toString());
		} else if(leftValue instanceof Long){
			return ((Long)leftValue)+Long.valueOf(rightValue.toString());
		} else if(leftValue instanceof String){
			return ((String)leftValue)+rightValue.toString();
		} else {
			throw new FormulaExecuteException("Invalid type of operation");
		}
		
	}

}
