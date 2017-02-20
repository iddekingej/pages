package org.elaya.page.xml;

public class XMLConfig {
	private Class<?>  baseClass;
	private Class<?>  defaultClass;
	private boolean   custom;
	private Class<?>  parentClass;
	private String    defaultSetMethod;    
	private Class<?>    xmlParser=null;
	/**
	 * Creates a xml parser config node. This information determens how to parse
	 * a xml expression to an object
	 * 
	 * @param pbaseClass            Specified class in tag must descent from this class
	 * @param pdefaultClass         If no class is given use this as class to create an object
	 * @param pcustom               Parsing of node and sub nodes is using a custom procedure
	 * @param pdefaultSetMethod     Use this method to add object to its parent object
	 * @param parentClass           Parent class must inherit from this class (extends,interface). Null when parent not neccesary
	 */
	
	public XMLConfig(Class<?> pbaseClass,Class<?> pdefaultClass,String pdefaultSetMethod,Class<?> pparentClass) 
	{
		baseClass        = pbaseClass;
		defaultClass     = pdefaultClass;
		custom           = false;
		defaultSetMethod = pdefaultSetMethod;
		parentClass      = pparentClass;
	}
	public void setXMLParser(Class<?> pparser)
	{
		xmlParser=pparser;
	}
	
	public Class<?> getXMLParser()
	{
		return xmlParser;
	}
	
	public Class<?> getBaseClass(){ return baseClass;}
	public Class<?> getDefaultClass(){ return defaultClass;}
	
	protected void  setCustom(boolean flag){
		custom=flag;
	}
	public boolean  getCustom(){ return custom;}
	public String   getDefaultSetMethod(){ return defaultSetMethod;}
	public Class<?>  getParentClass(){ return parentClass;}
	


}
