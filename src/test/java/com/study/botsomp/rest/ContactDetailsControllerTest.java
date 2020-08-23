package com.study.botsomp.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.study.botsomp.dto.ContactDetailsDTO;
import com.study.botsomp.service.*;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.ArrayList;
import java.util.Arrays;

import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(ContactDetailsController.class)
@ComponentScan
public class ContactDetailsControllerTest {

    private final String CONTACT_DETAILS = "/contacts";

    @MockBean
    private ContactDetailsService contactDetailsService;

    @MockBean
    private ProductService productService;

    @MockBean
    private StandardService standardService;

    @MockBean
    private ManufacturerService manufacturerService;

    @MockBean
    private SteelGradeService steelGradeService;

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper objectMapper;

    private ContactDetailsDTO contactDetails = ContactDetailsDTO
            .builder()
            .id(0L)
            .firstName("Vladimir")
            .lastName("Drobyshev")
            .position("Director of Commercial Affairs")
            .phone(380998541212L)
            .email("azovstal@com.ua")
            .manufacturer(1L)
            .build();
    private ContactDetailsDTO contactDetailsOut = ContactDetailsDTO
            .builder()
            .id(2L)
            .firstName("Vladimir")
            .lastName("Drobyshev")
            .position("Director of Commercial Affairs")
            .phone(380998541212L)
            .email("azovstal@com.ua")
            .manufacturer(1L)
            .build();

    private ContactDetailsDTO contactDetails2 = ContactDetailsDTO
            .builder()
            .id(3L)
            .firstName("Alex")
            .lastName("Vasilev")
            .position("Director of Commercial Affairs")
            .phone(380953256855L)
            .email("dmz@com.ua")
            .manufacturer(2L)
            .build();

    @Test
    public void add() throws Exception {
        when(contactDetailsService.add(contactDetails)).thenReturn(contactDetailsOut);
        mvc.perform(post(CONTACT_DETAILS)
                .accept(APPLICATION_JSON)
                .contentType(APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(contactDetails)))
                .andExpect(status().isOk())
                .andExpect(content().string(objectMapper.writeValueAsString(contactDetailsOut)));
    }

    @Test
    public void addWithId() throws Exception {
        mvc.perform(post(CONTACT_DETAILS)
                .accept(APPLICATION_JSON)
                .contentType(APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(contactDetails2)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void addWithValidationViolation() throws Exception {
        contactDetails.setEmail(null);
        mvc.perform(post(CONTACT_DETAILS)
                .accept(APPLICATION_JSON)
                .contentType(APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(contactDetails)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void update() throws Exception {
        when(contactDetailsService.update(contactDetails2)).thenReturn(contactDetails2);
        mvc.perform(put(CONTACT_DETAILS)
                .accept(APPLICATION_JSON)
                .contentType(APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(contactDetails2)))
                .andExpect(status().isOk())
                .andExpect(content().string(objectMapper.writeValueAsString(contactDetails2)));

    }

    @Test
    public void updateWithoutId() throws Exception {
        mvc.perform(put(CONTACT_DETAILS)
                .accept(APPLICATION_JSON)
                .contentType(APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(contactDetails)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void updateNonExistEntity() throws Exception {
        when(contactDetailsService.update(contactDetails2)).thenReturn(null);
        mvc.perform(put(CONTACT_DETAILS)
                .accept(APPLICATION_JSON)
                .contentType(APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(contactDetails2)))
                .andExpect(status().isNotFound());
    }

    @Test
    public void updateWithValidationViolation() throws Exception {
        contactDetails2.setLastName("");
        mvc.perform(put(CONTACT_DETAILS)
                .accept(APPLICATION_JSON)
                .contentType(APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(contactDetails2)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void delete() throws Exception {
        when(contactDetailsService.delete(contactDetailsOut.getId())).thenReturn(true);
        mvc.perform(MockMvcRequestBuilders.delete(CONTACT_DETAILS + "/" + contactDetailsOut.getId()))
                .andExpect(status().isOk());

    }

    @Test
    public void getOne() throws Exception {
        when(contactDetailsService.getOne(contactDetailsOut.getId())).thenReturn(contactDetailsOut);
        mvc.perform(get(CONTACT_DETAILS + "/" + contactDetailsOut.getId()))
                .andExpect(status().isOk())
                .andExpect(content().string(objectMapper.writeValueAsString(contactDetailsOut)));
    }

    @Test
    public void findAll() throws Exception {
        when(contactDetailsService.findAll())
                .thenReturn(new ArrayList<>(Arrays.asList(contactDetailsOut, contactDetails2)));
        mvc.perform(get(CONTACT_DETAILS)
                .accept(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(objectMapper
                        .writeValueAsString(Arrays.asList(contactDetailsOut, contactDetails2))));
    }
}
