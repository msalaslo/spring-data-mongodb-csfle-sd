package com.verisure.vcp.springdatamongodbcsfle.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import com.verisure.vcp.springdatamongodbcsfle.domain.entity.EncryptedPerson;
import com.verisure.vcp.springdatamongodbcsfle.domain.entity.Person;
import com.verisure.vcp.springdatamongodbcsfle.domain.repository.EncryptedPersonRepository;
import com.verisure.vcp.springdatamongodbcsfle.service.impl.PersonServiceImpl;

@SpringBootTest
public class PersonServiceTest {

    @InjectMocks
    private PersonService personService = new PersonServiceImpl();

    @Mock
    private EncryptedPersonRepository personRepository;


    @Test
    void findAll() {
        when(personRepository.findAll()).thenReturn(Arrays.asList(EncryptedPerson.builder().name("name").build()));
        List<Person> items = personService.getPersons();
        assertEquals(items.size(), 1);
        verify(personRepository, times(1)).findAll();
    }


    @ParameterizedTest(name = "Person   description: {0}, code: {1}")
    @CsvSource({
            "Description,    code",
            "Description 2,  code 2",
            "Description 3,  code 3",
            "Description 4, code 4"
    })
    void exampleParametrized(String name, String code) {
        EncryptedPerson item = EncryptedPerson.builder()
                .name(name)
                .id(code)
                .build();
        when(personRepository.findAll()).thenReturn(Arrays.asList(item));
        List<Person> items = personService.getPersons();
        assertEquals(items.size(), 1);
        assertEquals(items.get(0), item,
                () -> name + " should equal " + item.getName());

        verify(personRepository, times(1)).findAll();
    }

}
