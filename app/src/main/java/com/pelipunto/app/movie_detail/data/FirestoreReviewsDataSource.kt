package com.pelipunto.app.movie_detail.data

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.pelipunto.app.movie_detail.domain.models.UserReview
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class FirestoreReviewsDataSource @Inject constructor() {
    private val db = FirebaseFirestore.getInstance()

    suspend fun getReviewsForMovie(movieId: Int): List<FirestoreReview> {
        return try {
            db.collection("user_reviews")
                .whereEqualTo("movieId", movieId.toLong())
                .orderBy("createdAt", Query.Direction.DESCENDING)
                .get()
                .await()
                .toObjects(FirestoreReview::class.java)
        } catch (e: Exception) {
            e.printStackTrace()
            emptyList()
        }
    }

    suspend fun addReview(review: UserReview): Result<Unit> {
        return try {
            db.collection("user_reviews").add(review).await()
            Result.success(Unit)
        } catch (e: Exception) {
            e.printStackTrace()
            Result.failure(e)
        }
    }
}