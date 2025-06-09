package com.srj.productapp.domain.repository

import com.srj.productapp.data.model.Product


interface IProductRepository {
    suspend fun getProducts(): List<Product>

}
