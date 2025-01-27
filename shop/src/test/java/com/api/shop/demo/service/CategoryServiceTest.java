package com.api.shop.demo.service;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import com.api.shop.demo.exception.CreatedResourceError;
import com.api.shop.demo.model.Category;
import com.api.shop.demo.repository.CategoryRepository;
import com.api.shop.demo.service.category.CategoryService;

@ExtendWith(MockitoExtension.class)
public class CategoryServiceTest {

    @InjectMocks
    private CategoryService categoryService;

    @Mock
    private CategoryRepository categoryRepository;

    @Test
    public void createCategory(){
        Category category = new Category("Eletronic");
        Category categoryAdded = new Category("Eletronic");
        categoryAdded.setId(1L);
        when(this.categoryRepository.findByName(category.getName())).thenReturn(null);
        when(this.categoryRepository.save(category)).thenReturn(categoryAdded);
        Optional<Category> categoryOp = this.categoryService.add(category);
        Assertions.assertTrue(categoryOp.isPresent());
        Assertions.assertNotNull(categoryOp.get());
        Assertions.assertEquals(1L, categoryOp.get().getId());
    }

    @Test
    public void errorToCreateCategoryAlreadyExists(){
        Category category = new Category("Eletronic");   
        String error = "Error to create a category : Category name Eletronic already exixt, you should update it.";
        try{
            when(this.categoryRepository.findByName(category.getName())).thenReturn(category);
            this.categoryService.add(category);
        }catch(Exception errorEx){
            Assertions.assertEquals(error, errorEx.getMessage());
        }
    }

    @Test
    public void errorToCreateCategory(){
        Category category = new Category("Eletronic");   
        String error = "Error to create a category : null";
        try{
            when(this.categoryRepository.findByName(category.getName())).thenReturn(null);
            when(this.categoryRepository.save(category)).thenReturn(null);
            this.categoryService.add(category);
        }catch(Exception errorEx){
            Assertions.assertEquals(error, errorEx.getMessage());
        }
    }

    @Test
    public void errorToCreateCategoryThrowExceptionResourceCreate(){
        Category category = new Category("Eletronic");      
        String error = "Error to create a category : Resource not created";
        try{
            when(this.categoryRepository.findByName(category.getName())).thenReturn(null);
            when(this.categoryRepository.save(category)).thenThrow(new CreatedResourceError("Resource not created"));
            this.categoryService.add(category);
        }catch(CreatedResourceError errorEx){
            Assertions.assertEquals(error, errorEx.getMessage());
        }
    }

    @Test
    public void getAllCategories(){
        Category category = new Category("Eletronic");
        List<Category> categories = List.of(category);
        when(this.categoryRepository.findAll()).thenReturn(categories);
        Optional<List<Category>> categoriesOp = this.categoryService.getAll();
        Assertions.assertTrue(categoriesOp.isPresent());
        Assertions.assertEquals(1, categoriesOp.get().size());
        Assertions.assertEquals("Eletronic", categoriesOp.get().get(0).getName());
    }

    @Test
    public void errorGetAllCategories(){
        try{
            when(this.categoryRepository.findAll()).thenReturn(null);
            this.categoryService.getAll();
        }catch(Exception error){
            Assertions.assertEquals("There is not category regostery", error.getMessage());
        }
    }

    @Test
    public void getCategoryByName(){
        String name = "Eletronic";
        Category category = new Category(name);
        category.setId(1L);

        when(this.categoryRepository.findByName(name)).thenReturn(category);
        Optional<Category> categoryOp = this.categoryService.getByName(name);

        Assertions.assertTrue(categoryOp.isPresent());
        Assertions.assertEquals(name, categoryOp.get().getName());
    }

    @Test
    public void errorGetCategoryByName(){
        String name = "Eletronic";
        try{
            when(this.categoryRepository.findByName(name)).thenReturn(null);
            this.categoryService.getByName(name);
        }catch(Exception error){
            Assertions.assertEquals("Category not found name Eletronic", error.getMessage());
        }
    }

    @Test
    public void getCategoryById(){
        String id = "1";
        Category category = new Category();
        category.setName("home");
        category.setId(1L);

        when(this.categoryRepository.findById(id)).thenReturn(Optional.of(category));
        Optional<Category> categoryOp = this.categoryService.getById(id);

        Assertions.assertTrue(categoryOp.isPresent());
        Assertions.assertEquals(1L, categoryOp.get().getId());
        Assertions.assertEquals("home", categoryOp.get().getName());
    }


    @Test
    public void errorNotFoundToGetCategoryById(){
        String id = "1";
        try{
            when(this.categoryRepository.findById(id)).thenReturn(Optional.empty());
            this.categoryService.getById(id);
        }catch(Exception error){
            Assertions.assertEquals("Category not found id 1", error.getMessage());
        }
    }

    @Test
    public void deleteCategory(){
        String id = "1";
        this.categoryService.deleteById(id);
        verify(this.categoryRepository).deleteById(id);
    }


    @Test
    public void updateCategory(){

        String name = "Eletronic";
        Category category = new Category(name);
        category.setId(1L);

        Category categoryHome = new Category("Home");
        categoryHome.setId(1L);

        when(this.categoryRepository.findById("1")).thenReturn(Optional.of(categoryHome));
        when(this.categoryRepository.save(Mockito.any())).thenReturn(category);

        Optional<Category> categoryUpdatedd = this.categoryService.update(category, "1");
        Assertions.assertTrue(categoryUpdatedd.isPresent());
        Assertions.assertEquals("Eletronic", categoryUpdatedd.get().getName());

    }

    @Test
    public void errorUpdateCategory(){
        String name = "Eletronic";
        Category category = new Category(name);
        category.setId(1L);

        Category categoryHome = new Category("Home");
        categoryHome.setId(2L);
        when(this.categoryRepository.findById("1")).thenReturn(Optional.of(categoryHome));
        try{
            this.categoryService.update(category, "1");
        }catch(Exception error){
            Assertions.assertEquals("Different categories ids to the updated", error.getMessage());
        }
    }

    @Test
    public void errorUpdateNotFoundCategory(){
        String name = "Eletronic";
        Category category = new Category(name);
        category.setId(1L);

        Category categoryHome = new Category("Home");
        categoryHome.setId(2L);
        when(this.categoryRepository.findById("1")).thenReturn(Optional.of(categoryHome));
        try{
            when(this.categoryRepository.findById("1")).thenReturn(Optional.empty());
            this.categoryService.update(category, "1");
        }catch(Exception error){
            Assertions.assertEquals("Category not found id 1", error.getMessage());
        }
    }
}
