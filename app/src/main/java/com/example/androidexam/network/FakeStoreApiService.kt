package com.example.androidexam.network

import com.example.androidexam.Product
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import retrofit2.Retrofit
import retrofit2.http.GET
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType

private const val BASE_URL =
    "https://fakestoreapi.com/"

private val retrofit = Retrofit.Builder()
    .addConverterFactory(Json.asConverterFactory("application/json".toMediaType()))
    .baseUrl(BASE_URL)
    .build()

interface FakeStoreApiService {
    @GET("products")
    suspend fun getImage() : List<Product>
}

object FakeStoreApi {
    val retrofitService: FakeStoreApiService by lazy { retrofit.create(FakeStoreApiService::class.java) }
}