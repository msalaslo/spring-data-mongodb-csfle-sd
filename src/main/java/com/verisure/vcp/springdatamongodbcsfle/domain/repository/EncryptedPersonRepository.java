package com.verisure.vcp.springdatamongodbcsfle.domain.repository;

import java.util.List;

import org.bson.BsonBinary;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.verisure.vcp.springdatamongodbcsfle.domain.entity.EncryptedPerson;

@Repository
public interface EncryptedPersonRepository extends MongoRepository<EncryptedPerson, String> {
	public List<EncryptedPerson> findAll();

	public List<EncryptedPerson> findByName(String name);

	public EncryptedPerson findByDni(BsonBinary dni);
}
