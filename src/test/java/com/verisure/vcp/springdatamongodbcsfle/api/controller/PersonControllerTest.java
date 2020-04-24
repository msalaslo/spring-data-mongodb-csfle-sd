package com.verisure.vcp.springdatamongodbcsfle.api.controller;

import com.verisure.vcp.springdatamongodbcsfle.api.converter.PersonConverter;
import com.verisure.vcp.springdatamongodbcsfle.domain.entity.Person;
import com.verisure.vcp.springdatamongodbcsfle.service.PersonService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;

import static org.hamcrest.core.Is.is;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
public class PersonControllerTest {

    @Mock
    PersonService personService;

    @Mock
    PersonConverter personConverter;

    @InjectMocks
    PersonController personController;

    MockMvc mockMvc;

    Person person;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(personController).build();
        Person.builder()
                .id("ITEM_CODE")
                .name("NAME")
                .build();
    }

    @Test
    void getAllItemsOK() throws Exception {
        given(personService.getPersons()).willReturn(Arrays.asList(person));
        mockMvc.perform(get("/persons"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.length()", is(1)));
    }

}
