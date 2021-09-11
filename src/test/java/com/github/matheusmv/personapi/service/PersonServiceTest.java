package com.github.matheusmv.personapi.service;

import com.github.matheusmv.personapi.dto.mapper.PersonMapper;
import com.github.matheusmv.personapi.exception.ResourceNotFoundException;
import com.github.matheusmv.personapi.repository.PersonRepository;
import com.github.matheusmv.personapi.utils.PersonUtils;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class PersonServiceTest {

    private static final long INVALID_PERSON_ID = 2L;

    @Mock
    private PersonRepository personRepository;

    @Mock
    private PersonMapper personMapper;

    @InjectMocks
    private PersonService personService;

    @Test
    void whenListPersonIsCalledThenReturnAListOfPersons() {
        // given
        var person = PersonUtils.toPerson();
        var personDTO = PersonUtils.toPersonDTO();

        // when
        when(personMapper.toDTO(person)).thenReturn(personDTO);
        when(personRepository.findAll()).thenReturn(Collections.singletonList(person));

        // then
        var listOfPersonDTO = personService.listAll();

        assertAll("testing listAll",
                () -> assertThat(listOfPersonDTO, is(not(empty()))),
                () -> assertThat(listOfPersonDTO.get(0), is(equalTo(personDTO))));
    }

    @Test
    void whenValidPersonIdIsGivenThenReturnAPerson() {
        // given
        var expectedFoundPersonDTO = PersonUtils.toPersonDTO();
        var expectedFoundPerson = PersonUtils.toPerson();

        // when
        when(personMapper.toDTO(expectedFoundPerson)).thenReturn(expectedFoundPersonDTO);
        when(personRepository.findById(expectedFoundPerson.getId())).thenReturn(Optional.of(expectedFoundPerson));

        // then
        var foundPersonDTO = personService.getById(expectedFoundPerson.getId());

        assertThat(foundPersonDTO, is(equalTo(expectedFoundPersonDTO)));
    }

    @Test
    void whenInvalidPersonIdIsGivenThenThrowAnException() {
        // when
        when(personRepository.findById(INVALID_PERSON_ID)).thenReturn(Optional.empty());

        // then
        assertThrows(ResourceNotFoundException.class, () -> personService.getById(INVALID_PERSON_ID));
    }
}
