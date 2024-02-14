package org.example.mapper;

import org.example.dto.CategoryCreateDTO;
import org.example.dto.CategoryEditDTO;
import org.example.dto.CategoryItemDTO;
import org.example.entities.CategoryEntity;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CategoryMapper {
    CategoryItemDTO categoryItemDTO(CategoryEntity category);
    List<CategoryItemDTO> categoryItemDTOList(List<CategoryEntity> categories);
    CategoryEntity categoryCreateDto(CategoryCreateDTO categoryCreateDTO);
    CategoryEntity categoryEditDto(CategoryEditDTO categoryCreateDTO);

}
