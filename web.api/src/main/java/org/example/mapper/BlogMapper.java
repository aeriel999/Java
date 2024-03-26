package org.example.mapper;


import org.example.dto.blog.PostDTO;
import org.example.entities.blog.PostEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface BlogMapper {
    @Mapping(source = "blogCategory.name", target = "categoryName")
    @Mapping(source = "blogCategory.id", target = "categoryId")
    @Mapping(target = "tags", ignore = true)
    PostDTO postItemDto(PostEntity post);
}
