package org.elaya.page;

public abstract class FormElement<themeType extends ThemeItemBase>  {
	protected Theme theme;
	protected themeType themeItem;
	
	private String label;
	private String name;
	public String getLabel(){ return label;}
	public void setLabel(String p_label){ label=p_label;}
	public String getName(){ return name;}
	public void setName(String p_name){ name=p_name;}
	public boolean hasValue(){ return true;}
	
	abstract void display(String p_value) throws Exception;
	
	
	abstract String getThemeName();
	
	@SuppressWarnings("unchecked")
	final public void setTheme(Theme p_theme) throws Exception
	{
		theme=p_theme;
		themeItem=(themeType)p_theme.getTheme(getThemeName());
	}
	
}
