package com.example.consecutivepracts.network

import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface Api {
    @GET("anime/{id}/full")
    suspend fun getAnimeDetails(
        @Path("id") id: Int
    ): AnimeResponse

    @GET("anime")
    suspend fun searchAnime(
        @Query("q") query: String,
        @Query("limit") limit: Int = 5
    ): AnimeListResponse
}