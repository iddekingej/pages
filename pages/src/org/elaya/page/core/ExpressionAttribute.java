package org.elaya.page.core;


import java.lang.reflect.InvocationTargetException;
import org.elaya.page.Errors.ReplaceVarException;


public class ExpressionAttribute extends Attribute {
	

	
	private String expression;

	
	public ExpressionAttribute(Class<?> ptype,String pexpression)
	{
		super(ptype);
		expression=pexpression;
	}
	
	public String replaceVariables(Data pdata,String pstring) throws ReplaceVarException
	{
		int pos=0;
		int newPos;
		StringBuilder returnValue=new StringBuilder();
		String varName;
		Object value;
		String string=pstring==null?"":pstring;
		
			while(true){
				newPos=string.indexOf("${",pos);
				if(newPos==-1){
					returnValue.append(string.substring(pos));
					break;
				}
				returnValue.append(string.substring(pos,newPos));
				pos=string.indexOf('}',newPos+2);
				if(pos==-1){
					throw new ReplaceVarException("Missing '}'"); 				
				}
				varName=string.substring(newPos+2,pos);
				try{
					value=pdata.get(varName);
				}catch(Exception e){
					throw new ReplaceVarException("In String "+pstring,e);
				}
							
				if(value != null){
					returnValue.append(value.toString());
				}			
				pos++;
			}
		
		return returnValue.toString();
	}
	

	@Override
	public Object getValue(Data pdata) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException, ReplaceVarException {

		return this.convertFromString(replaceVariables(pdata,expression));
              
	}

}
