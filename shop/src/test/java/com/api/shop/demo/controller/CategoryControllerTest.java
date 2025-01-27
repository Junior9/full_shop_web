package com.api.shop.demo.controller;

import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.api.shop.demo.model.Category;
import com.api.shop.demo.repository.CategoryRepository;
import com.api.shop.demo.service.category.CategoryService;
import com.fasterxml.jackson.databind.ObjectMapper;

@ExtendWith(MockitoExtension.class)
public class CategoryControllerTest {

    MockMvc mockMvc;
 
    @InjectMocks
    CategoryService service;

    @Mock
    CategoryRepository categoryRepository;

    @BeforeEach
    void init() {
        this.mockMvc = MockMvcBuilders.standaloneSetup(new CategoryController(service)).build();
    }

    @Test
    @DisplayName("Get categories from the api")
    public void getCategories() throws Exception {
        List<Category> categories = new ArrayList<>();   
        Category category = new Category("Eletronic");
        categories.add(category);

        when(this.categoryRepository.findAll()).thenReturn(categories);

        MvcResult result = this.mockMvc.perform(MockMvcRequestBuilders
                    .get("/api/category/all"))
                    .andExpect(MockMvcResultMatchers.status().isOk())
                    .andReturn();

        MockHttpServletResponse responseMock = result.getResponse();
        Assertions.assertEquals("{\"message\":\"Success\",\"data\":[{\"id\":null,\"name\":\"Eletronic\",\"products\":null}]}",    responseMock.getContentAsString());
    }

    @Test
    @DisplayName("Get category by id from the api")
    public void getById() throws Exception {
        List<Category> categories = new ArrayList<>();   
        Category category = new Category("Eletronic");
        category.setId(1L);
        categories.add(category);
        String id = "1";
        when(this.categoryRepository.findById(id)).thenReturn(Optional.empty());

        MvcResult result = this.mockMvc.perform(MockMvcRequestBuilders
                    .get("/api/category/id/1"))
                    .andExpect(MockMvcResultMatchers.status().isOk())
                    .andReturn();

        MockHttpServletResponse responseMock = result.getResponse();
        Assertions.assertEquals("{\"message\":\"Category not found id 1\",\"data\":null}",    responseMock.getContentAsString());
    }

    @Test
    @DisplayName("Get category by name from the api")
    public void getByName() throws Exception {
        List<Category> categories = new ArrayList<>();   
        Category category = new Category("Home");
        category.setId(1L);
        categories.add(category);
        when(this.categoryRepository.findByName("Home")).thenReturn(category);

        MvcResult result = this.mockMvc.perform(MockMvcRequestBuilders
                    .get("/api/category/name/Home"))
                    .andExpect(MockMvcResultMatchers.status().isOk())
                    .andReturn();

        MockHttpServletResponse responseMock = result.getResponse();
        Assertions.assertEquals("{\"message\":\"Success\",\"data\":{\"id\":1,\"name\":\"Home\",\"products\":null}}",    responseMock.getContentAsString());
    }

    @Test
    public void create() throws Exception{
        Category category = new Category("Pets");
        Category categoryAdded = new Category("Pets");
        categoryAdded.setId(1L);

        when(this.categoryRepository.save(Mockito.any())).thenReturn(categoryAdded);
        
        MvcResult result = this.mockMvc.perform(MockMvcRequestBuilders
        .post("/api/category/")
            .content(this.asJsonString(category))
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON))
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andReturn();
        
        MockHttpServletResponse responseMock = result.getResponse();
        Assertions.assertEquals("{\"message\":\"Success\",\"data\":{\"id\":1,\"name\":\"Pets\",\"products\":null}}",
            responseMock.getContentAsString());
    }

    @Test
    public void errorToCreateCategory() throws Exception{
        Category category = new Category("Pets");
        Category categoryAdded = new Category("Pets");
        categoryAdded.setId(1L);

        when(this.categoryRepository.save(Mockito.any())).thenReturn(null);
        
        MvcResult result = this.mockMvc.perform(MockMvcRequestBuilders
        .post("/api/category/")
            .content(this.asJsonString(category))
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON))
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andReturn();
        
        MockHttpServletResponse responseMock = result.getResponse();
        Assertions.assertEquals("{\"message\":\"Error to create a category : null\",\"data\":null}",
            responseMock.getContentAsString());
    }

    @Test
    public void errorToCreateCategoryAlreadyExixsts() throws Exception{
        Category category = new Category("Pets");
        Category categoryAdded = new Category("Pets");
        categoryAdded.setId(1L);
        when(this.categoryRepository.findByName("Pets")).thenReturn(categoryAdded);
        
        MvcResult result = this.mockMvc.perform(MockMvcRequestBuilders
        .post("/api/category/")
            .content(this.asJsonString(category))
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON))
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andReturn();
        
        MockHttpServletResponse responseMock = result.getResponse();
        Assertions.assertEquals("{\"message\":\"Error to create a category : Category name Pets already exixt, you should update it.\",\"data\":null}",
            responseMock.getContentAsString());
    }

    @Test
    public void deleteTest() throws Exception{
        String id = "1";
        MvcResult result = this.mockMvc.perform(MockMvcRequestBuilders
            .delete("/api/category/"+id))
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andReturn();

        MockHttpServletResponse responseMock = result.getResponse();
        Assertions.assertEquals("{\"message\":\"Success\",\"data\":null}",
            responseMock.getContentAsString());
    }

    private static String asJsonString(final Object obj) {
        try {
            final ObjectMapper mapper = new ObjectMapper();
            final String jsonContent = mapper.writeValueAsString(obj);
            return jsonContent;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    } 

}
