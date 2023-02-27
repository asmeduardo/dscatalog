package com.asmeduardo.dscatalog.services;

import com.asmeduardo.dscatalog.dto.ProductDTO;
import com.asmeduardo.dscatalog.repositories.ProductRepository;
import com.asmeduardo.dscatalog.services.exceptions.ResourceNotFoundException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
public class ProductServiceIT {
    @Autowired
    private ProductService productService;

    @Autowired
    private ProductRepository productRepository;

    private Long existingId;
    private Long nonExistingId;
    private Long countTotalProducts;

    @BeforeEach
    void setUp() throws Exception {
        existingId = 1L;
        nonExistingId = 1000L;
        countTotalProducts = 25L;
    }

    @Test
    public void getAllProductsPagedShouldReturnAPageWhenPageZeroWithSizeTen() {

        Pageable pageable = PageRequest.of(0, 10);

        Page<ProductDTO> result = productService.getAllProductsPaged(pageable);

        Assertions.assertFalse(result.isEmpty());
        Assertions.assertEquals(0, result.getNumber());
        Assertions.assertEquals(10, result.getSize());
        Assertions.assertEquals(countTotalProducts, result.getTotalElements());
    }

    @Test
    public void getAllProductsPagedShouldReturnAnEmptyPageWhenPageDoesNotExist() {

        Pageable pageable = PageRequest.of(50, 10);

        Page<ProductDTO> result = productService.getAllProductsPaged(pageable);

        Assertions.assertTrue(result.isEmpty());
    }

    @Test
    public void getAllProductsPagedShouldReturnASortedPageWhenSortByName() {

        Pageable pageable = PageRequest.of(0, 10, Sort.by("name"));

        Page<ProductDTO> result = productService.getAllProductsPaged(pageable);

        Assertions.assertFalse(result.isEmpty());
        Assertions.assertEquals("Macbook Pro", result.getContent().get(0).getName());
        Assertions.assertEquals("PC Gamer", result.getContent().get(1).getName());
        Assertions.assertEquals("PC Gamer Alfa", result.getContent().get(2).getName());
    }

    @Test
    public void deleteProductShouldDeleteResourceWhenIdExists() {

        productService.deleteProduct(existingId);

        Assertions.assertEquals(countTotalProducts - 1, productRepository.count());
    }

    @Test
    public void deleteProductShouldThrowResourceNotFoundExceptionWhenIdDoesNotExist() {

        Assertions.assertThrows(ResourceNotFoundException.class, () -> {
            productService.deleteProduct(nonExistingId);
        });
    }
}
