package org.elaya.page;
import java.util.HashMap;
import java.util.Map;

import org.w3c.dom.Node;

/**
 * A element variant is a xml snippet, which can be reused multiple times.
 * A element has parameter used for setting attributes of innert XML nodes. 
 *
 */
public class ElementVariant implements NamedObject{
	public static class PropertyNotFoundException extends Exception{
		private static final long serialVersionUID = 4382532741034742320L;
		public PropertyNotFoundException(String name)
		{
			super(String.format("Property '%s' is mandatory, but not found",name));
		}
	}
	/**
	 * Element variant name (must be unique) 
	 */
	
	private String name;
	
	/**
	 * The xml node contents 
	 * A ElementVariant can have multiple xml nodes, but must have 1 top node.
	 * The XML must be valid.
	 */
	private Node   content;
	/**
	 * The XML snippet can have named parameter to set attribute value (use $${name})
	 */
	private UniqueNamedObjectList<ElementVariantParameter> parameters=new UniqueNamedObjectList<>();
	
	public void setName(String pname)
	{
		name=pname;
	}
	
	public void setContent(Node pcontent)
	{
		content=pcontent;
	}
	
	public String getName()
	{
		return name;
	}
	
	public Node getContent()
	{
		return content;
	}

	/**
	 * Checks if  the snippet has a parameter 
	 * 
	 * @param name Name of the parameter to check
	 * @return     true - the snippet has a named parameter 'name'
	 *             false- has not 
	 */
	public boolean containsParameter(String name)
	{
		return parameters.containsKey(name);
	}
	
	/**
	 * Add parameter to snippet
	 * @param parameter  parameter to add to the snippet
	 * 
	 */
	public void addParameter(ElementVariantParameter parameter) throws UniqueNamedObjectList.DuplicateItemName
	{
		parameters.put(parameter);
	}
	
	/**
	 *  A ElementVariant can be used as follows: 
	 *  <element class='@veriantname' attr='bla' ....>
	 *  This method collect all attributes with a same name as a parameter 
	 *  are added to a hashmap.
	 *  This is used inside the element xml parser to replace the parameters 
	 *  inside the snippet($${var} variables)
	 *  The remaining attributes are used to set the values of the top object 
	 *  created by the top node   
	 *  
	 *  @param node  XML node containing the element variant parameters
	 */
	public Map<String,String> getDataFromNode(Node node) throws PropertyNotFoundException
	{
		Map<String,String> dataMap=new HashMap<>();
		Node value;
		String parameterName;
		ElementVariantParameter parameter; 
		for(Map.Entry<String,ElementVariantParameter> l_entry:parameters){
			parameterName=l_entry.getKey();
			parameter=l_entry.getValue();
			value=node.getAttributes().getNamedItem(parameterName);
			if(value !=null){
				dataMap.put(parameterName,value.getNodeValue());
			} else {
				if(parameter.getDefaultValue()==null){
					throw new PropertyNotFoundException(parameterName);
				} else {
					dataMap.put(parameterName,parameter.getDefaultValue());
				}
			}
		}
		return dataMap;
	}

	@Override
	public String getFullName() {
		return getName();
	}
}
