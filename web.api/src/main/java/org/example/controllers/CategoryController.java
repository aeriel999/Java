package org.example.controllers;


import lombok.AllArgsConstructor;
import org.example.dto.CategoryCreateDTO;
import org.example.dto.CategoryEditDTO;
import org.example.dto.CategoryItemDTO;
import org.example.entities.CategoryEntity;
import org.example.mapper.CategoryMapper;
import org.example.repositories.CategoryRepository;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("api/categories")
public class CategoryController {
    private final CategoryMapper categoryMapper;
    private final CategoryRepository categoryRepository;

    @GetMapping
    public ResponseEntity<List<CategoryItemDTO>> index() {
        List<CategoryEntity> list = categoryRepository.findAll();

        var result =  categoryMapper.categoryItemDTOList(list);

        return new ResponseEntity<>(result, HttpStatus.OK);
    }
    @PostMapping
    public ResponseEntity<CategoryEntity> addCategory(@RequestBody CategoryCreateDTO categoryCreate) {

        var category = categoryMapper.categoryCreateDto(categoryCreate);

        CategoryEntity savedCategory = categoryRepository.save(category);

        return new ResponseEntity<>(savedCategory, HttpStatus.CREATED);
    }

    // Method to delete a category by ID
    @DeleteMapping("/{categoryId}")
    public ResponseEntity<Void> deleteCategory(@PathVariable int categoryId) {
        categoryRepository.deleteById(categoryId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("/{categoryId}")
    public ResponseEntity<CategoryEntity> updateCategory(@RequestBody CategoryEditDTO updatedCategoryDTO) {

        var category = categoryMapper.categoryEditDto(updatedCategoryDTO);

        // Check if the category with the given ID exists
        if (!categoryRepository.existsById(category.getId())) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        // Retrieve the existing category from the database
        CategoryEntity existingCategory = categoryRepository.findById(category.getId()).orElse(null);

        // Map and update the fields from the DTO to the existing category
        if (existingCategory != null) {
            existingCategory.setName(category.getName());
            existingCategory.setImage(category.getImage());
            existingCategory.setDescription(category.getDescription());

            // Save the updated category
            CategoryEntity updatedCategory = categoryRepository.save(existingCategory);

            return new ResponseEntity<>(updatedCategory, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
