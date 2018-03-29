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
            .id(2L)
            .firstName("Vladimir")
            .lastName("Drobyshev")
            .position("Director of Commercial Affairs")
            .phone(380998541212L)
            .email("azovstal@com.ua")
            .build();

    private ContactDetailsDTO contactDetails2 = ContactDetailsDTO
            .builder()
            .id(3L)
            .firstName("Alex")
            .lastName("Vasilev")
            .position("Director of Commercial Affairs")
            .phone(380953256855L)
            .email("dmz@com.ua")
            .build();

    @Before
    public void setUp() throws Exception {
        when(contactDetailsService.findAll())
                .thenReturn(new ArrayList<>(Arrays.asList(contactDetails, contactDetails2)));
        when(contactDetailsService.add(contactDetails)).thenReturn(contactDetails);
        when(contactDetailsService.update(contactDetails)).thenReturn(contactDetails);
        when(contactDetailsService.getOne(contactDetails.getId())).thenReturn(contactDetails);
        when(contactDetailsService.delete(contactDetails.getId())).thenReturn(true);
    }

    @Test
    public void add() throws Exception {
        String contactsRequestBody = objectMapper.writeValueAsString(contactDetails);

        mvc.perform(post(CONTACT_DETAILS)
                .accept(APPLICATION_JSON)
                .contentType(APPLICATION_JSON)
                .content(contactsRequestBody))
                .andExpect(status().isOk())
                .andExpect(content().string(contactsRequestBody));
    }

    @Test
    public void update() throws Exception {
        String contactsRequestBody = objectMapper.writeValueAsString(contactDetails);

        mvc.perform(put(CONTACT_DETAILS)
                .accept(APPLICATION_JSON)
                .contentType(APPLICATION_JSON)
                .content(contactsRequestBody))
                .andExpect(status().isOk())
                .andExpect(content().string(contactsRequestBody));

    }

    @Test
    public void delete() throws Exception {
        mvc.perform(MockMvcRequestBuilders.delete(CONTACT_DETAILS + "/" + contactDetails.getId()))
                .andExpect(status().isOk());

    }

    @Test
    public void getOne() throws Exception {
        mvc.perform(get(CONTACT_DETAILS + "/" + contactDetails.getId()))
                .andExpect(status().isOk());
    }

    @Test
    public void findAll() throws Exception {
        mvc.perform(get(CONTACT_DETAILS)
                .accept(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(objectMapper
                        .writeValueAsString(Arrays.asList(contactDetails, contactDetails2))));
    }
}
