package com.srj.productapp.data.repository

import com.srj.productapp.data.model.Product
import com.srj.productapp.data.source.remote.ApiService
import com.srj.productapp.domain.repository.IProductRepository

class ProductRepository(private val apiService: ApiService) : IProductRepository {
    override suspend fun getProducts(): List<Product> {
        return apiService.getProducts().map { it.toDomainModel() }
    }
}