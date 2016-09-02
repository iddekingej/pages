package org.elaya.page;

public enum HorizontalAlign {
	left("left"),center("center"),right("right");
	
	private String tagValue;
	public String gettagValue()
	{
		return tagValue;
	}
	HorizontalAlign(String p_tagValue)
	{
		tagValue=p_tagValue;
	}
	
}
