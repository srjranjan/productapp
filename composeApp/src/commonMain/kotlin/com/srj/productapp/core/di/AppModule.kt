package com.srj.productapp.core.di

import com.srj.productapp.data.repository.ProductRepository
import com.srj.productapp.data.repository.ProfileRepository
import com.srj.productapp.data.source.local.AuthSettings
import com.srj.productapp.data.source.local.CacheService
import com.srj.productapp.data.source.remote.ApiService
import com.srj.productapp.domain.repository.IProductRepository
import com.srj.productapp.domain.repository.IProfileRepository
import com.srj.productapp.domain.usecase.GetProductsUseCase
import com.srj.productapp.domain.usecase.GetProfileUseCase
import com.srj.productapp.presentation.ui.home.HomeViewModel
import com.srj.productapp.presentation.ui.login.LoginViewModel
import com.srj.productapp.presentation.ui.profile.ProfileViewModel
import io.ktor.client.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.json.Json
import org.koin.core.context.startKoin
import org.koin.core.module.Module
import org.koin.dsl.KoinAppDeclaration
import org.koin.dsl.module

expect fun platformModule(): Module

fun initKoin(appDeclaration: KoinAppDeclaration = {}) =
    startKoin {
        appDeclaration()
        modules(commonModule, platformModule())
    }

val commonModule = module {

    single {
        HttpClient {
            install(ContentNegotiation) {
                json(Json {
                    ignoreUnknownKeys = true
                    prettyPrint = true
                    isLenient = true
                })
            }
        }
    }

    single { ApiService(get()) }

    single {
        Json {
            ignoreUnknownKeys = true
            prettyPrint = true
            isLenient = true
        }
    }

    single { CacheService(get()) }

    single { AuthSettings(get()) }

    single<IProductRepository> { ProductRepository(get()) }

    single<IProfileRepository> { ProfileRepository(apiService = get(), cacheService = get(), json = get()) }

    factory { GetProfileUseCase(get()) }

    factory { GetProductsUseCase(get()) }

    factory { HomeViewModel(get()) }

    factory { ProfileViewModel(get()) }

    factory { LoginViewModel(get()) }

}