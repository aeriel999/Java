package org.example.services;

import org.example.dto.category.CategoryCreateDTO;
import org.example.dto.category.CategoryEditDTO;
import org.example.dto.category.CategoryItemDTO;
import org.example.dto.category.CategorySearchResultDTO;
import org.example.entities.CategoryEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public interface CategoryService {
    Page<CategoryItemDTO> getAllCategories(Pageable pageable);
    boolean delete(Integer categoryId);
    CategoryItemDTO getById(Integer categoryId);
    CategoryItemDTO create(CategoryCreateDTO model);
    CategoryItemDTO edit(CategoryEditDTO model);
    public CategorySearchResultDTO searchCategories(String keyword, int page, int size);
}
