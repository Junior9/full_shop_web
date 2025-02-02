package com.api.shop.demo.service.category;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.api.shop.demo.exception.CreatedResourceError;
import com.api.shop.demo.exception.DeleteResourceException;
import com.api.shop.demo.exception.ResourceNotFound;
import com.api.shop.demo.exception.UpdateResourceError;
import com.api.shop.demo.model.Category;
import com.api.shop.demo.repository.CategoryRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class CategoryService implements CategoryServiceInter {

    private final CategoryRepository categoryRepository;

    @Override
    public Optional<List<Category>> getAll() {
        try{
            List<Category> categories = this.categoryRepository.findAll();
            return Optional.of(categories);
        }catch(Exception error){
            log.error("Error to get all categories :",error );
            throw new ResourceNotFound("There is not category regostery");
        }
    }

    @Override
    public Optional<Category> getByName(String name) {
        try{
            Category category = this.categoryRepository.findByName(name);
            return Optional.of(category);
        }catch(Exception error){
            System.err.println("Error to get category name : " + name + " error : " + error.getMessage());
            throw new ResourceNotFound("Category not found name " + name);
        }
    }

    @Override
    public Optional<Category> getById(Long id) {
        try{
            Optional<Category> categoryOp = this.categoryRepository.findById(id);
            if(categoryOp.isPresent()){
                return categoryOp;
            }else{
                System.err.println("Category not found id : " + id );
                throw new ResourceNotFound("Category not found id " + id);
            }
        }catch(Exception error){
            System.err.println("Error to get category id : " + id + " error : " + error.getMessage());
            throw new ResourceNotFound("Category not found id " + id );
        }
    }

    @Override
    public void deleteById(Long id) {
        try{
            this.categoryRepository.deleteById(id);
        }catch(Exception error){
            System.err.println("Error to delete categoryt by id: " + id);
            throw new DeleteResourceException ("Error to delete category : " + error.getMessage());
        }
    }

    public Optional<Category> add(Category category) {
        try{
            Category categoryExists = this.categoryRepository.findByName(category.getName());
            if(Objects.isNull(categoryExists)){
                Category categoryCreated = this.categoryRepository.save(category);
                return Optional.of(categoryCreated);
            }else{
                System.err.println("Category already exists" );
                throw new CreatedResourceError ("Category name " + category.getName() + " already exixt, you should update it.");
            }
        }catch(Exception errorEx){
            System.err.println("Error to create category: " + errorEx.getMessage());
            throw new CreatedResourceError ("Error to create a category : " + errorEx.getMessage());
        }
    }

    @Override
    public Optional<Category> update(Category categoryUpdate, Long id) {
        try{
            Optional<Category> categoryOp = getById(id);
            if(categoryOp.isPresent()){
                Category categoryToUpdate = this.updateWhithNewCategory(categoryUpdate, categoryOp.get());
                Category categoryUpdated = this.categoryRepository.save(categoryToUpdate);
                return Optional.of(categoryUpdated);
            }else{
                throw new ResourceNotFound("Category not found");
            }
        }catch(Exception error){
            System.err.println("Error to update category name : "  + categoryUpdate.getName() );
            throw new UpdateResourceError(error.getMessage());
        }
    }

    private Category updateWhithNewCategory(Category categoryUpdate, Category category) throws Exception {
        if(categoryUpdate.getId().equals(category.getId())) {
            category.setName(categoryUpdate.getName());
            return category;
        }else{
            throw new Exception("Different categories ids to the updated");
        }
    }

}
