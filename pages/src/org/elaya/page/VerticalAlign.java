package org.elaya.page;

public enum VerticalAlign {
	
	top("top"),middle("middle"),bottom("bottom");
	private String tagValue;
	public String gettagValue()
	{
		return tagValue;
	}
	VerticalAlign(String p_tagValue)
	{
		tagValue=p_tagValue;
	}
}
