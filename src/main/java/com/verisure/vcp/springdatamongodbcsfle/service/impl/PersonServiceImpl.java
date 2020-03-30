package com.verisure.vcp.springdatamongodbcsfle.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.verisure.vcp.springdatamongodbcsfle.domain.entity.EncryptedPerson;
import com.verisure.vcp.springdatamongodbcsfle.domain.entity.Person;
import com.verisure.vcp.springdatamongodbcsfle.domain.entity.PersonEncryptionMapper;
import com.verisure.vcp.springdatamongodbcsfle.domain.repository.EncryptedPersonRepository;
import com.verisure.vcp.springdatamongodbcsfle.service.PersonService;

/**
 * Sample service implementation.
 *
 * @since 1.0.0
 * @author FaaS [faas@securitasdirect.es]
 */
@Service
//@Slf4j
public class PersonServiceImpl implements PersonService {

	@Autowired
	private EncryptedPersonRepository repository;

	@Autowired
	private PersonEncryptionMapper mapper;

	@Override
	public void createPerson(Person person) {
		// Encrypt the Person and save to EncryptedPerson
		EncryptedPerson ep = mapper.getEncrypedPerson(person);
		repository.save(ep);
	}

	@Override
	public List<Person> getPersons() {
		List<EncryptedPerson> encryptedPersons = repository.findAll();
		return toPersons(encryptedPersons);
	}

	public List<Person> toPersons(final List<EncryptedPerson> encryptedPersons) {
		List<Person> persons = null;
		if (encryptedPersons != null) {
			persons = encryptedPersons.parallelStream().map(mapper::getPerson).collect(Collectors.toList());
		}
		return persons;
	}

	@Override
	public List<Person> findByName(String name) {
		return toPersons(repository.findByName(name));		
	}

	@Override
	public Person findByDni(String dni) {
		return mapper.getPerson(repository.findByDni(mapper.getEncryptedDni(dni)));
	}

}
