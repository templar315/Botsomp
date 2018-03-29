package com.study.botsomp.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.study.botsomp.dto.ProductDTO;
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
@WebMvcTest(ProductController.class)
@ComponentScan
public class ProductControllerTest {

    private final String PRODUCTS = "/products";

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

    private ProductDTO product = ProductDTO.builder()
            .name("Steel hot-rolled channel #5")
            .type("Channel bars")
            .steelGrade("St5sp")
            .standard("GOST8240-89")
            .build();

    private ProductDTO product2 = ProductDTO.builder()
            .name("Steel hot-rolled channel #5")
            .type("Channel bars")
            .steelGrade("St3sp")
            .standard("GOST8240-89")
            .build();

    @Before
    public void setUp() throws Exception {
        when(productService.add(product)).thenReturn(product);
        when(productService.update(product)).thenReturn(product);
        when(productService.getOne(product.getId())).thenReturn(product);
        when(productService.delete(product.getId())).thenReturn(true);
        when(productService.findAll()).thenReturn(new ArrayList<>(Arrays.asList(product, product2)));
        when(productService.findByName(product.getName()))
                .thenReturn(new ArrayList<>(Arrays.asList(product, product2)));
        when(productService.findByType(product.getType()))
                .thenReturn(new ArrayList<>(Arrays.asList(product, product2)));
        when(productService.findByNameAndSteelGradeDesignation(product.getName(), product.getSteelGrade()))
                .thenReturn(product);
        when(productService.findByTypeAndSteelGradeDesignation(product.getType(), product.getSteelGrade()))
                .thenReturn(new ArrayList<>(Arrays.asList(product, product2)));
        when(productService.findBySteelGradeAndProductStandard(product.getSteelGrade(), product.getStandard()))
                .thenReturn(new ArrayList<>(Arrays.asList(product, product2)));
    }

    @Test
    public void add() throws Exception {
        String requestBody = objectMapper.writeValueAsString(product);

        mvc.perform(post(PRODUCTS)
                .accept(APPLICATION_JSON)
                .contentType(APPLICATION_JSON)
                .content(requestBody))
                .andExpect(status().isOk())
                .andExpect(content().string(requestBody));
    }

    @Test
    public void update() throws Exception {
        String contactsRequestBody = objectMapper.writeValueAsString(product);

        mvc.perform(put(PRODUCTS)
                .accept(APPLICATION_JSON)
                .contentType(APPLICATION_JSON)
                .content(contactsRequestBody))
                .andExpect(status().isOk())
                .andExpect(content().string(contactsRequestBody));
    }

    @Test
    public void delete() throws Exception {
        mvc.perform(MockMvcRequestBuilders.delete(PRODUCTS + "/" + product.getId()))
                .andExpect(status().isOk());

    }

    @Test
    public void getOne() throws Exception {
        mvc.perform(get(PRODUCTS + "/" + product.getId()))
                .andExpect(status().isOk());
    }

    @Test
    public void findAll() throws Exception {
        mvc.perform(get(PRODUCTS)
                .accept(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(objectMapper
                        .writeValueAsString(Arrays.asList(product, product2))));
    }

    @Test
    public void findByName() throws Exception {
        mvc.perform(get(PRODUCTS)
                .accept(APPLICATION_JSON)
                .param("name", product.getName()))
                .andExpect(status().isOk())
                .andExpect(content().string((objectMapper
                        .writeValueAsString(Arrays.asList(product, product2)))));
    }

    @Test
    public void findByType() throws Exception {
        mvc.perform(get(PRODUCTS)
                .accept(APPLICATION_JSON)
                .param("type", product.getType()))
                .andExpect(status().isOk())
                .andExpect(content().string((objectMapper
                        .writeValueAsString(Arrays.asList(product, product2)))));
    }

    @Test
    public void findByNameAndSteelGrade() throws Exception {
        mvc.perform(get(PRODUCTS)
                .accept(APPLICATION_JSON)
                .param("name", product.getName())
                .param("grade", product.getSteelGrade()))
                .andExpect(status().isOk())
                .andExpect(content().string((objectMapper
                        .writeValueAsString(product))));
    }

    @Test
    public void findByTypeAndSteelGrade() throws Exception {
        mvc.perform(get(PRODUCTS)
                .accept(APPLICATION_JSON)
                .param("type", product.getType())
                .param("grade", product.getSteelGrade()))
                .andExpect(status().isOk())
                .andExpect(content().string((objectMapper
                        .writeValueAsString(Arrays.asList(product, product2)))));
    }

    @Test
    public void findBySteelGradeAndStandard() throws Exception {
        mvc.perform(get(PRODUCTS)
                .accept(APPLICATION_JSON)
                .param("grade", product.getSteelGrade())
                .param("standard", product.getStandard()))
                .andExpect(status().isOk())
                .andExpect(content().string((objectMapper
                        .writeValueAsString(Arrays.asList(product, product2)))));
    }

}
