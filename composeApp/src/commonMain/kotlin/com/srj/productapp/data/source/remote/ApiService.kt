package com.srj.productapp.data.source.remote

import com.srj.productapp.data.Constants
import com.srj.productapp.data.Utils
import com.srj.productapp.data.model.Product
import com.srj.productapp.data.model.UserProfile
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*

class ApiService(private val client: HttpClient) {
    suspend fun getProducts(): List<Product> {
        return client.get(Utils.getEndpoint(endpoint = Constants.PRODUCT_LIST_ENDPOINT)).body()
    }

    suspend fun getUserProfile(): UserProfile {
        return client.get(Utils.getEndpoint(endpoint = Constants.PROFILE_ENDPOINT)).body()
    }

}