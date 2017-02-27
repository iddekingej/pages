package org.elaya.page.core;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Objects;
/**
 * This is a list of one or more records
 * Each record contains a field  and a field is retrieved by name (fieldname)
 * Each record has exactly the same fields.
 * 
 * Before adding data, first add the field names with the addField method 
 *
 */
public class DataRecordList implements  Iterable<DataRecord>{

	class CantAddFieldAfterData extends DataException
	{

		private static final long serialVersionUID = -2358478922979707690L;

		public CantAddFieldAfterData()
		{
			super("Can't add fields after adding data to the list");
		}
	}
	/**
	 * List of field names
	 */
	private HashMap<String,Integer> fieldList=new HashMap<>();
	/**
	 * List of data records
	 */
	private LinkedList<DataRecord> items=new LinkedList<DataRecord>();
	
	/**
	 * Add a field name to the list of field. This method can only be called
	 * when there are no data added the the list.
	 * 
	 * @param pname Name of field, must be unique
	 * 
	 */
	private Data parent;
	
	
	
	public DataRecordList(Data pparent)
	{
		parent=pparent;
	}
	
	public Data getParent()
	{
		return parent;
	}
	
	/**
	 * Get the number of fields 
	 * 
	 * @return
	 */
	public int getNumberOfFields()
	{
		return fieldList.size();
	}
	
	public int getSize()
	{
		return items.size();
	}
	
	/**
	 * Get the position a field by field name
	 * 
	 * @param pname
	 * @return Field position
	 */
	public Integer getFieldPosition(String pname)
	{
		return fieldList.get(pname);
	}
	
	/**
	 * Checks if 
	 * 
	 * @param pname
	 * @throws DataException
	 */
	
	public boolean constainsField(String pfieldName)
	{
		return fieldList.containsKey(pfieldName);
	}
	
	public void addField(String pname) throws DataException
	{
		Objects.requireNonNull(pname);
		if(items.isEmpty()){
			if(fieldList.containsKey(pname)){
				throw new KeyAlreadyExists(pname);
			}
			fieldList.put(pname, fieldList.size());			
		} else {
			throw new CantAddFieldAfterData();
		}
	}
	/**
	 * Add an empty record to the list.
	 * 
	 * @return An empty record.
	 */
	public DataRecord addRecord()
	{
		DataRecord data=new DataRecord(this);
		items.add(data);
		return data;
	}
	
	/**
	 * Iterator to iterate through the data list.
	 */
	@Override
	public Iterator<DataRecord> iterator() {
		return items.iterator();
	}

}
