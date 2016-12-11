package org.elaya.page;

import java.lang.reflect.InvocationTargetException;

public class Theme {
	private String baseName;
	private String defaultTheme;
	
	public Theme(String p_baseName)
	{
		baseName=p_baseName;
		defaultTheme="org.elaya.page.defaultTheme";
	}
	
	public ThemeItemBase getThemeItem(String p_name) throws ClassNotFoundException, InstantiationException, IllegalAccessException, InvocationTargetException, NoSuchMethodException
	{
		Class<?> l_class;
		try{			
			l_class=Class.forName(baseName+"."+p_name);
		}catch(ClassNotFoundException l_e){
			l_class=Class.forName(defaultTheme+"."+p_name);
		}
		Class<?>[] l_types={};
		Object[] l_params={};
		
		return (ThemeItemBase)(l_class.getConstructor(l_types).newInstance(l_params));
	}

}
