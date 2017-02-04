package org.elaya.page;

class FileModification{
	private String fileName;
	private long modificationTime;
	
	public FileModification(String pfileName,long pmodificationTime){
		fileName=pfileName;
		modificationTime=pmodificationTime;
	}
	
	public String getFileName()
	{
		return fileName;
	}
	
	public long getModificationTime()
	{
		return modificationTime;
	}
}
