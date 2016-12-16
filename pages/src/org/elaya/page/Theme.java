package org.elaya.page;

import java.lang.reflect.InvocationTargetException;

public class Theme {
	private String baseName;
	private String defaultTheme;
	
	public Theme(String pbaseName)
	{
		baseName=pbaseName;
		defaultTheme="org.elaya.page.defaulttheme";
	}
	
	public ThemeItemBase getThemeItem(String pname) throws ClassNotFoundException, InstantiationException, IllegalAccessException, InvocationTargetException, NoSuchMethodException
	{
		Class<?> className;
		try{			
			className=Class.forName(baseName+"."+pname);
		}catch(ClassNotFoundException e){
			className=Class.forName(defaultTheme+"."+pname);
		}
		Class<?>[] types={};
		Object[] params={};
		
		return (ThemeItemBase)(className.getConstructor(types).newInstance(params));
	}

}
