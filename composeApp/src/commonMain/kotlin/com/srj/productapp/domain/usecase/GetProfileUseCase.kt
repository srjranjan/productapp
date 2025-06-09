package com.srj.productapp.domain.usecase

import com.srj.productapp.domain.repository.IProfileRepository

class GetProfileUseCase(private val repository: IProfileRepository) {
    suspend operator fun invoke() = repository.getProfile()
}