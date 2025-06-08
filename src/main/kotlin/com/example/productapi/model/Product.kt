package com.example.productapi.model

import io.swagger.v3.oas.annotations.media.Schema
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document(collection = "products")
@Schema(description = "Representa um produto no sistema.")
data class Product(
    @Id
    @Schema(description = "Identificador único do produto (gerado automaticamente pelo MongoDB).", accessMode = Schema.AccessMode.READ_ONLY)
    val id: String? = null,

    @Schema(description = "Nome do produto.", example = "Laptop XYZ")
    val name: String,

    @Schema(description = "Descrição detalhada do produto.", example = "Laptop de alta performance com 16GB RAM e SSD 512GB.")
    val description: String,

    @Schema(description = "Preço do produto.", example = "1250.99")
    val price: Double
)
