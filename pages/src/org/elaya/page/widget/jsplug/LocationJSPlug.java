package org.elaya.page.widget.jsplug;

import org.json.JSONException;

public class LocationJSPlug extends EventJSPlug {
	private String url;
	
	public void setUrl(String purl)
	{
		url=purl;
	}

	@Override
	public String getJs() throws JSONException {
		return "window.location="+toJsString(url);
	}

}
