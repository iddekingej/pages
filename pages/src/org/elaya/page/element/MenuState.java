package org.elaya.page.element;

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
