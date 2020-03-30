package com.verisure.vcp.springdatamongodbcsfle.domain.entity;

import org.springframework.data.annotation.Id;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Person {

	@Id
	private String id;
	private String name;
	private int age;
	private String dni;

}