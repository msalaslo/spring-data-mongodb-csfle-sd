package com.verisure.vcp.springdatamongodbcsfle.domain.entity;

import org.bson.BsonBinary;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Document(collection = "CSFLE-PERSON")
public class EncryptedPerson {

	@Id
	private String id;
	private String name;
	private int age;
	private BsonBinary dni;

}