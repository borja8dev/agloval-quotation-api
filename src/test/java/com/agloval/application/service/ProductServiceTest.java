package com.agloval.application.service;

import com.agloval.application.dto.ProductRequest;
import com.agloval.application.dto.ProductResponse;
import com.agloval.application.port.out.ProductRepositoryPort;
import com.agloval.domain.entity.Product;
import com.agloval.domain.enums.ProductCategory;
import com.agloval.domain.enums.SaleUnit;
import com.agloval.domain.exception.ProductNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ProductServiceTest {

    @Mock
    private ProductRepositoryPort productRepositoryPort;

    @InjectMocks
    private ProductService productService;

    @Test
    void createProduct_WhenValidRequest_ThenReturnsProductResponse() {
        when(productRepositoryPort.save(any(Product.class))).thenReturn(savedProduct());

        ProductResponse result = productService.createProduct(validRequest());

        assertThat(result.getId()).isEqualTo(1L);
        assertThat(result.getName()).isEqualTo("Oak Tablero 18mm");
        verify(productRepositoryPort).save(any(Product.class));
    }

    @Test
    void getProductById_WhenProductExists_ThenReturnsProductResponse() {
        when(productRepositoryPort.findById(1L)).thenReturn(Optional.of(savedProduct()));

        ProductResponse result = productService.getProductById(1L);

        assertThat(result.getId()).isEqualTo(1L);
        assertThat(result.getCategory()).isEqualTo(ProductCategory.TABLERO);
    }

    @Test
    void getProductById_WhenProductNotFound_ThenThrowsProductNotFoundException() {
        when(productRepositoryPort.findById(99L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> productService.getProductById(99L))
                .isInstanceOf(ProductNotFoundException.class)
                .hasMessageContaining("99");
    }

    @Test
    void getAllProducts_WhenProductsExist_ThenReturnsListOfResponses() {
        when(productRepositoryPort.findAll()).thenReturn(List.of(savedProduct(), savedProduct()));

        List<ProductResponse> result = productService.getAllProducts();

        assertThat(result).hasSize(2);
    }

    @Test
    void deleteProduct_WhenProductExists_ThenDeletesSuccessfully() {
        when(productRepositoryPort.findById(1L)).thenReturn(Optional.of(savedProduct()));

        productService.deleteProduct(1L);

        verify(productRepositoryPort).deleteById(1L);
    }

    @Test
    void deleteProduct_WhenProductNotFound_ThenThrowsProductNotFoundException() {
        when(productRepositoryPort.findById(99L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> productService.deleteProduct(99L))
                .isInstanceOf(ProductNotFoundException.class);

        verify(productRepositoryPort, never()).deleteById(any());
    }

    private ProductRequest validRequest() {
        return ProductRequest.builder()
                .name("Oak Tablero 18mm")
                .category(ProductCategory.TABLERO)
                .saleUnit(SaleUnit.TABLERO)
                .pricePerUnit(BigDecimal.valueOf(45.00))
                .build();
    }

    private Product savedProduct() {
        return Product.builder()
                .id(1L)
                .name("Oak Tablero 18mm")
                .category(ProductCategory.TABLERO)
                .saleUnit(SaleUnit.TABLERO)
                .pricePerUnit(BigDecimal.valueOf(45.00))
                .build();
    }
}
