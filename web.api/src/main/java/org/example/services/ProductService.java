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

    ProductSearchResultDTO searchProducts(String name, int categoryId,
                                          String description, int page, int size);
}
