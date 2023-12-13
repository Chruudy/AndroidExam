package com.example.androidexam

import kotlinx.serialization.Serializable

@Serializable
data class Rating(
    val rate: Double,
    val count: Int,
)
