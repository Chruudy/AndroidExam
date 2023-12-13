package com.example.androidexam


import kotlinx.serialization.Serializable
import com.example.androidexam.Rating


@Serializable
data class Product(
    val id: Int,
    val title: String,
    val price: Double,
    val description: String,
    val category: String,
    val image: String,
    val rating: Rating
)
