package org.elaya.page.xml;

public class XmlConfig {
	private Class<?>  baseClass;
	private Class<?>  defaultClass;
	private boolean   custom;
	private boolean   needParent;
	private String    defaultSetMethod;    

	public Class<?> getBaseClass(){ return baseClass;}
	public Class<?> getDefaultClass(){ return defaultClass;}
	public boolean  getCustom(){ return custom;}
	public String   getDefaultSetMethod(){ return defaultSetMethod;}
	public boolean  getNeedParent(){ return needParent;}
	
	/**
	 * Creates a xml parser config node. This information determens how to parse
	 * a xml expression to an object
	 * 
	 * @param p_baseClass            Specified class in tag must descent from this class
	 * @param p_defaultClass         If no class is given use this as class to create an object
	 * @param p_custom               Parsing of node and sub nodes is using a custom procedure
	 * @param p_defaultSetMethod     Use this method to add object to its parent object
	 * @param p_needParent           true: If object can needs a parent node
	 */
	
	public XmlConfig(Class<?> p_baseClass,Class<?> p_defaultClass,boolean p_custom,String p_defaultSetMethod,boolean p_needParent) 
	{
		baseClass        = p_baseClass;
		defaultClass     = p_defaultClass;
		custom           = p_custom;
		defaultSetMethod = p_defaultSetMethod;
		needParent       = p_needParent;
	}

}
