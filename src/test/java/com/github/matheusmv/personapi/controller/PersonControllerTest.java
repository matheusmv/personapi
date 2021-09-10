package com.github.matheusmv.personapi.controller;

import com.github.matheusmv.personapi.builder.PersonBuilder;
import com.github.matheusmv.personapi.service.PersonService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import java.util.Collections;

import static org.hamcrest.core.Is.is;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
public class PersonControllerTest {

    private static final String PERSON_API_URL_PATH = "/api/v1/people";

    private MockMvc mockMvc;

    @Mock
    private PersonService personService;

    @InjectMocks
    private PersonController personController;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(personController)
                .setCustomArgumentResolvers(new PageableHandlerMethodArgumentResolver())
                .setViewResolvers((viewName, locale) -> new MappingJackson2JsonView())
                .build();
    }

    @Test
    void whenGETListWithPersonsIsCalledThenOkStatusIsReturned() throws Exception {
        // given
        var personDTO = PersonBuilder.builder().build().toPersonDTO();

        // when
        when(personService.listAll()).thenReturn(Collections.singletonList(personDTO));

        // then
        mockMvc.perform(get(PERSON_API_URL_PATH).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].firstName", is(personDTO.getFirstName())))
                .andExpect(jsonPath("$[0].lastName", is(personDTO.getLastName())))
                .andExpect(jsonPath("$[0].cpf", is(personDTO.getCpf())))
                .andExpect(jsonPath("$[0].birthDate", is(personDTO.getBirthDate())))
                .andExpect(jsonPath("$[0].phones[0].type", is(personDTO.getPhones().get(0).getType().toString())))
                .andExpect(jsonPath("$[0].phones[0].number", is(personDTO.getPhones().get(0).getNumber())));
    }
}
