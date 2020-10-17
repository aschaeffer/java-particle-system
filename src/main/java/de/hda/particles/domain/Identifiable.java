package de.hda.particles.domain;

/**
 * This interface makes an object identifiable by id.
 */
public interface Identifiable {

	/**
	 * Returns the id of the object.
	 * @return The id of the object.
	 */
	Integer getId();

	/**
	 * Sets the id of the object.
	 * @param id The id of the object.
	 */
	void setId(Integer id);
	
}
