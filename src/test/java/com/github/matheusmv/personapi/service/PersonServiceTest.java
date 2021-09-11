package com.github.matheusmv.personapi.service;

import com.github.matheusmv.personapi.dto.mapper.PersonMapper;
import com.github.matheusmv.personapi.entity.Person;
import com.github.matheusmv.personapi.exception.CPFAlreadyRegisteredException;
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
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class PersonServiceTest {

    private static final long INVALID_PERSON_ID = 2L;
    private static final String CPF_ALREADY_REGISTERED = "21536261882";

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

    @Test
    void whenGivenPersonDTOThenReturnSavedPersonDTO() {
        // given
        var expectedPersonDTO = PersonUtils.toPersonDTO();
        var expectedSavedPerson = PersonUtils.toPerson();

        // when
        when(personMapper.toModel(expectedPersonDTO)).thenReturn(expectedSavedPerson);
        when(personRepository.save(any(Person.class))).thenReturn(expectedSavedPerson);
        when(personMapper.toDTO(expectedSavedPerson)).thenReturn(expectedPersonDTO);

        // then
        var savedPerson = personService.create(expectedPersonDTO);

        assertAll("testing create",
                () -> assertThat(savedPerson, is(not(Optional.empty()))),
                () -> assertThat(savedPerson.getId(), is(equalTo(expectedPersonDTO.getId()))),
                () -> assertThat(savedPerson.getFirstName(), is(equalTo(expectedPersonDTO.getFirstName()))),
                () -> assertThat(savedPerson.getPhones(), is(equalTo(expectedPersonDTO.getPhones()))));
    }

    @Test
    void whenGivenPersonDTOWithAlreadyRegisteredCPFThenThrowAnException() {
        // given
        var personDTO = PersonUtils.toPersonDTO();
        personDTO.setCpf(CPF_ALREADY_REGISTERED);

        var duplicatedPerson = PersonUtils.toPerson();
        duplicatedPerson.setCpf(CPF_ALREADY_REGISTERED);

        // when
        when(personRepository.findByCpf(personDTO.getCpf())).thenReturn(Optional.of(duplicatedPerson));

        // then
        assertThrows(CPFAlreadyRegisteredException.class, () -> personService.create(personDTO));
    }

    @Test
    void whenUpdateIsCalledThenUpdateAPersonDetails() {
        // given
        var expectedUpdatedPersonDTO = PersonUtils.toPersonDTO();
        var expectedUpdatedPerson = PersonUtils.toPerson();

        // when
        when(personRepository.findById(expectedUpdatedPerson.getId())).thenReturn(Optional.of(expectedUpdatedPerson));
        when(personMapper.toModel(expectedUpdatedPersonDTO)).thenReturn(expectedUpdatedPerson);
        when(personRepository.save(any(Person.class))).thenReturn(expectedUpdatedPerson);
        when(personMapper.toDTO(expectedUpdatedPerson)).thenReturn(expectedUpdatedPersonDTO);

        // then
        var updatedPerson = personService.update(expectedUpdatedPerson.getId(), expectedUpdatedPersonDTO);

        assertAll("test update",
                () -> assertThat(updatedPerson, equalTo(expectedUpdatedPersonDTO)),
                () -> assertThat(updatedPerson.getId(), is(equalTo(expectedUpdatedPersonDTO.getId()))),
                () -> assertThat(updatedPerson.getFirstName(), is(equalTo(expectedUpdatedPersonDTO.getFirstName()))));
    }

    @Test
    void whenUpdateIsCalledWithInvalidIdThrownAnException() {
        // given
        var personDTO = PersonUtils.toPersonDTO();

        // when
        when(personRepository.findById(INVALID_PERSON_ID)).thenReturn(Optional.empty());

        // then
        assertThrows(ResourceNotFoundException.class, () -> personService.update(INVALID_PERSON_ID, personDTO));
    }
}
