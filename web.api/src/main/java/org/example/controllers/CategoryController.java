package org.example.controllers;

import lombok.AllArgsConstructor;
import org.example.dto.CategoryCreateDTO;
import org.example.dto.CategoryEditDTO;
import org.example.dto.CategoryItemDTO;
import org.example.entities.CategoryEntity;
import org.example.mapper.CategoryMapper;
import org.example.repositories.CategoryRepository;

import org.example.services.CategoryService;
import org.example.storage.FileSaveFormat;
import org.example.storage.StorageService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.MediaType;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RestController
@AllArgsConstructor
@RequestMapping("api/categories")

public class CategoryController {
    private final CategoryMapper categoryMapper;
    private final CategoryRepository categoryRepository;
    private final StorageService storageService;
    private final CategoryService categoryService;

    @GetMapping
    public ResponseEntity<Page<CategoryItemDTO>> index(Pageable pageable) {
//        List<CategoryEntity> list = categoryRepository.findAll();
//
//        var result =  categoryMapper.categoryItemDTOList(list);
//
//        return new ResponseEntity<>(result, HttpStatus.OK);
        Page<CategoryItemDTO> categories = categoryService.getAllCategories(pageable);
        return ResponseEntity.ok(categories);
    }

    @GetMapping("/{categoryId}")
    public ResponseEntity<CategoryItemDTO> getCategoryById(@PathVariable int categoryId) {
        Optional<CategoryEntity> optionalCategory = categoryRepository.findById(categoryId);

        if (optionalCategory.isPresent()) {
            CategoryEntity category = optionalCategory.get();
            CategoryItemDTO categoryItemDTO = categoryMapper.categoryItemDTO(category);
            return new ResponseEntity<>(categoryItemDTO, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/search")
    public ResponseEntity<Page<CategoryEntity>> searchCategories(
            @RequestParam String keyword,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size) {

        Page<CategoryEntity> searchResult = categoryService.searchCategories(keyword, page, size);

        if (searchResult.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(searchResult, HttpStatus.OK);
    }

    @PostMapping(value = "", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<CategoryEntity> addCategory(@ModelAttribute CategoryCreateDTO categoryCreate) {

        var category = categoryMapper.categoryCreateDto(categoryCreate);

        category.setCreationTime(LocalDateTime.now());

        String fileName = storageService.SaveImage(categoryCreate.getFile(), FileSaveFormat.WEBP);

        category.setImage(fileName);

        CategoryEntity savedCategory = categoryRepository.save(category);

        return new ResponseEntity<>(savedCategory, HttpStatus.CREATED);
    }

    // Method to delete a category by ID
    // Method to delete a category by ID
    @DeleteMapping("/{categoryId}")
    public ResponseEntity<Void> delete(@PathVariable int categoryId) {
        var entity = categoryRepository.findById(categoryId).orElse(null);
        if (entity == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        try {
            storageService.deleteImage(entity.getImage());
            categoryRepository.deleteById(categoryId);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        catch (Exception exception) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/{categoryId}")
    public ResponseEntity<CategoryEntity> updateCategory(CategoryEditDTO updatedCategoryDTO) {

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
            existingCategory.setDescription(category.getDescription());

            if(updatedCategoryDTO.getFile() != null)
            {
                try{
                    storageService.deleteImage(existingCategory.getImage());

                    String fileName = storageService.SaveImage(updatedCategoryDTO.getFile(), FileSaveFormat.WEBP);

                    existingCategory.setImage(fileName);
                }
                catch (Exception exception) {
                    return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
                }
            }

            // Save the updated category
            CategoryEntity updatedCategory = categoryRepository.save(existingCategory);

            return new ResponseEntity<>(updatedCategory, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
