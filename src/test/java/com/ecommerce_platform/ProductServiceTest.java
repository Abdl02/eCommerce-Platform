package com.ecommerce_platform;

import com.ecommerce_platform.api.dto.request.ProductRequest;
import com.ecommerce_platform.api.dto.response.ProductResponse;
import com.ecommerce_platform.repository.entity.Product;
import com.ecommerce_platform.infra.mapper.ProductMapper;
import com.ecommerce_platform.repository.repos.ProductRepository;
import com.ecommerce_platform.service.ProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;

    @Mock
    private ProductMapper productMapper;

    @InjectMocks
    private ProductService productService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateProduct_Success() {
        // Arrange
        ProductRequest request = new ProductRequest("Laptop", "High-end gaming laptop", new BigDecimal("1500.00"), 10);
        Product product = new Product(null, "Laptop", "High-end gaming laptop", new BigDecimal("1500.00"), 10);
        Product savedProduct = new Product(1L, "Laptop", "High-end gaming laptop", new BigDecimal("1500.00"), 10);
        ProductResponse expectedResponse = new ProductResponse(1L, "Laptop", "High-end gaming laptop", new BigDecimal("1500.00"), 10);

        when(productMapper.toEntity(request)).thenReturn(product);
        when(productRepository.save(product)).thenReturn(savedProduct);
        when(productMapper.toDto(savedProduct)).thenReturn(expectedResponse);

        // Act
        ProductResponse response = productService.createProduct(request);

        // Assert
        assertNotNull(response);
        assertEquals("Laptop", response.name());
        verify(productRepository).save(product);
    }

    @Test
    void testGetAllProducts_Success() {
        // Arrange
        Product product = new Product(1L, "Laptop", "High-end gaming laptop", new BigDecimal("1500.00"), 10);
        ProductResponse productResponse = new ProductResponse(1L, "Laptop", "High-end gaming laptop", new BigDecimal("1500.00"), 10);

        when(productRepository.findAll()).thenReturn(Collections.singletonList(product));
        when(productMapper.toDto(product)).thenReturn(productResponse);

        // Act
        List<ProductResponse> products = productService.getAllProducts();

        // Assert
        assertEquals(1, products.size());
        assertEquals("Laptop", products.get(0).name());
        verify(productRepository).findAll();
    }
}