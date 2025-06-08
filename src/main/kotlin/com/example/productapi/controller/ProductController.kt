package com.example.productapi.controller

import com.example.productapi.model.Product
import com.example.productapi.service.ProductService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.util.Optional

@RestController
@RequestMapping("/products")
class ProductController(private val productService: ProductService) {

    @PostMapping
    fun createProduct(@RequestBody product: Product): ResponseEntity<Product> {
        val createdProduct = productService.createProduct(product)
        return ResponseEntity(createdProduct, HttpStatus.CREATED)
    }

    @GetMapping
    fun getAllProducts(): ResponseEntity<List<Product>> {
        val products = productService.getAllProducts()
        return ResponseEntity(products, HttpStatus.OK)
    }

    @GetMapping("/{id}")
    fun getProductById(@PathVariable id: String): ResponseEntity<Product> {
        val productOptional: Optional<Product> = productService.getProductById(id)
        return productOptional.map { product ->
            ResponseEntity(product, HttpStatus.OK)
        }.orElseGet {
            ResponseEntity(HttpStatus.NOT_FOUND)
        }
    }

    @DeleteMapping("/{id}")
    fun deleteProduct(@PathVariable id: String): ResponseEntity<Void> {
        productService.deleteProduct(id) // This will throw ProductNotFoundException if not found (handled by ControllerAdvice)
        return ResponseEntity(HttpStatus.NO_CONTENT)
    }
}
