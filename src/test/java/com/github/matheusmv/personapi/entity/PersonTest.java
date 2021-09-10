package com.github.matheusmv.personapi.entity;

import com.github.matheusmv.personapi.enums.PhoneType;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.ThrowingSupplier;

import java.time.LocalDate;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

public class PersonTest {

    @Test
    void shouldInstantiateAPersonSuccessfully() {
        var personId = 1L;
        var firstName = "Friedrich";
        var lastName = "Hayek";
        var cpf = "99988877766";
        var birthDate = LocalDate.parse("1899-03-08");
        var listOfPhones = List.of(
                new Phone(1L, PhoneType.MOBILE, "(99) 99999-9999"),
                new Phone(2L, PhoneType.HOME, "(88) 88888-8888"));

        var testPerson = new Person.PersonBuilder()
                .id(personId)
                .firstName(firstName)
                .lastName(lastName)
                .cpf(cpf)
                .birthDate(birthDate)
                .phones(listOfPhones)
                .build();

        assertAll("tests for Person entity",
                () -> assertDoesNotThrow((ThrowingSupplier<Person>) Person::new),
                () -> assertDoesNotThrow(() -> new Person(null, firstName, lastName, cpf, birthDate, null)),
                () -> assertDoesNotThrow(() -> new Person(personId, firstName, lastName, cpf, birthDate, null)),
                () -> assertDoesNotThrow(() -> new Person(personId, firstName, lastName, cpf, birthDate, listOfPhones)),
                () -> assertThat(personId, is(equalTo(testPerson.getId()))),
                () -> assertThat(firstName, is(equalTo(testPerson.getFirstName()))),
                () -> assertThat(lastName, is(equalTo(testPerson.getLastName()))),
                () -> assertThat(cpf, is(equalTo(testPerson.getCpf()))),
                () -> assertThat(birthDate, is(equalTo(testPerson.getBirthDate()))),
                () -> assertThat(listOfPhones, is(equalTo(testPerson.getPhones())))
        );
    }
}
