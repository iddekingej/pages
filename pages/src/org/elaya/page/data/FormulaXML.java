package org.elaya.page.data;

import org.elaya.page.formula.FormulaNode;
import org.elaya.page.formula.FormulaParser;

public class FormulaXML extends XMLBaseDataItem {

	/**
	 * Result of formula is stored in this variable
	 */
	private String name;
	/**
	 * Formula to evaluate
	 */
	private String formula;
	
	/**
	 * Parsed formula
	 */
	private FormulaNode parsedFormula=null;
	
	public void setName(String pname)
	{
		name=pname;
	}
	public String getName()
	{
		return name;
	}
	
	public void setFormula(String pformula)
	{
		formula=pformula;
	}
	
	public String getFormula()
	{
		return formula;
	}
	
	@Override
	public void processData(MapData pdata) throws XMLDataException {
		try{
			if(formula != null && formula.length() != 0){
				if(parsedFormula==null){
					FormulaParser parser=new FormulaParser(formula);
					parsedFormula=parser.parseFormula();
				}
				Object value=parsedFormula.calculate(pdata);
				pdata.put(name, value);
			}
		}catch(Exception e){
			throw new XMLDataException(e);
		}

	}

}
