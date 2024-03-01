package org.example.controllers;

import lombok.AllArgsConstructor;
import org.example.dto.category.CategoryCreateDTO;
import org.example.dto.category.CategoryEditDTO;
import org.example.dto.category.CategoryItemDTO;
import org.example.dto.category.CategorySearchResultDTO;
import org.example.entities.CategoryEntity;
import org.example.services.CategoryService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("api/categories")

public class CategoryController {

    private final CategoryService categoryService;

    @GetMapping
    public ResponseEntity<Page<CategoryItemDTO>> index(Pageable pageable) {
        Page<CategoryItemDTO> categories = categoryService.getAllCategories(pageable);
        return ResponseEntity.ok(categories);
    }

    @PostMapping(value = "", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<CategoryItemDTO> create(@ModelAttribute CategoryCreateDTO model) {
        var result = categoryService.create(model);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @PutMapping("/{categoryId}")
    public ResponseEntity<CategoryItemDTO> edit(CategoryEditDTO model) {
        var result = categoryService.edit(model);
        if (result == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    // Method to delete a category by ID
    @DeleteMapping("/{categoryId}")
    public ResponseEntity<Void> delete(@PathVariable int categoryId) {
        var success = categoryService.delete(categoryId);
        if (!success) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/{categoryId}")
    public ResponseEntity<CategoryItemDTO> getById(@PathVariable int categoryId) {
        var result = categoryService.getById(categoryId);
        if (result == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping("/search")
    public ResponseEntity<CategorySearchResultDTO> searchCategories(
            @RequestParam (defaultValue = "")String keyword,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size) {
        CategorySearchResultDTO searchResult = categoryService.searchCategories(keyword, page, size);

        return new ResponseEntity<>(searchResult, HttpStatus.OK);
    }
}
