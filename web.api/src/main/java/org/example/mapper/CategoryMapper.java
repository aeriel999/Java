package org.example.mapper;

import org.example.dto.CategoryCreateDTO;
import org.example.dto.CategoryEditDTO;
import org.example.dto.CategoryItemDTO;
import org.example.entities.CategoryEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CategoryMapper {

    @Mapping(source = "creationTime", target = "dateCreated", dateFormat = "dd.MM.yyyy HH:mm:ss")
    CategoryItemDTO categoryItemDTO(CategoryEntity category);

    List<CategoryItemDTO> categoryItemDTOList(List<CategoryEntity> categories);
    @Mapping(target = "image", ignore = true)
    CategoryEntity categoryCreateDto(CategoryCreateDTO categoryCreateDTO);

    @Mapping(target = "image", ignore = true)
    CategoryEntity categoryEditDto(CategoryEditDTO categoryEditDTO);

}
