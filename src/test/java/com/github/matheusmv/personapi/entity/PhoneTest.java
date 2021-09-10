package com.github.matheusmv.personapi.entity;

import com.github.matheusmv.personapi.enums.PhoneType;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.ThrowingSupplier;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

public class PhoneTest {

    @Test
    void shouldInstantiateAPhoneSuccessfully() {
        var phoneId = 1L;
        var phoneType = PhoneType.COMMERCIAL;
        var phoneNumber = "(88) 88888-8888";

        var testPhone = new Phone.PhoneBuilder()
                .id(phoneId)
                .type(phoneType)
                .number(phoneNumber)
                .build();

        assertAll("tests for Phone entity",
                () -> assertDoesNotThrow((ThrowingSupplier<Phone>) Phone::new),
                () -> assertDoesNotThrow(() -> new Phone(null, phoneType, phoneNumber)),
                () -> assertThat(phoneId, is(equalTo(testPhone.getId()))),
                () -> assertThat(phoneType, is(equalTo(testPhone.getType()))),
                () -> assertThat(phoneNumber, is(equalTo(testPhone.getNumber())))
        );
    }
}
