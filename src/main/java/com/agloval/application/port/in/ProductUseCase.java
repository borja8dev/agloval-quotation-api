package com.agloval.application.port.in;

import com.agloval.application.dto.ProductRequest;
import com.agloval.application.dto.ProductResponse;

import java.util.List;

public interface ProductUseCase {

    ProductResponse createProduct(ProductRequest request);

    ProductResponse getProductById(Long id);

    List<ProductResponse> getAllProducts();

    ProductResponse updateProduct(Long id, ProductRequest request);

    void deleteProduct(Long id);
}
