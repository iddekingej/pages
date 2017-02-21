package org.elaya.page;

/**
 *  Interface indicating object has a name
 *
 */
public interface NamedObject {
	/**
	 * Full name, this function is used for checking if the name is unique
	 * @return
	 */
	String getFullName();
	/**
	 * Get the name of the object.
	 * Can be a local name. For the full name use getFullName
	 * @return
	 */
	String getName();
}
