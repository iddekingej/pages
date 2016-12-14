package org.elaya.page;

public enum HorizontalAlign {
	LEFT("left"),CENTER("center"),RIGHT("right");
	private String tagValue;

	HorizontalAlign(String ptagValue)
	{
		tagValue=ptagValue;
	}
	public String gettagValue()
	{
		return tagValue;
	}

	
}
