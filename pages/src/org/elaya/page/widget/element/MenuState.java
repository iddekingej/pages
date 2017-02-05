package org.elaya.page.widget.element;

public enum MenuState {
	NORMAL("normal"),
	DISABLED("disabled"),
	HIDDEN("hidden");
	private String jsState;
	
	MenuState(String pjsState){
		jsState=pjsState;
	}
	public String getJsState(){ return jsState;}
	
}
