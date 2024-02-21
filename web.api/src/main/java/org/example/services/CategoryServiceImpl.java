package org.example.services;


import lombok.AllArgsConstructor;
import org.example.dto.CategoryItemDTO;
import org.example.entities.CategoryEntity;
import org.example.mapper.CategoryMapper;
import org.example.repositories.CategoryRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;


    @Override
    public Page<CategoryItemDTO> getAllCategories(Pageable pageable) {
        Page<CategoryEntity> categories = categoryRepository.findAll(pageable);
        return categories.map(categoryMapper::categoryItemDTO);
    }

    @Override
    public CategoryEntity getCategoryById(Integer categoryId) {
        return categoryRepository.findById(categoryId).orElse(null);
    }

    @Override
    public Page<CategoryEntity> searchCategories(String keyword, int page, int size) {

        return categoryRepository.searchByNameContaining(keyword, PageRequest.of(page, size));
    }
}
