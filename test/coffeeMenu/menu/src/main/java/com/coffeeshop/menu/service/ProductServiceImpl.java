package com.coffeeshop.menu.service;


import com.coffeeshop.menu.model.Product;
import com.coffeeshop.menu.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service

public class ProductServiceImpl implements ProductService {
    @Autowired
    private ProductRepository productRepository;

    @Override
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    @Override
    public void saveProduct(Product product) {
        productRepository.save(product);
    }

    @Override
    public Product getProductById(int id) {
        Optional<Product> optional = this.productRepository.findById(id);
        Product product=null;
        if(optional.isPresent()){
            product=optional.get();
        } else{
            throw new RuntimeException("Product not found");
        }

        return product;
    }

    @Override
    public void deleteProductById(int id) {
        this.productRepository.deleteById(id);
    }
}
