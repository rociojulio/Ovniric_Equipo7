package com.ovniric.service;

import com.ovniric.model.Category;
import com.ovniric.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryService {
    private CategoryRepository categoryRepository;

    @Autowired
    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public Category createCategory(Category category) {
        return categoryRepository.save(category);
    }

    public Optional<Category> getCategory(Long id) {
        return categoryRepository.findById(id);
    }

    public List<Category> getCategories() {
        return categoryRepository.findAll();
    }

    public void updateCategory(Category category) {
        categoryRepository.save(category);
    }

    public void deleteCategory(Long id) {
        Optional<Category> categoryToDelete = getCategory(id);
        if(categoryToDelete.isPresent()) {
            categoryRepository.deleteById(id);
        }
    }
}
