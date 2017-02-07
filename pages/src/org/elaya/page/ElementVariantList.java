package org.elaya.page;

public class ElementVariantList extends UniqueNamedObjectList<ElementVariant> {
	@Override
	public void put(ElementVariant variant) throws org.elaya.page.UniqueNamedObjectList.DuplicateItemName
	{
		super.put(variant);
	}
}
