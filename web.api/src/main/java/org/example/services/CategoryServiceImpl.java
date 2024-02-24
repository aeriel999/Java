package org.example.services;


import lombok.AllArgsConstructor;
import org.example.dto.CategoryCreateDTO;
import org.example.dto.CategoryEditDTO;
import org.example.dto.CategoryItemDTO;
import org.example.entities.CategoryEntity;
import org.example.mapper.CategoryMapper;
import org.example.repositories.CategoryRepository;
import org.example.storage.FileSaveFormat;
import org.example.storage.StorageService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@AllArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;
    private final StorageService storageService;

    @Override
    public Page<CategoryItemDTO> getAllCategories(Pageable pageable) {
        Page<CategoryEntity> categories = categoryRepository.findAll(pageable);
        return categories.map(categoryMapper::categoryItemDTO);
    }

    @Override
    public boolean delete(Integer categoryId) {
        var entity = categoryRepository.findById(categoryId).orElse(null);
        if (entity == null) {
            return false;
        }
        try {
            storageService.deleteImage(entity.getImage());
            categoryRepository.deleteById(categoryId);
            return true;
        }
        catch (Exception exception) {
            return false;
        }
    }

    @Override
    public CategoryItemDTO getById(Integer categoryId) {
        var entity = categoryRepository.findById(categoryId).orElse(null);
        if (entity == null) {
            return null;
        }
        return categoryMapper.categoryItemDTO(entity);
    }

    @Override
    public CategoryItemDTO create(CategoryCreateDTO model) {
        var entity = categoryMapper.categoryEntityByCategoryCreateDTO(model);
        entity.setCreationTime(LocalDateTime.now());
        String fileName = storageService.SaveImage(model.getFile(), FileSaveFormat.WEBP);
        entity.setImage(fileName);
        categoryRepository.save(entity);
        return categoryMapper.categoryItemDTO(entity);
    }

    @Override
    public CategoryItemDTO edit(CategoryEditDTO model) {
        var old = categoryRepository.findById(model.getId()).orElse(null);
        if (old == null) {
            return null;
        }
        var entity = categoryMapper.categoryEditDto(model);
        if(model.getFile()==null) {
            entity.setImage(old.getImage());
        }
        else {
            try {
                storageService.deleteImage(old.getImage());
                String fileName = storageService.SaveImage(model.getFile(), FileSaveFormat.WEBP);
                entity.setImage(fileName);
            }
            catch (Exception exception) {
                return null;
            }
        }
        categoryRepository.save(entity);
        return categoryMapper.categoryItemDTO(entity);
    }

    @Override
    public Page<CategoryEntity> searchCategories(String keyword, int page, int size) {
        return categoryRepository.searchByNameContaining(keyword, PageRequest.of(page, size));
    }
}
