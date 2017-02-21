package org.elaya.page;

import java.lang.reflect.InvocationTargetException;

import org.elaya.page.core.DynamicObject;
/**
 * Theme manager.
 * The object is responsible for loading the named themeItem
 * A themeItem is used for outputting the html of a widget
 */

public class Theme {
	private String baseName;
	private String defaultTheme;

	/**
	 * Theme constructor. 
	 * The widget only sets the class name of the theme.It is possible to change the layout of a widget
	 * by changing the package name. When a class is not found inside the given package
	 * the class is loaded from the default theme (by default this is org.elaya.pag.defaulttheme)
	 *  
	 * @param pbaseName  Package name of the theme.
	 *                 
	 */
	
	public Theme(String pbaseName)
	{
		baseName=pbaseName;
		defaultTheme="org.elaya.page.defaulttheme";
	}
	
	/**
	 * Same as Theme(String pbaseName) but also sets the default theme
	 * @param pbaseName
	 * @param pdefaultTheme
	 */
	public Theme(String pbaseName,String pdefaultTheme)
	{
		baseName=pbaseName;
		defaultTheme=pdefaultTheme;
	}
	
	/**
	 * Get the package name where to find the theme classes
	 * 
	 * @return
	 */
	
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
