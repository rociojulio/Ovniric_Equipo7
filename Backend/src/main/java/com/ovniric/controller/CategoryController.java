package com.ovniric.controller;

import com.ovniric.model.Category;
import com.ovniric.service.CategoryService;
import com.ovniric.service.LocationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController

@RequestMapping("/api/categorias")
public class CategoryController { 


    private CategoryService categoryService;

    @Autowired
    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @PostMapping
    public ResponseEntity<Category> createCategory(@RequestBody Category category){
        return ResponseEntity.ok(categoryService.createCategory(category));
    }

    @GetMapping
    public ResponseEntity<List<Category>> getCategories(){
        return ResponseEntity.ok(categoryService.getCategories());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Category> getCategory(@PathVariable Long id){
        Optional<Category> categoryToSearch = categoryService.getCategory(id);
        if(categoryToSearch.isPresent()) {
            return ResponseEntity.ok(categoryToSearch.get());
        }else{
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping
    public ResponseEntity<String> updateCategory(@RequestBody Category category){
        Optional<Category> categoryToSearch = categoryService.getCategory(category.getCategoryId());
        if(categoryToSearch.isPresent()) {
            categoryService.updateCategory(category);
            return ResponseEntity.ok("The category has been updated");
        }else{
            return ResponseEntity.badRequest().body("The category has not been updated because the category" +
                    " does not exist");
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteCategory(@PathVariable Long id) {
        Optional<Category> categoryToSearch = categoryService.getCategory(id);
        if(categoryToSearch.isPresent()) {
            categoryService.deleteCategory(id);
            return ResponseEntity.ok("The category has been deleted");
        }else {
            return ResponseEntity.badRequest().body("The category does not exist");
        }

    }




}