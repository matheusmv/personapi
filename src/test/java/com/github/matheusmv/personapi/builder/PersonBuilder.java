package com.github.matheusmv.personapi.builder;

import com.github.matheusmv.personapi.dto.PersonDTO;
import com.github.matheusmv.personapi.dto.PhoneDTO;
import com.github.matheusmv.personapi.entity.Person;
import com.github.matheusmv.personapi.entity.Phone;
import com.github.matheusmv.personapi.enums.PhoneType;
import lombok.Builder;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Builder
public class PersonBuilder {

    @Builder.Default
    private final Long id = 1L;

    @Builder.Default
    private final String firstName = "Friedrich";

    @Builder.Default
    private final String lastName = "Hayek";

    @Builder.Default
    private final String cpf = "99988877766";

    @Builder.Default
    private final LocalDate birthDate = LocalDate.parse("1899-03-08");

    @Builder.Default
    private final List<Phone> phones = List.of(
            new Phone(1L, PhoneType.MOBILE, "(99) 99999-9999"),
            new Phone(2L, PhoneType.HOME, "(88) 88888-8888"));

    public PersonDTO toPersonDTO() {
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

    public Person toPerson() {
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
