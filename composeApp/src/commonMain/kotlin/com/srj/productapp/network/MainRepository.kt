package com.srj.productapp.network

import com.srj.productapp.data.model.Product
import com.srj.productapp.data.model.UserProfile

interface MainRepository {
    suspend fun getProducts(): List<Product>
    suspend fun getProfile(): UserProfile
}
