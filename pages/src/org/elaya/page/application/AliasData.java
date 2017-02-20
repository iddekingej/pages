package org.elaya.page.application;

/**
 *   This type contains alias data
 *   An alias data exists as (alias,type, value). 
 *   The alias is stored as a key in a Map and the value is this object
 *   Alias are managed in the Application object 
 */
public class AliasData {
	/**
	 *  Alias value.  
	 */
	private String value;
	/**
	 *  Alias type. Used for checking if the alias is the correct type
	 */
	
	private AliasNamespace type;
	
	/**
	 * Constructor of alias data
	 * 
	 * @param ptype    Type of alias
	 * @param pvalue   value of alias
	 */
	public AliasData(AliasNamespace ptype,String pvalue) {
		value=pvalue;
		type=ptype;
	}


	public String getValue(){ return value;}
	public AliasNamespace getType(){ return type;}


}
