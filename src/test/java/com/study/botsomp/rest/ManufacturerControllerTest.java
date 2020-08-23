package com.study.botsomp.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.study.botsomp.dto.ManufacturerDTO;
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
@WebMvcTest(ManufacturerController.class)
@ComponentScan
public class ManufacturerControllerTest {

    private final String MANUFACTURERS = "/manufacturers";

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

    private ManufacturerDTO manufacturer = ManufacturerDTO.builder()
            .id(0)
            .name("Azovstal")
            .country("Ukraine")
            .region("Donetsk region")
            .city("Mariupol")
            .address("Str. Unclearly, 100")
            .build();
    private ManufacturerDTO manufacturerOut = ManufacturerDTO.builder()
            .id(1)
            .name("Azovstal")
            .country("Ukraine")
            .region("Donetsk region")
            .city("Mariupol")
            .address("Str. Unclearly, 100")
            .build();

    private ManufacturerDTO manufacturer2 = ManufacturerDTO.builder()
            .id(2)
            .name("DMZ")
            .country("Ukraine")
            .region("Donetsk region")
            .city("Donetsk")
            .address("Str. Unclearly, 100")
            .build();

    @Test
    public void add() throws Exception {
        when(manufacturerService.add(manufacturer)).thenReturn(manufacturerOut);
        mvc.perform(post(MANUFACTURERS)
                .accept(APPLICATION_JSON)
                .contentType(APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(manufacturer)))
                .andExpect(status().isOk())
                .andExpect(content().string(objectMapper.writeValueAsString(manufacturerOut)));
    }

    @Test
    public void addWithId() throws Exception {
        when(manufacturerService.add(manufacturer2)).thenReturn(manufacturer2);
        mvc.perform(post(MANUFACTURERS)
                .accept(APPLICATION_JSON)
                .contentType(APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(manufacturer2)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void addWithValidationViolation() throws Exception {
        manufacturer2.setAddress("");
        mvc.perform(post(MANUFACTURERS)
                .accept(APPLICATION_JSON)
                .contentType(APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(manufacturer2)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void update() throws Exception {
        String contactsRequestBody = objectMapper.writeValueAsString(manufacturer2);
        when(manufacturerService.update(manufacturer2)).thenReturn(manufacturer2);
        mvc.perform(put(MANUFACTURERS)
                .accept(APPLICATION_JSON)
                .contentType(APPLICATION_JSON)
                .content(contactsRequestBody))
                .andExpect(status().isOk())
                .andExpect(content().string(contactsRequestBody));
    }

    @Test
    public void updateNonExistEntity() throws Exception {
        when(manufacturerService.update(manufacturer2)).thenReturn(null);
        mvc.perform(put(MANUFACTURERS)
                .accept(APPLICATION_JSON)
                .contentType(APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(manufacturer2)))
                .andExpect(status().isNotFound());
    }

    @Test
    public void updateWithoutId() throws Exception {
        mvc.perform(put(MANUFACTURERS)
                .accept(APPLICATION_JSON)
                .contentType(APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(manufacturer)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void updateWithValidationViolation() throws Exception {
        manufacturer2.setCountry("");
        mvc.perform(put(MANUFACTURERS)
                .accept(APPLICATION_JSON)
                .contentType(APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(manufacturer2)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void delete() throws Exception {
        when(manufacturerService.delete(manufacturer.getId())).thenReturn(true);
        mvc.perform(MockMvcRequestBuilders.delete(MANUFACTURERS + "/" + manufacturer.getId()))
                .andExpect(status().isOk());
    }

    @Test
    public void getOne() throws Exception {
        when(manufacturerService.getOne(manufacturer.getId())).thenReturn(manufacturer);
        mvc.perform(get(MANUFACTURERS + "/" + manufacturer.getId()))
                .andExpect(status().isOk());
    }

    @Test
    public void findAll() throws Exception {
        when(manufacturerService.findAll())
                .thenReturn(new ArrayList<>(Arrays.asList(manufacturer, manufacturer2)));
        mvc.perform(get(MANUFACTURERS)
                .accept(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(objectMapper
                        .writeValueAsString(Arrays.asList(manufacturer, manufacturer2))));
    }

    @Test
    public void findByName() throws Exception {
        when(manufacturerService.findByName(manufacturer.getName())).thenReturn(manufacturer);
        mvc.perform(get(MANUFACTURERS)
                .accept(APPLICATION_JSON)
                .param("name", manufacturer.getName()))
                .andExpect(status().isOk())
                .andExpect(content().string((objectMapper
                        .writeValueAsString(manufacturer))));
    }

    @Test
    public void findByCountry() throws Exception {
        when(manufacturerService.findByCountry(manufacturer.getCountry()))
                .thenReturn(new ArrayList<>(Arrays.asList(manufacturer, manufacturer2)));
        mvc.perform(get(MANUFACTURERS)
                .accept(APPLICATION_JSON)
                .param("country", manufacturer.getCountry()))
                .andExpect(status().isOk())
                .andExpect(content().string(objectMapper
                        .writeValueAsString(Arrays.asList(manufacturer, manufacturer2))));
    }

    @Test
    public void findByCountryAndRegion() throws Exception {
        when(manufacturerService.findByCountryAndRegion(manufacturer.getCountry(), manufacturer.getRegion()))
                .thenReturn(new ArrayList<>(Arrays.asList(manufacturer, manufacturer2)));
        mvc.perform(get(MANUFACTURERS)
                .accept(APPLICATION_JSON)
                .param("country", manufacturer.getCountry())
                .param("region", manufacturer.getRegion()))
                .andExpect(status().isOk())
                .andExpect(content().string(objectMapper
                        .writeValueAsString(Arrays.asList(manufacturer, manufacturer2))));
    }

    @Test
    public void findByCity() throws Exception {
        when(manufacturerService.findByCity(manufacturer.getCity()))
                .thenReturn(new ArrayList<>(Arrays.asList(manufacturer, manufacturer2)));
        mvc.perform(get(MANUFACTURERS)
                .accept(APPLICATION_JSON)
                .param("city", manufacturer.getCity()))
                .andExpect(status().isOk())
                .andExpect(content().string(objectMapper
                        .writeValueAsString(Arrays.asList(manufacturer, manufacturer2))));
    }
}
