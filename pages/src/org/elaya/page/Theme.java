package org.elaya.page;

import java.lang.reflect.InvocationTargetException;

import org.elaya.page.core.DynamicObject;

public class Theme {
	private String baseName;
	private String defaultTheme;

	public Theme(String pbaseName)
	{
		baseName=pbaseName;
		defaultTheme="org.elaya.page.defaulttheme";
	}
	
	public Theme(String pbaseName,String pdefaultTheme)
	{
		baseName=pbaseName;
		defaultTheme=pdefaultTheme;
	}
	
	public String getBaseName()
	{
		return baseName;
	}
	
	
	public String getDefaultTheme()
	{
		return defaultTheme;
	}
	

	
	public ThemeItemBase getThemeItem(String pname) throws ClassNotFoundException, InstantiationException, IllegalAccessException, InvocationTargetException, NoSuchMethodException
	{
		Object object;
		try{			
			object=DynamicObject.createObjectFromName(baseName+"."+pname);
		}catch(ClassNotFoundException e){
			object=DynamicObject.createObjectFromName(defaultTheme+"."+pname);
		}
		return (ThemeItemBase)(object);//TODO Check object casting
	}

}
