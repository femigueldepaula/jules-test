package com.example.productapi.service

import com.example.productapi.exception.ProductNotFoundException
import com.example.productapi.model.Product
import com.example.productapi.repository.ProductRepository
import org.springframework.stereotype.Service
import java.util.Optional

@Service
class ProductService(private val productRepository: ProductRepository) {

    fun createProduct(product: Product): Product {
        return productRepository.save(product)
    }

    fun getAllProducts(): List<Product> {
        return productRepository.findAll()
    }

    fun getProductById(id: String): Optional<Product> {
        return productRepository.findById(id)
    }

    fun deleteProduct(id: String) {
        if (!productRepository.existsById(id)) {
            throw ProductNotFoundException("Product with id $id not found")
        }
        productRepository.deleteById(id)
    }
}
