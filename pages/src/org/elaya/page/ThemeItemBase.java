package org.elaya.page;

import java.util.Set;

import org.springframework.web.util.HtmlUtils;
/**
 * The HTML for a widget is generated inside a theme item object
 * "ThemeItembase" is the base class for such object
 *
 */

public class ThemeItemBase {
	/**
	 * Class prefix: This is added after the default classname
	 */
	private String classPrefix="";

	public void setClassPrefix(String pclassPrefix)
	{
		classPrefix=pclassPrefix;	
	}

	public String getClassPrefix()
	{
		return classPrefix;
	}
	
	/**
	 * Set the css files needed for the widget that is using this theme
	 * 
	 * @param pfiles
	 */
	public void getCssFiles(Set<String> pfiles)
	{
		/* This is an abstract class and doesn't have a css file*/
	}

	/**
	 * Convenient method to convert object to string
	 * When string is null, it is converted to a empty string
	 * or else toString is used
	 * 
	 * @param value
	 * @return string value of value
	 */
	
	public String str(Object value){
		if(value==null){
			return "";
		}
		return value.toString();
	}
	
	/**
	 * HTML escape string 
	 * 
	 * @param pvalue
	 * @return Html escaped string
	 */
	
	public String escape(String pvalue)
	{
		return HtmlUtils.htmlEscape(str(pvalue));
	}
	
	/**
	 * Convert object to string and html escape string 
	 * 
	 * @param pvalue
	 * @return
	 */
	
	public String escape(Object pvalue){
		return escape(str(pvalue));
	}
	
	/**
	 * Generate an attribute for an html node
	 * 
	 * @param pname    Attribute name 
	 * @param pvalue   Attribute value
	 * @return
	 */
	
	public String property(String pname,Object pvalue)
	{
		return pname+"=\""+escape(pvalue)+"\" ";
	}
	
	/**
	 * Generated attribute for a html tag, but if value is empty
	 * the tag is odmitted
	 * 
	 * @param pname
	 * @param pvalue
	 * @return
	 */
	public String propertyF(String pname,Object pvalue)
	{
		String str=str(pvalue);
		if(str.length()>0){
			return property(pname,pvalue);
		}
		return "";
	}
	
	/**
	 * create class='classname'  tag. Add class prefix to class. 
	 * @param pclassName
	 * @return
	 */
	
	protected String classProperty(String pclassName)
	{
		String className=pclassName;
		if(!classPrefix.isEmpty()){
			className=classPrefix+"_"+pclassName;
		}
		return property("class",className);
	}

}
