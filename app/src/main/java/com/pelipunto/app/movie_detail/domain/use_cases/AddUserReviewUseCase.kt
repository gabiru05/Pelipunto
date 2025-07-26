package com.pelipunto.app.movie_detail.domain.use_cases

import com.pelipunto.app.movie_detail.domain.models.UserReview
import com.pelipunto.app.movie_detail.domain.repository.MovieDetailRepository
import javax.inject.Inject

class AddUserReviewUseCase @Inject constructor(
    private val repository: MovieDetailRepository
) {
    operator fun invoke(review: UserReview) = repository.addReview(review)
}