package org.elaya.page;

public class AliasData {
	public static final String ALIAS_ELEMENT="element";
	public static final String ALIAS_JSFILE="jsfile";
	public static final String ALIAS_CSSFILE="cssfile";
	public static final String ALIAS_URL="url";
	public static final String ALIAS_RECIEVER="reciever";
	public static final String ALIAS_SECURITY="security";
	private String value;
	private String type;
	
	public AliasData(String ptype,String pvalue) {
		value=pvalue;
		type=ptype;
	}


	public String getValue(){ return value;}
	public String getType(){ return type;}


}
