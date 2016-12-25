package org.elaya.page;


public class Errors {
	private Errors()
	{
		
	}
	public static class PropertyNotSet extends Exception{

		private static final long serialVersionUID = -378654124608489073L;

		public PropertyNotSet(String pname)
		{
			super("Property '"+pname+"' is mandatory, but not set");
		}
	}
	public static class ValueNotFound extends Exception{

		private static final long serialVersionUID = -1297503130626285932L;

		public ValueNotFound(String pname){
			super("Value '"+pname+"' not found");
		}
	}
	
	public static class DuplicateElementOnPage extends Exception
	{
		private static final long serialVersionUID = 5670782478614650834L;
		
		public DuplicateElementOnPage(String pname)
		{
			super("Duplicate element name on page:"+pname);
		}
		
	}
	
	public static class InvalidElement extends Exception{

		private static final long serialVersionUID = -4997959418112427282L;
		public InvalidElement(Element<?> pelement,Element<?> pwhere){
			super("Wrong type of element("+pelement.getClass().getName()+"), a subelement of "+pelement.getClass().getName()+" "+pwhere);
		}
	}
	
	public static class InvalidObjectType extends Exception{
		
		private static final long serialVersionUID = -2061874935260687214L;

		public InvalidObjectType(Object pobject,String pexpected){
			super("Invalid type, "+pobject.getClass().getName()+" found but "+pexpected+" expected");
		}
	}
	
	public static class LoadingPageFailed extends Exception{

		private static final long serialVersionUID = -5787713033565235846L;
		public LoadingPageFailed(String pfileName){
			super("Loading page '"+pfileName+" failed");
		}
	}
	
	public static  class AliasNotFound extends Exception{

		private static final long serialVersionUID = -8957504944800562183L;
		
		public AliasNotFound(String palias)
		{
			super("Alias not found:"+palias);
		}
	}
	
	
	public static class LoadingAliasFailed extends Exception{

		private static final long serialVersionUID = 1L;
		
		public LoadingAliasFailed(String error){
			super(error);
		}
		
		public LoadingAliasFailed(String error,Throwable cause){
			super(error,cause);
		}
	}
	
	public static class NormalizeClassNameException extends Exception
	{

		private static final long serialVersionUID = 2077963158182896033L;
		
		public NormalizeClassNameException(String message,Throwable cause){
			super(message,cause);
		}
	}
	
	public static class SettingAttributeException extends Exception
	{
		private static final long serialVersionUID = 5025527259790385247L;

		public SettingAttributeException(String attribute,Object object,Throwable cause){
			super("Setting attribute "+attribute+" at object of class "+object.getClass().getName(),cause);
		}
	}

	public static class InvalidTypeException extends Exception{
		private static final long serialVersionUID = -3007635928476602360L;

		public InvalidTypeException(String message){
			super(message);
		}
	}
	
}
