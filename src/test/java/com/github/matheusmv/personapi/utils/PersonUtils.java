package com.github.matheusmv.personapi.utils;

import com.github.matheusmv.personapi.dto.PersonDTO;
import com.github.matheusmv.personapi.dto.PhoneDTO;
import com.github.matheusmv.personapi.entity.Person;
import com.github.matheusmv.personapi.entity.Phone;
import com.github.matheusmv.personapi.enums.PhoneType;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

public class PersonUtils {

    private static final Long id = 1L;
    private static final String firstName = "Friedrich";
    private static final String lastName = "Hayek";
    private static final String cpf = "35562354470";
    private static final LocalDate birthDate = LocalDate.parse("1899-03-08");
    private static final List<Phone> phones = List.of(
            new Phone(1L, PhoneType.MOBILE, "(99)99999-9999"),
            new Phone(2L, PhoneType.HOME, "(88)88888-8888"));

    public static PersonDTO toPersonDTO() {
        var listOfPhoneDTO = phones.stream()
                .map(phone -> new PhoneDTO(phone.getId(), phone.getType(), phone.getNumber()))
                .collect(Collectors.toList());

        return new PersonDTO(
                id,
                firstName,
                lastName,
                cpf,
                birthDate.toString(),
                listOfPhoneDTO
        );
    }

    public static Person toPerson() {
        return new Person(
                id,
                firstName,
                lastName,
                cpf,
                birthDate,
                phones
        );
    }
}
