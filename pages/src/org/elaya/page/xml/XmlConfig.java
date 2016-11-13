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

	public XmlConfig(Class<?> p_baseClass,Class<?> p_defaultClass,boolean p_custom,String p_defaultSetMethod,boolean p_needParent) 
	{
		baseClass        = p_baseClass;
		defaultClass     = p_defaultClass;
		custom           = p_custom;
		defaultSetMethod = p_defaultSetMethod;
		needParent       = p_needParent;
	}

}
