package com.github.matheusmv.personapi.service;

import com.github.matheusmv.personapi.builder.PersonBuilder;
import com.github.matheusmv.personapi.dto.mapper.PersonMapper;
import com.github.matheusmv.personapi.repository.PersonRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class PersonServiceTest {

    @Mock
    private PersonRepository personRepository;

    @Mock
    private PersonMapper personMapper;

    @InjectMocks
    private PersonService personService;

    @Test
    void whenListPersonIsCalledThenReturnAListOfPersons() {
        // given
        var person = PersonBuilder.builder().build().toPerson();
        var personDTO = personMapper.toDTO(person);

        // when
        when(personRepository.findAll()).thenReturn(Collections.singletonList(person));

        // then
        var listOfPersonDTO = personService.listAll();

        assertAll("testing listAll",
                () -> assertThat(listOfPersonDTO, is(not(empty()))),
                () -> assertThat(listOfPersonDTO.get(0), is(equalTo(personDTO))));
    }
}
