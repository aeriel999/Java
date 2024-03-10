package org.example.mapper;


import org.example.dto.product.ProductItemDTO;
import org.example.entities.shop.ProductEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ProductMapper {
    @Mapping(source = "category.name", target = "category")
    @Mapping(source = "category.id", target = "category_id")
    ProductItemDTO ProductItemDTOByProduct(ProductEntity product);

    List<ProductItemDTO> productItemDTOList(List<ProductEntity> product);

}
