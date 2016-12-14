package org.elaya.page;

import java.util.Set;

import org.springframework.web.util.HtmlUtils;

public class ThemeItemBase {
	private String classPrefix="";

	public void setClassPrefix(String pclassPrefix)
	{
		classPrefix=pclassPrefix;	
	}

	public String getClassPrefix()
	{
		return classPrefix;
	}
	
	public void getCssFiles(Set<String> pfiles)
	{
		
	}

	
	public String str(Object pvalue){
		if(pvalue==null){
			return "";
		}
		return pvalue.toString();
	}
	
	public String escape(String pvalue)
	{
		return HtmlUtils.htmlEscape(str(pvalue));
	}
	
	public String escape(Object pvalue){
		return escape(str(pvalue));
	}
	
	public String property(String pname,Object pvalue)
	{
		return pname+"=\""+escape(pvalue)+"\" ";
	}
	
	public String propertyF(String pname,Object pvalue)
	{
		String str=str(pvalue);
		if(str.length()>0){
			return property(pname,pvalue);
		}
		return "";
	}
	
	protected String classProperty(String pclassName)
	{
		return property("class",classPrefix+pclassName);
	}


	
	public ThemeItemBase() {
	}
}
