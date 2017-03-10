package org.elaya.page.core;

public class ValueAttribute extends Attribute {
	Object value;
	
	public ValueAttribute(Class<?> ptype,Object pvalue)
	{
		super(ptype);
		if(!ptype.isInstance(pvalue)){
			String className=pvalue != null?pvalue.getClass().getName():"null";
			throw new IllegalArgumentException("Object is of type "+className+", but "+ptype.getName()+" expected");
		}
		value=pvalue;
	}
	
	@Override
	public Object getValue(Data pdata) {
		return value;
	}

}
