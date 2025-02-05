package com.ecommerce_platform.service;

import com.ecommerce_platform.api.dto.request.ProductRequest;
import com.ecommerce_platform.api.dto.response.ProductResponse;
import com.ecommerce_platform.infra.exception.ProductNotFoundException;
import com.ecommerce_platform.repository.entity.Product;
import com.ecommerce_platform.infra.mapper.ProductMapper;
import com.ecommerce_platform.repository.repos.ProductRepository;
import com.ecommerce_platform.infra.util.LoggerUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;
    private final ProductMapper productMapper;

    public ProductResponse createProduct(ProductRequest request) {
        Product product = productMapper.toEntity(request);
        Product savedProduct = productRepository.save(product);
        LoggerUtil.logAction("Created product with ID: " + savedProduct.getId() + ", Name: " + savedProduct.getName());
        return productMapper.toDto(savedProduct);
    }

    public List<ProductResponse> getAllProducts() {
        List<ProductResponse> products = productRepository.findAll().stream()
                .map(productMapper::toDto)
                .collect(Collectors.toList());
        LoggerUtil.logAction("Retrieved " + products.size() + " products from the database.");
        return products;
    }

    public ProductResponse updateProduct(Long productId, ProductRequest request) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ProductNotFoundException("Product with ID " + productId + " not found"));

        product.setName(request.name());
        product.setDescription(request.description());
        product.setPrice(request.price());
        product.setStock(request.stock());

        Product updatedProduct = productRepository.save(product);
        LoggerUtil.logAction("Updated product with ID: " + updatedProduct.getId());
        return productMapper.toDto(updatedProduct);
    }

    public void deleteProduct(Long productId) {
        if (!productRepository.existsById(productId)) {
            LoggerUtil.logAction("Attempted to delete non-existent product with ID: " + productId);
            throw new ProductNotFoundException("Product with ID " + productId + " not found");
        }
        productRepository.deleteById(productId);
        LoggerUtil.logAction("Deleted product with ID: " + productId);
    }
}