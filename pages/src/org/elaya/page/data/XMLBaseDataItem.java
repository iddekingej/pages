package org.elaya.page.data;

import org.elaya.page.formula.FormulaNode;
import org.elaya.page.formula.FormulaParser;

public abstract class XMLBaseDataItem implements XMLDataItem {

	public static class XMLDataException extends Exception{

		private static final long serialVersionUID = -4673935787663608356L;
		
		public XMLDataException(Throwable e){
			super(e.getMessage(),e);
		}
		
		public XMLDataException(String pmessage)
		{
			super(pmessage);
		}
	}
	/**
	 * Condition if the node should be executed
	 */
	private String condition;
	private FormulaNode parsedCondition;
	
	@Override
	public boolean executeThis(MapData pdata) throws XMLDataException
	{
		try{
			if(condition != null && condition.length()>0){
				if(parsedCondition ==null){
					FormulaParser parser=new FormulaParser(condition);
					parsedCondition=parser.parseFormula();
				}
				Object value=parsedCondition.calculate(pdata);
				if(value instanceof Boolean){
					return (Boolean) value;
				} else {				
					throw new XMLDataException("Invalid data type, the result of a condition should be a boolean");
				}				
			}
			return true;
		}catch(Exception e){
			throw new XMLDataException(e);
		}
	}

}
