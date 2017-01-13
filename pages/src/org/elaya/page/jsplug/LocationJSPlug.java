package org.elaya.page.jsplug;

public class LocationJSPlug extends EventJSPlug {
	private String url;
	
	public void setUrl(String purl)
	{
		url=purl;
	}

	@Override
	public String getJs() {
		return "window.location="+toJsString(url);
	}

}
