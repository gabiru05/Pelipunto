package com.pelipunto.app.movie_detail.domain.models

data class Review(
    val author: String,
    val content: String,
    val createdAt: String,
    val rating: Double,
    val isOfficial: Boolean,
    val userPhotoUrl: String?
)