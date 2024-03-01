package org.example.services;


import org.example.dto.category.CategorySearchResultDTO;
import org.example.dto.product.ProductCreateDTO;
import org.example.dto.product.ProductItemDTO;
import org.example.dto.product.ProductSearchResultDTO;

import java.util.List;

public interface ProductService {
    ProductItemDTO create(ProductCreateDTO model);
    List<ProductItemDTO> get();

    public ProductSearchResultDTO searchProducts(String keywordName, String keywordCategory,
                                                 String keywordDescription, int page, int size);

}
