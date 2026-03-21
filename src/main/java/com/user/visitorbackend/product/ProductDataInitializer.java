package com.user.visitorbackend.product;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;

@Component
public class ProductDataInitializer implements CommandLineRunner {

    private final ProductRepository productRepository;

    public ProductDataInitializer(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public void run(String... args) {
        if (productRepository.count() > 0) {
            return;
        }

        productRepository.saveAll(List.of(
                buildProduct("iPhone 15", "SKU-IP15", new BigDecimal("70000.00")),
                buildProduct("AirPods Pro", "SKU-APPRO", new BigDecimal("22000.00")),
                buildProduct("MacBook Air", "SKU-MBAIR", new BigDecimal("95000.00"))
        ));
    }

    private Product buildProduct(String name, String sku, BigDecimal configuredPrice) {
        Product product = new Product();
        product.setProductName(name);
        product.setSku(sku);
        product.setConfiguredPrice(configuredPrice);
        product.setActive(true);
        return product;
    }
}
