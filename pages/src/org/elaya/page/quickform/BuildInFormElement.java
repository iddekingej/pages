package org.elaya.page.quickform;


public abstract class BuildInFormElement extends FormElement<FormElementThemeItem> {

	public BuildInFormElement()
	{
		super();
	}
	
	@Override
	final public String getThemeName() {
		return "FormElementThemeItem";
	}

	

}
