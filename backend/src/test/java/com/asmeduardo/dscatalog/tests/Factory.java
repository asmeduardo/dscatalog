package com.asmeduardo.dscatalog.tests;

import com.asmeduardo.dscatalog.dto.ProductDTO;
import com.asmeduardo.dscatalog.models.Category;
import com.asmeduardo.dscatalog.models.Product;

import java.time.Instant;

public class Factory {

    public static Product createProduct() {
        Product product = new Product(1L, "TV", "Ótima TV", 1000.0,
                "https://img.com/img.png", Instant.now());
        product.getCategories().add(createCategory());
        return product;
    }

    public static ProductDTO createProductDTO() {
        Product product = createProduct();
        return new ProductDTO(product, product.getCategories());
    }

    public static Category createCategory() {
        return new Category(1L, "Eletrônicos");
    }
}
