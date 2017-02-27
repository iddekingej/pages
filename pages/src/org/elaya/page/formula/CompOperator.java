package org.elaya.page.formula;

/**
 * Compare operator
 * < ,<=,>=,>,== and !=
 */
public class CompOperator extends DualOperatorNode {

	
	public CompOperator(FormulaIdent poperator, FormulaNode prightNode, FormulaNode pleftNode) {
		super(poperator, prightNode, pleftNode);
	}

	/**
	 * Calculate compare operator
	 * @param pdata Data used during calculation
	 * @return  result of calculation (always boolean)
	 * @throws FormulaExecuteException 
	 */

	@Override
	protected Object dualOperator(FormulaIdent poperator,Object pleftValue,Object prightValue) throws FormulaExecuteException 
	{
		if(poperator==FormulaIdent.CHAR_EQUAL){
			if(pleftValue==null){
				return prightValue ==null;
			}
			return pleftValue.equals(prightValue);
		} else if(poperator==FormulaIdent.CHAR_NOT_EQUAL){
			if(pleftValue==null){
				return prightValue != null;
			}
			return !pleftValue.equals(prightValue);
		}
		@SuppressWarnings({ "unchecked", "rawtypes" })
		int result=((Comparable)pleftValue).compareTo(prightValue);
		switch(poperator){
			case CHAR_BIGGER:
				return result<0;
			case CHAR_BIGGER_EQUAL:
				return result<=0;
			case CHAR_LESS_EQUAL:
				return result >=0;
			case CHAR_LESS:
				return result > 0;
			
			default:
				throw new FormulaExecuteException("Invalid poperator type "+poperator);
		}	
	}

}
