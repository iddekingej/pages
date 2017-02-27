package org.elaya.page.formula;

public class FormulaParser {
	private FormulaScanner scanner;
	
	public  FormulaParser(String psource) throws FormulaParseException{
		scanner=new FormulaScanner(psource);
		scanner.next();
	}
	
	public FormulaNode parseFormula() throws FormulaException
	{
		return parseCompNode();
	}
	
	void raiseParserError(String pmessage) throws ParseException
	{
		throw new ParseException(pmessage,scanner.getCurrentPosition());
	}
	/**
	 * Checks if the current ident is the expected type. If not raise
	 * an exception, otherwise move to the next token 
	 * 	 
	 * @param pident  Identifier that is expected
	 * @param pvalue  Description of identifier used in error message
	 * @throws FormulaException
	 */
	void expect(FormulaIdent pident,String pvalue) throws FormulaException
	{
		if(scanner.getCurrentIdent() != pident){
			raiseParserError("'"+pvalue+"' expected, but ' "+scanner.getCurrentIdentText()+"' found");
		}
		scanner.next();
	}
	
	/**
	 * Parses <,> etc.. nodes
	 * 
	 * @return CompNode
	 * @throws FormulaException
	 */
	private FormulaNode parseCompNode() throws FormulaException 
	{
		FormulaNode leftNode=parseAddSubNode();
		FormulaNode rightNode;
		FormulaIdent ident;
		while(true){
			ident=scanner.getCurrentIdent();
			switch(ident){
			case CHAR_EQUAL:
			case CHAR_NOT_EQUAL:
			case CHAR_BIGGER:
			case CHAR_BIGGER_EQUAL:
			case CHAR_LESS:
			case CHAR_LESS_EQUAL:
				scanner.next();
				rightNode=parseAddSubNode();
				leftNode=new CompOperator(ident,leftNode,rightNode);
				break;
			default:
				return leftNode;
			}
		}
	}
	/**
	 * Parse + and - to Nodes
	 * @return AddOperatorNode or a SubOperatorNode
	 * @throws FormulaException
	 */
	private FormulaNode parseAddSubNode() throws FormulaException
	{
		FormulaNode leftNode=parseItem();
		FormulaNode rightNode;
		FormulaIdent ident;
		while(true){
			ident=scanner.getCurrentIdent();
			if(ident==FormulaIdent.CHAR_PLUS){
				rightNode=parseItem();
				leftNode=new AddOperatorNode(leftNode,rightNode);
			} else if(ident==FormulaIdent.CHAR_MINUS){
				rightNode=parseItem();
				leftNode=new SubOperatorNode(leftNode,rightNode);				
			} else {
				return leftNode;
			}
		}
		
	}
	
	/**
	 * Parses the lowest lever of expression"
	 * -Variable
	 * -Constant
	 * -(#expression#)
	 * @return
	 * @throws FormulaException
	 */
	FormulaNode parseItem() throws FormulaException
	{
		FormulaNode node=null;
		switch(scanner.getCurrentIdent()){
		case VARIABLE:
			node=new VarNode(scanner.getCurrentIdentText());
			scanner.next();			
			break;
		case STRING:
			node=new ConstantNode(scanner.getCurrentIdentText());
			scanner.next();
			break;
		case NUMBER:
			node=new ConstantNode(scanner.getCurrentNumber());
			scanner.next();
			break;
		case CHAR_HOOK_L:
			scanner.next();
			node=parseFormula();
			expect(FormulaIdent.CHAR_HOOK_R,")");			
			break;
		default:
			raiseParserError("Syntax error variable,string or (<expression>) expected, but '"+scanner.getCurrentIdentText()+"' found");
		}
		return node;
	}
}
