package com.pelipunto.app.ui.detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.pelipunto.app.movie.domain.models.Movie
import com.pelipunto.app.movie_detail.domain.models.MovieDetail
import com.pelipunto.app.movie_detail.domain.models.UserReview
import com.pelipunto.app.movie_detail.domain.repository.MovieDetailRepository
import com.pelipunto.app.utils.K
import com.pelipunto.app.utils.Response
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val repository: MovieDetailRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {
    private val _detailState = MutableStateFlow(DetailState())
    val detailState = _detailState.asStateFlow()

    private val movieId: Int = savedStateHandle.get<Int>(K.MOVIE_ID) ?: -1

    init {
        if (movieId != -1) {
            fetchMovieDetailById()
            fetchSimilarMovies()
        } else {
            _detailState.update { it.copy(isLoading = false, error = "Movie ID not found") }
        }
    }

    private fun fetchMovieDetailById() {
        repository.fetchMovieDetail(movieId).onEach { response ->
            when (response) {
                is Response.Loading -> _detailState.update { it.copy(isLoading = true) }
                is Response.Success -> _detailState.update { it.copy(isLoading = false, movieDetail = response.data) }
                is Response.Error -> _detailState.update { it.copy(isLoading = false, error = response.error?.message) }
            }
        }.launchIn(viewModelScope)
    }

    private fun fetchSimilarMovies() {
        repository.fetchSimilarMovies(movieId).onEach { response ->
            when (response) {
                is Response.Loading -> _detailState.update { it.copy(isMovieLoading = true) }
                is Response.Success -> _detailState.update { it.copy(isMovieLoading = false, movies = response.data ?: emptyList()) }
                is Response.Error -> _detailState.update { it.copy(isMovieLoading = false) }
            }
        }.launchIn(viewModelScope)
    }

    fun postReview(rating: Float, comment: String) {
        viewModelScope.launch {
            val user = FirebaseAuth.getInstance().currentUser ?: return@launch
            val newReview = UserReview(
                movieId = movieId,
                userId = user.uid,
                userName = user.displayName ?: "Anónimo",
                userPhotoUrl = user.photoUrl?.toString(),
                rating = rating.toDouble(),
                comment = comment
            )
            repository.addReview(newReview).onEach { response ->
                when (response) {
                    is Response.Success -> fetchMovieDetailById()
                    is Response.Error -> _detailState.update { it.copy(error = response.error?.message) }
                    is Response.Loading -> { /* No-op */ }
                }
            }.launchIn(viewModelScope)
        }
    }
}

data class DetailState(
    val movieDetail: MovieDetail? = null,
    val movies: List<Movie> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null,
    val isMovieLoading: Boolean = false
)