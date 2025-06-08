package com.example.productapi.service

import com.example.productapi.exception.ProductNotFoundException
import com.example.productapi.model.Product
import com.example.productapi.repository.ProductRepository
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.junit.jupiter.MockitoExtension
import java.util.Optional

@ExtendWith(MockitoExtension::class)
class ProductServiceTest {

    @Mock
    private lateinit var productRepository: ProductRepository

    @InjectMocks
    private lateinit var productService: ProductService

    @Test
    fun `createProduct should save and return product`() {
        val product = Product(name = "Test Product", description = "Description", price = 10.0)
        val savedProduct = product.copy(id = "1")

        `when`(productRepository.save(product)).thenReturn(savedProduct)

        val result = productService.createProduct(product)

        assertEquals(savedProduct, result)
        verify(productRepository).save(product)
    }

    @Test
    fun `getAllProducts should return list of products`() {
        val products = listOf(Product(id = "1", name = "P1", description = "D1", price = 10.0))
        `when`(productRepository.findAll()).thenReturn(products)

        val result = productService.getAllProducts()

        assertEquals(products, result)
        verify(productRepository).findAll()
    }

    @Test
    fun `getProductById should return product when found`() {
        val product = Product(id = "1", name = "Test Product", description = "Description", price = 10.0)
        `when`(productRepository.findById("1")).thenReturn(Optional.of(product))

        val result = productService.getProductById("1")

        assertTrue(result.isPresent)
        assertEquals(product, result.get())
        verify(productRepository).findById("1")
    }

    @Test
    fun `getProductById should return empty when not found`() {
        `when`(productRepository.findById("1")).thenReturn(Optional.empty())

        val result = productService.getProductById("1")

        assertFalse(result.isPresent)
        verify(productRepository).findById("1")
    }

    @Test
    fun `deleteProduct should call deleteById when product exists`() {
        `when`(productRepository.existsById("1")).thenReturn(true)
        doNothing().`when`(productRepository).deleteById("1")

        assertDoesNotThrow {
            productService.deleteProduct("1")
        }
        verify(productRepository).existsById("1")
        verify(productRepository).deleteById("1")
    }

    @Test
    fun `deleteProduct should throw ProductNotFoundException when product does not exist`() {
        `when`(productRepository.existsById("1")).thenReturn(false)

        val exception = assertThrows(ProductNotFoundException::class.java) {
            productService.deleteProduct("1")
        }
        assertEquals("Product with id 1 not found", exception.message)
        verify(productRepository).existsById("1")
        verify(productRepository, never()).deleteById("1")
    }
}
