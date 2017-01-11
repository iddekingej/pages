package org.elaya.page;
import java.util.HashMap;
import java.util.Map;

import org.w3c.dom.Node;

public class ElementVariant {
	public static class PropertyNotFoundException extends Exception{
		private static final long serialVersionUID = 4382532741034742320L;
		public PropertyNotFoundException(String name)
		{
			super(String.format("Property '%s' is mandatory, but not found",name));
		}
	}
	private String name;
	private Node   content;
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

	public boolean containsParameter(String name)
	{
		return parameters.containsKey(name);
	}
	
	public void addParameter(ElementVariantParameter parameter) throws UniqueNamedObjectList.DuplicateItemName
	{
		parameters.put(parameter);
	}
	
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
				if(parameter.getDefaultValue().isEmpty()){
					throw new PropertyNotFoundException(parameterName);
				} else {
					dataMap.put(parameterName,parameter.getDefaultValue());
				}
			}
		}
		return dataMap;
	}
}
