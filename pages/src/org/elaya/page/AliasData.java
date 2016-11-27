package org.elaya.page;

public class AliasData {
	public static final String alias_element="element";
	public static final String alias_jsfile="jsfile";
	public static final String alias_cssfile="cssfile";
	public static final String alias_url="url";
	public static final String alias_reciever="reciever";
	public static final String alias_security="security";
	
	private String value;
	private String type;
	public String getValue(){ return value;}
	public String getType(){ return type;}
	public AliasData(String p_type,String p_value) {
		value=p_value;
		type=p_type;
	}

}
