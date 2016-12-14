package org.elaya.page.xml;

public class XmlConfig {
	private Class<?>  baseClass;
	private Class<?>  defaultClass;
	private boolean   custom;
	private boolean   needParent;
	private String    defaultSetMethod;    
	/**
	 * Creates a xml parser config node. This information determens how to parse
	 * a xml expression to an object
	 * 
	 * @param pbaseClass            Specified class in tag must descent from this class
	 * @param pdefaultClass         If no class is given use this as class to create an object
	 * @param pcustom               Parsing of node and sub nodes is using a custom procedure
	 * @param pdefaultSetMethod     Use this method to add object to its parent object
	 * @param pneedParent           true: If object can needs a parent node
	 */
	
	public XmlConfig(Class<?> pbaseClass,Class<?> pdefaultClass,boolean pcustom,String pdefaultSetMethod,boolean pneedParent) 
	{
		baseClass        = pbaseClass;
		defaultClass     = pdefaultClass;
		custom           = pcustom;
		defaultSetMethod = pdefaultSetMethod;
		needParent       = pneedParent;
	}
	public Class<?> getBaseClass(){ return baseClass;}
	public Class<?> getDefaultClass(){ return defaultClass;}
	public boolean  getCustom(){ return custom;}
	public String   getDefaultSetMethod(){ return defaultSetMethod;}
	public boolean  getNeedParent(){ return needParent;}
	


}
