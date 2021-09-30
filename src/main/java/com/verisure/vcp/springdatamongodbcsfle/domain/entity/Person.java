package com.verisure.vcp.springdatamongodbcsfle.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Person {

	private String id;
	private String name;
	private int age;
	private String dni;

}