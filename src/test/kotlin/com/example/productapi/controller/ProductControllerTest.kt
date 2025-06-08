package com.example.productapi.controller

import com.example.productapi.advice.GlobalExceptionHandler
import com.example.productapi.exception.ProductNotFoundException
import com.example.productapi.model.Product
import com.example.productapi.service.ProductService
import com.fasterxml.jackson.databind.ObjectMapper
import org.junit.jupiter.api.Test
import org.mockito.Mockito.*
import org.mockito.kotlin.any // Added for mockito-kotlin's any()
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.context.annotation.Import
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.*
import java.util.Optional

@WebMvcTest(ProductController::class)
@Import(GlobalExceptionHandler::class) // Import GlobalExceptionHandler to test its behavior
class ProductControllerTest {

    @Autowired
    private lateinit var mockMvc: MockMvc

    @MockBean
    private lateinit var productService: ProductService

    @Autowired
    private lateinit var objectMapper: ObjectMapper

    @Test
    fun `createProduct should return created product and status 201`() {
        val product = Product(name = "New Product", description = "Desc", price = 20.0)
        val savedProduct = product.copy(id = "1")

        `when`(productService.createProduct(any())).thenReturn(savedProduct)

        mockMvc.perform(post("/products")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(product)))
            .andExpect(status().isCreated)
            .andExpect(jsonPath("\$.id").value("1"))
            .andExpect(jsonPath("\$.name").value("New Product"))
    }

    @Test
    fun `getAllProducts should return list of products and status 200`() {
        val products = listOf(Product(id = "1", name = "P1", description = "D1", price = 10.0))
        `when`(productService.getAllProducts()).thenReturn(products)

        mockMvc.perform(get("/products"))
            .andExpect(status().isOk)
            .andExpect(jsonPath("\$.size()").value(1))
            .andExpect(jsonPath("\$[0].id").value("1"))
    }

    @Test
    fun `getProductById should return product and status 200 when found`() {
        val product = Product(id = "1", name = "Test Product", description = "Desc", price = 10.0)
        `when`(productService.getProductById("1")).thenReturn(Optional.of(product))

        mockMvc.perform(get("/products/1"))
            .andExpect(status().isOk)
            .andExpect(jsonPath("\$.id").value("1"))
            .andExpect(jsonPath("\$.name").value("Test Product"))
    }

    @Test
    fun `getProductById should return status 404 when not found`() {
        `when`(productService.getProductById("1")).thenReturn(Optional.empty())

        mockMvc.perform(get("/products/1"))
            .andExpect(status().isNotFound)
    }

    @Test
    fun `deleteProduct should return status 204 when product exists`() {
        // Mocking a void method
        doNothing().`when`(productService).deleteProduct("1")

        mockMvc.perform(delete("/products/1"))
            .andExpect(status().isNoContent)

        verify(productService).deleteProduct("1")
    }

    @Test
    fun `deleteProduct should return status 404 when product does not exist`() {
        // productService.deleteProduct("1") will throw ProductNotFoundException
        // GlobalExceptionHandler will catch it and return 404
        `when`(productService.deleteProduct("1")).thenThrow(ProductNotFoundException("Product with id 1 not found"))

        mockMvc.perform(delete("/products/1"))
            .andExpect(status().isNotFound)
            .andExpect(jsonPath("\$.message").value("Product with id 1 not found"))

        verify(productService).deleteProduct("1")
    }
}
