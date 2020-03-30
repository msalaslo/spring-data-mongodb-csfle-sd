package com.verisure.vcp.springdatamongodbcsfle.api.converter;

import org.mapstruct.Mapper;

import com.verisure.vcp.springdatamongodbcsfle.api.dto.PersonDTO;
import com.verisure.vcp.springdatamongodbcsfle.domain.entity.Person;

/**
 * Person converter
 *
 * @since 1.0.0
 * @author FaaS [faas@securitasdirect.es]
 */
@Mapper(componentModel = "spring")
public interface PersonConverter {

    PersonDTO toPersonDto(Person person);
    Person toPerson(PersonDTO entry);

}