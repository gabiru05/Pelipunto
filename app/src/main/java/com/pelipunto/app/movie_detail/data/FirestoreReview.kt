package com.pelipunto.app.movie_detail.data

import com.google.firebase.firestore.ServerTimestamp
import com.pelipunto.app.movie_detail.domain.models.Review
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

data class FirestoreReview(
    val movieId: Long = 0,
    val userId: String = "",
    val userName: String = "",
    val userPhotoUrl: String? = null,
    val rating: Double = 0.0,
    val comment: String = "",
    @ServerTimestamp val createdAt: Date? = null
) {
    fun toDomainModel(): Review {
        val formatter = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        return Review(
            author = this.userName,
            content = this.comment,
            createdAt = createdAt?.let { formatter.format(it) } ?: "",
            rating = this.rating,
            isOfficial = false,
            userPhotoUrl = this.userPhotoUrl
        )
    }
}