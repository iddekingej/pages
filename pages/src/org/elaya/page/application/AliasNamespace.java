package org.elaya.page.application;

public enum AliasNamespace {
	ELEMENT("element"),
	JSFILE("jsfile"),
	CSSFILE("cssfile"),
	URL("url"),
	RECIEVER("reciever"),
	SECURITY("security"),
	ROUTER("router"),
	DATALAYER("datalayer")
	;
	
	private String tag;
	
	AliasNamespace(String ptag)
	{
		tag=ptag;
	}
	
	public String getTag()
	{
		return tag;
	}
	
	
}
