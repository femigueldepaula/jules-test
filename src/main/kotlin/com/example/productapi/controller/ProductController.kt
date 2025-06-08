package com.example.productapi.controller

import com.example.productapi.model.Product
import com.example.productapi.service.ProductService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.util.Optional

@RestController
@RequestMapping("/products")
@Tag(name = "Product API", description = "Endpoints para gerenciamento de produtos")
class ProductController(private val productService: ProductService) {

    @PostMapping
    @Operation(summary = "Cria um novo produto", description = "Registra um novo produto no sistema.")
    @ApiResponses(value = [
        ApiResponse(responseCode = "201", description = "Produto criado com sucesso",
            content = [Content(mediaType = "application/json", schema = Schema(implementation = Product::class))]),
        ApiResponse(responseCode = "400", description = "Requisição inválida (e.g., campos faltando)")
        // Add other relevant responses if applicable
    ])
    fun createProduct(@RequestBody product: Product): ResponseEntity<Product> {
        val createdProduct = productService.createProduct(product)
        return ResponseEntity(createdProduct, HttpStatus.CREATED)
    }

    @GetMapping
    @Operation(summary = "Lista todos os produtos", description = "Retorna uma lista de todos os produtos cadastrados.")
    @ApiResponse(responseCode = "200", description = "Lista de produtos retornada com sucesso",
        content = [Content(mediaType = "application/json", schema = Schema(implementation = Product::class))]) // Note: For lists, Schema should ideally wrap Product in an array type. Springdoc might infer this.
    fun getAllProducts(): ResponseEntity<List<Product>> {
        val products = productService.getAllProducts()
        return ResponseEntity(products, HttpStatus.OK)
    }

    @GetMapping("/{id}")
    @Operation(summary = "Busca um produto pelo ID", description = "Retorna um produto específico baseado no seu ID.")
    @ApiResponses(value = [
        ApiResponse(responseCode = "200", description = "Produto encontrado",
            content = [Content(mediaType = "application/json", schema = Schema(implementation = Product::class))]),
        ApiResponse(responseCode = "404", description = "Produto não encontrado")
    ])
    fun getProductById(@Parameter(description = "ID do produto a ser buscado", required = true, example = "60c72b2f9b1e8a3b3c8d0a1e") @PathVariable id: String): ResponseEntity<Product> {
        val productOptional: Optional<Product> = productService.getProductById(id)
        return productOptional.map { product ->
            ResponseEntity(product, HttpStatus.OK)
        }.orElseGet {
            ResponseEntity(HttpStatus.NOT_FOUND) // This will be handled by GlobalExceptionHandler for documentation if ProductNotFoundException is thrown by service
        }
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Deleta um produto pelo ID", description = "Remove um produto do sistema baseado no seu ID.")
    @ApiResponses(value = [
        ApiResponse(responseCode = "204", description = "Produto deletado com sucesso"),
        ApiResponse(responseCode = "404", description = "Produto não encontrado")
    ])
    fun deleteProduct(@Parameter(description = "ID do produto a ser deletado", required = true, example = "60c72b2f9b1e8a3b3c8d0a1e") @PathVariable id: String): ResponseEntity<Void> {
        productService.deleteProduct(id) // ProductNotFoundException will be thrown by service if not found
        return ResponseEntity(HttpStatus.NO_CONTENT)
    }
}
