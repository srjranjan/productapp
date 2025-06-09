package com.srj.productapp.domain.usecase

import com.srj.productapp.domain.repository.IProductRepository


class GetProductsUseCase(private val repository: IProductRepository) {
    suspend operator fun invoke() = repository.getProducts()
}

