package com.verisure.vcp.springdatamongodbcsfle.api.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.verisure.vcp.springdatamongodbcsfle.api.converter.PersonConverter;
import com.verisure.vcp.springdatamongodbcsfle.api.dto.PersonDTO;
import com.verisure.vcp.springdatamongodbcsfle.service.PersonService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;

/**
 * Sample controller used as template. <b>Please remove for actual project
 * implementation.</b>
 *
 * @since 1.0.0
 * @author FaaS [faas@securitasdirect.es]
 */
@Slf4j
@RestController
@RequestMapping("/persons")
@Tag(name = "Person controller")
public class PersonController {

	@Autowired
	private PersonConverter personConverter;

	@Autowired
	private PersonService personService;

	@GetMapping(produces = "application/json")
	@ResponseBody
	@Operation(description = "get persons", responses = {
			@ApiResponse(content = @Content(array = @ArraySchema(schema = @Schema(implementation = PersonDTO.class))), responseCode = "200") })
	public List<PersonDTO> getPersonBy(@RequestParam(required = false) final String name,
			@RequestParam(required = false) final String dni) {
		LOGGER.debug("getPersons::Trying to retrieve all persons");
		if (dni != null) {
			List<PersonDTO> persons = new ArrayList<PersonDTO>();
			persons.add(personConverter.toPersonDto(personService.findByDni(dni)));
			return persons;
		} else if (name != null) {
			return personService.findByName(name).stream().map(personConverter::toPersonDto)
					.collect(Collectors.toList());
		} else {
			return personService.getPersons().stream().map(personConverter::toPersonDto).collect(Collectors.toList());
		}
	}

	@PostMapping(consumes = "application/json", produces = "application/json")
	@ResponseBody
	@ResponseStatus(HttpStatus.CREATED)
	@Operation(description = "Create a person")
	public void createPerson(@Valid @RequestBody PersonDTO request) {
		LOGGER.debug("Creating a person: {}", request.toString());
		personService.createPerson(personConverter.toPerson(request));
	}

}
