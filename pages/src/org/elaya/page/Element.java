package org.elaya.page;

public abstract class Element<themeType> {
	protected themeType themeItem;
	protected Theme theme;
	
	public abstract String getThemeName();
	public abstract void display() throws Exception;
	
	@SuppressWarnings("unchecked")
	final public void setTheme(Theme p_theme) throws Exception
	{
		theme=p_theme;
		themeItem=(themeType)p_theme.getTheme(getThemeName());
	}
	
	
}
