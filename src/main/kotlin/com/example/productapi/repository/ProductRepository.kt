package com.example.productapi.repository

import com.example.productapi.model.Product
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Repository

@Repository // Optional, but good practice
interface ProductRepository : MongoRepository<Product, String>
