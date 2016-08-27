package org.elaya.page.form;

public class CheckboxElement extends BuildInFormElement {

	public CheckboxElement()
	{
		super();
	}


	@Override
	public void  display(Object p_value) throws Exception {
		themeItem.checkBoxElement(getDomId(),getName(),p_value);

	}


	@Override
	public String getJsType() {
		return "checkbox";
	}


	@Override
	public String getJsClassName() {
		return "TCheckboxElement";
	}

}
