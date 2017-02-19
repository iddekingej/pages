package org.elaya.page.application;

/**
 *   This type contains alias data
 *   An alias data exists as (alias,type, value). 
 *   The alias is stored as a key in a Map and the value is this object
 *   Alias are managed in the Application object 
 */
public class AliasData {
	public static final String ALIAS_ELEMENT="element";
	public static final String ALIAS_JSFILE="jsfile";
	public static final String ALIAS_CSSFILE="cssfile";
	public static final String ALIAS_URL="url";
	public static final String ALIAS_RECIEVER="reciever";
	public static final String ALIAS_SECURITY="security";
	public static final String ALIAS_ROUTER="router";
	
	/**
	 *  Alias value.  
	 */
	private String value;
	/**
	 *  Alias type. Used for checking if the alias is the correct type
	 */
	
	private String type;
	
	/**
	 * Constructor of alias data
	 * 
	 * @param ptype    Type of alias
	 * @param pvalue   value of alias
	 */
	public AliasData(String ptype,String pvalue) {
		value=pvalue;
		type=ptype;
	}


	public String getValue(){ return value;}
	public String getType(){ return type;}


}
