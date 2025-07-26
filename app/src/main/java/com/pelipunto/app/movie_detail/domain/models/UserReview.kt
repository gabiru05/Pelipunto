package com.pelipunto.app.movie_detail.domain.models

import com.google.firebase.firestore.ServerTimestamp
import java.util.Date

data class UserReview(
    val movieId: Int,
    val userId: String,
    val userName: String,
    val userPhotoUrl: String?,
    val rating: Double,
    val comment: String,
    @ServerTimestamp val createdAt: Date? = null
)