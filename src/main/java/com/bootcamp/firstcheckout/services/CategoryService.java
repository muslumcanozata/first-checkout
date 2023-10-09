package com.bootcamp.firstcheckout.services;

import com.bootcamp.firstcheckout.daos.CategoryRepository;
import com.bootcamp.firstcheckout.domains.models.Category;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
public class CategoryService {
    private final CategoryRepository repository;

    public CategoryService(CategoryRepository repository) {
        this.repository = repository;
    }

    public Category getCategoryOrSaveCategoryIfItIsNotExist(Integer id){
        Optional<Category> categoryOptional = repository.findById(id);
        if(categoryOptional.isPresent()) {
            Category category = categoryOptional.get();
            log.info("Category {} fetched", category.getId());
            return category;
        }
        return createCategory(id);
    }

    private Category createCategory(Integer id) {
        Category category = new Category();
        category.setId(id);
        repository.save(category);
        log.info("Category saved");
        return category;
    }
}
