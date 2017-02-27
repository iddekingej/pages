package org.elaya.page.formula;

public class FormulaScanner {
		
	private String       source;
	private int          position;
	/**
	 * Text value of current ident (after call of next of the next method) 
	 */
	private String       currentIdentText;
	/**
	 * Start position of the current ident (after call of the next method)
	 */
	private int          currentPosition;
	/**
	 * Id of current ident.
	 */
	private FormulaIdent currentIdent;
	
	/**
	 * If the current token is a number(integer,float etc..)
	 * The value is stored in currentNumber or otherwise this value
	 * is null
	 */
	private Number currentNumber;

	
	public FormulaScanner(String psource)
	{
		source=psource;
	}

	public Number getCurrentNumber()
	{
		return currentNumber;
	}
	public int getCurrentPosition()
	{
		return currentPosition;
	}
	
	public String getCurrentIdentText()
	{
		return currentIdentText;
	}
	
	public FormulaIdent getCurrentIdent()
	{
		return currentIdent;
	}
	
	/**
	 * Parse an string token (everything between ' or ")
	 * @param pch Type Character that ends the string 
	 * @throws FormulaParseException When the end quote is missing
	 */
	void parseString(int pch) throws FormulaParseException
	{
		position++;
		int ch=0;
		StringBuilder text=new StringBuilder();
		while(position<source.length()){
			ch=source.charAt(position);
			if(ch=='\\'){
				position++;
				if(position>=source.length()){
					break;
				}
				ch=source.charAt(position);
			} else if(ch==pch){
				break;
			}
			text.append((char)ch);
			position++;
		}
		
		if(ch != pch){
			throw new FormulaParseException("Missing end qoute ("+((char) pch)+"),last character is :"+ch);
		}
		currentIdent=FormulaIdent.STRING;
		currentIdentText=text.toString();
		position++;
	}
	/**
	 * Parse variable expression 
	 * ${varName}
	 * 
	 */
	void parseVar() throws FormulaParseException
	{
		position+=2;
		int end=source.indexOf('}',position);
		if(end<0){
			throw new FormulaParseException("Missing end '}' in variable");
		}
		currentIdent=FormulaIdent.VARIABLE;
		currentIdentText=source.substring(position, end);
		position=end+1;
	}
	
	/**
	 * Parse numerical expression
	 */
	void parseInteger()
	{
		int ch;
		int begin=position;
		while(position<source.length()){
			ch=source.charAt(position);
			if(ch<48 || ch>57){
				break;
			}
			ch++;
		}
		currentIdentText=source.substring(begin,position);
		try{
			currentNumber=Integer.valueOf(currentIdentText);
		}catch(NumberFormatException e)
		{
			currentNumber=Long.valueOf(currentIdentText);
		}
		currentIdent=FormulaIdent.NUMBER;
	}
	
	
	/**
	 * Fetches the next token to parse
	 * set the following vars: 
	 * currentIdent : Current ident code.
	 * currentIdentText : Text of the ident
	 * currentPosition  : Start position of identifier
	 */
	
	void next() throws FormulaParseException
	{
		currentNumber=null;
		
		int ch;
		while(position<source.length()){
			ch=source.charAt(position);
			if((ch != 32) && (ch != 9) && (ch != 13) && (ch != 10)){
				break;
			}
			position++;
		}
		if(position>=source.length()){
			currentIdent=FormulaIdent.EOF;
			currentIdentText="";
			return;
		}
		currentPosition=position;
		ch=source.charAt(position);
		if(ch=='"'||ch=='\''){
			parseString(ch);
		} else if(ch=='$' && position+1<source.length() && source.charAt(position+1)=='{'){
			parseVar();
		} else if(ch>=48 && ch<=57){
			parseInteger();
		} else {
			currentIdentText=String.valueOf((char)ch);
			position++;			
			switch(ch)
			{
			case '+':
				currentIdent=FormulaIdent.CHAR_PLUS;
				break;
			case '-':
				currentIdent=FormulaIdent.CHAR_MINUS;
				break;
			case '/':
				currentIdent=FormulaIdent.CHAR_DIV;
				break;
			case '*':
				currentIdent=FormulaIdent.CHAR_MUL;
				break;
			case '(':
				currentIdent=FormulaIdent.CHAR_HOOK_L;
				break;
			case ')':
				currentIdent=FormulaIdent.CHAR_HOOK_R;
				break;
			case '=':
				if(position<source.length()){
					if(source.charAt(position)=='='){
						currentIdent=FormulaIdent.CHAR_EQUAL;
						currentIdentText += "=";
						position++;
						break;
					}
				}
				currentIdent=FormulaIdent.CHAR_OTHER;
				break;
			case '!':				
				if(position<source.length()){
					if(source.charAt(position)=='='){
						currentIdent=FormulaIdent.CHAR_NOT_EQUAL;
						currentIdentText += "=";
						position++;
						break;
					}
				}
				currentIdent=FormulaIdent.CHAR_OTHER;
			case '<':
				if(position<source.length()){
					if(source.charAt(position)=='='){
						currentIdent=FormulaIdent.CHAR_LESS_EQUAL;
						currentIdentText += "=";
						position++;
						break;
					} 
				}
				currentIdent=FormulaIdent.CHAR_LESS;
				break;
			case '>':
				if(position<source.length()){
					if(source.charAt(position)=='='){
						currentIdent=FormulaIdent.CHAR_BIGGER_EQUAL;
						currentIdentText += "=";
						position++;
						break;
					}
				}
				currentIdent=FormulaIdent.CHAR_BIGGER;
				break;

			default:
				currentIdent=FormulaIdent.CHAR_OTHER;

			}
		}
	}
}
