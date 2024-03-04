package org.example.services;

import org.example.dto.product.ProductCreateDTO;
import org.example.dto.product.ProductItemDTO;
import org.example.dto.product.ProductSearchResultDTO;
import org.example.dto.product.ProductEditDTO;


import java.util.List;

public interface ProductService {
    ProductItemDTO create(ProductCreateDTO model);
    List<ProductItemDTO> get();
    ProductItemDTO getById(Integer productId);
    ProductItemDTO edit(ProductEditDTO model);

    public ProductSearchResultDTO searchProducts(String keywordName, String keywordCategory,
                                                 String keywordDescription, int page, int size);

}
