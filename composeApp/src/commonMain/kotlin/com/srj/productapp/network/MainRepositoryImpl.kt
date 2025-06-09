package com.srj.productapp.network

import com.srj.productapp.data.model.Product
import com.srj.productapp.data.model.UserProfile
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*

class MainRepositoryImpl(private val client: HttpClient) : MainRepository {
    override suspend fun getProducts(): List<Product> {
        return client.get("${ApiConfig.BASE_URL}${ApiConfig.PRODUCTS_ENDPOINT}").body()
    }

    override suspend fun getProfile(): UserProfile {
        return client.get("https://example.com/profile").body()
    }
}
