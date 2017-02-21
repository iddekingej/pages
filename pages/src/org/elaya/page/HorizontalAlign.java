package org.elaya.page;

/**
 * Enum of horizontal align into layout sizers
 *
 */
public enum HorizontalAlign {
	LEFT("left"),
	CENTER("center"),
	RIGHT("right");
	
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
