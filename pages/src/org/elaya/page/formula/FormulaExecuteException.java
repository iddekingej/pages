package org.elaya.page.formula;

public class FormulaExecuteException extends FormulaException {

		private static final long serialVersionUID = 1L;

		public FormulaExecuteException(String pmessage)
		{
			super(pmessage);
		}
		
		public FormulaExecuteException(String pmessage,Throwable pprevious)
		{
			super(pmessage,pprevious);
		}
}
