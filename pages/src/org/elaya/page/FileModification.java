package org.elaya.page;

/**
 * List of file names and last modification time.
 * This is used for caching XML files. 
 */
class FileModification{
	/**
	 * Name of XML File.
	 */
	private String fileName;
	/**
	 * The modification time when the XMLFile was parsed. 
	 */
	private long modificationTime;
	
	/**
	 * Constructor 
	 * 
	 * @param pfileName          Name of XML File that is parse
	 * @param pmodificationTime  Modification time of the XML File
	 */
	public FileModification(String pfileName,long pmodificationTime){
		fileName=pfileName;
		modificationTime=pmodificationTime;
	}
	
	/**
	 * File name of XML file
	 */
	
	public String getFileName()
	{
		return fileName;
	}
	
	/**
	 * Modification time of the XML File
	 * @return
	 */
	
	public long getModificationTime()
	{
		return modificationTime;
	}
}
