package org.elaya.page;

public enum VerticalAlign {
	
	TOP("top"),MIDDLE("middle"),BOTTOM("bottom");
	
	private String tagValue;
	
	VerticalAlign(String ptagValue)
	{
		tagValue=ptagValue;
	}
	public String gettagValue()
	{
		return tagValue;
	}

}
