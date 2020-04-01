package com.verisure.vcp.springdatamongodbcsfle.service.impl;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.verisure.vcp.springdatamongodbcsfle.domain.entity.EncryptedPerson;
import com.verisure.vcp.springdatamongodbcsfle.domain.entity.Person;
import com.verisure.vcp.springdatamongodbcsfle.domain.entity.PersonEncryptionMapper;
import com.verisure.vcp.springdatamongodbcsfle.domain.repository.EncryptedPersonRepository;

@Component
public class PersonExecutor {

	private static final Logger logger = LoggerFactory.getLogger(PersonExecutor.class);

	@Autowired
	private EncryptedPersonRepository encryptedPersonRepository;

	@Autowired
	private PersonEncryptionMapper personEntityHelper;

	private void clean() {
		encryptedPersonRepository.deleteAll();
	}

	public void runApplication() {
		clean();
		// Create a couple of non encrypted persons
		Person p1 = new Person();
		Person p2 = new Person();

		p1.setName("Miguel");
		p1.setAge(34);
		p1.setDni("11343122X");

		p2.setName("Pepe");
		p2.setAge(21);
		p2.setDni("11377122Y");

		// Encrypt the Person and save to EncryptedPerson
		EncryptedPerson ep1 = personEntityHelper.getEncrypedPerson(p1);
		EncryptedPerson ep2 = personEntityHelper.getEncrypedPerson(p2);
		// Save persons..
		encryptedPersonRepository.saveAll(Arrays.asList(new EncryptedPerson[] { ep1, ep2 }));

		// fetch all persons
		logger.debug("Persons found with findAll():");
		logger.debug("-------------------------------");

		boolean encrypted = false;
		List<Person> decryptedPersons = encryptedPersonRepository.findAll().stream()
				.map(ep -> personEntityHelper.getPerson(ep, encrypted)).collect(Collectors.toList());

		for (Person person : decryptedPersons) {
			logger.debug(person.toString());
		}

		// fetch an individual customer
		logger.debug("Person found with findByFirstName('Miguel'):");
		logger.debug("--------------------------------");

		List<EncryptedPerson> findByNamePerson = encryptedPersonRepository.findByName("Miguel");
		for (int i = 0; i < findByNamePerson.size(); i++) {
			EncryptedPerson ep = findByNamePerson.get(i);
			logger.info("findByNamePerson Equals Miguel Success: {}", ep.getName().equals("Miguel"));
		}

		// For Find by DNI we have to first get the binary version of DNI
		EncryptedPerson findByDni = encryptedPersonRepository
				.findByDni(personEntityHelper.getEncryptedDni("11343122X"));
		logger.info("findByDni equals Miguel Success: {}",
				personEntityHelper.getPerson(findByDni, false).getName().equals("Miguel"));

	}
}
