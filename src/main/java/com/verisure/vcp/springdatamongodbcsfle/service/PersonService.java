package com.verisure.vcp.springdatamongodbcsfle.service;


import java.util.List;

import com.verisure.vcp.springdatamongodbcsfle.domain.entity.Person;

/**
 * Service interface to manage Persons
 *
 * @since 1.0.0
 * @author FaaS [faas@securitasdirect.es]
 */
public interface PersonService {

	/**
	 * Creates a person.
	 * 
	 * @param person The person item to create.
	 */
    void createPerson(Person person);

	/**
	 * Gets all the person.
	 * 
	 * @return The list of persons.
	 */
    List<Person> getPersons();
    
	/**
	 * Gets the person by name.
	 * 
	 * @return The list of persons.
	 */
    List<Person> findByName(String name);
    
	/**
	 * Gets the person by dni.
	 * 
	 * @return The list of persons.
	 */
    Person findByDni(String dni);

}
