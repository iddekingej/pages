package org.elaya.page.xml;

import java.lang.reflect.InvocationTargetException;

import org.elaya.page.ElementVariant;
import org.elaya.page.Errors.LoadingAliasFailed;
import org.elaya.page.Errors.NormalizeClassNameException;
import org.elaya.page.Errors.ReplaceVarException;
import org.elaya.page.application.AliasNamespace;
import org.elaya.page.application.Application;
import org.elaya.page.application.PageApplicationAware;
import org.elaya.page.xml.XMLParserBase.XMLLoadException;
import org.w3c.dom.Node;

public abstract  class XMLAppParser extends XMLParser implements PageApplicationAware {
	
	private Application application;

	@Override
	public void setApplication(Application papplication)
	{
		application=papplication;
	}
	

	@Override
	public Application getApplication()
	{
		return application;
	}

	@Override
	protected void initializeObject(Object object) throws ClassNotFoundException, InstantiationException, IllegalAccessException, InvocationTargetException, NoSuchMethodException, LoadingAliasFailed, org.elaya.page.xml.XMLParserBase.XMLLoadException 
	{
		if(object instanceof PageApplicationAware){
			((PageApplicationAware)object).setApplication(application);
		}
		super.initializeObject(object);
	}
	
	/**
	 * This method determines if node refers a "ElementVariant". 
	 * 
	 * return If the node refers to a element variant, the ElementVariant object is returned
	 *        Otherwise a "null" value is returned. 
	 */
	
	@Override
	protected ElementVariant getVariant(Node node) throws XMLLoadException, ReplaceVarException   
	{
		
		String className=this.getAttributeValue(node, "type");
		ElementVariant variant=null;
		if((className != null) && (className.charAt(0)=='@')){
			variant=getApplication().getVariantByName(className.substring(1));	
		}
		return variant;
	}
	
	@Override
	protected Object subParse(Node pnode,String pfileName) throws org.elaya.page.xml.XMLParserBase.XMLLoadException 
	{
		try{
			XMLConfig config=getConfig(pnode);
			Class<?> parserClass=config.getXMLParser();
			if(parserClass==null){
				return super.subParse(pnode, pfileName);
			}
			Object parserObject=parserClass.newInstance();			
			if(parserObject instanceof XMLParser){
				XMLParser parser=(XMLParser)parserObject;
				if(parserObject instanceof PageApplicationAware){
					((PageApplicationAware)parser).setApplication(application);
				}
				return parser.parse(pfileName);				
			} 
			throw new XMLLoadException("Invalid parser object type, XMLParser expected but, "+parserObject.getClass().getName()+" found",pnode);
		} 
		catch(XMLLoadException e){
			throw e;
		}
	    catch(Exception e){
			throw new XMLLoadException(e,pnode);
		}
	}

	
	public abstract AliasNamespace getAliasNamespace();
	@Override
	protected String normalizeClassName(String pname) throws NormalizeClassNameException  {
		return application.normalizeClassName(pname,getAliasNamespace());
	}



}
