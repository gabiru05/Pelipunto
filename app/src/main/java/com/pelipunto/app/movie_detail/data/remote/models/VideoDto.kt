package com.pelipunto.app.movie_detail.data.remote.models

import kotlinx.serialization.Serializable

@Serializable
data class VideoResponseDto(
    val results: List<VideoDto>? = null
)

@Serializable
data class VideoDto(
    val key: String? = null,
    val site: String? = null,
    val type: String? = null,
    val official: Boolean? = false
)