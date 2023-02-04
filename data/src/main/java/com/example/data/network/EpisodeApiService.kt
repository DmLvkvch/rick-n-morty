package com.example.data.network

import com.example.domain.entities.episode.Episode
import com.example.domain.entities.episode.EpisodeList
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface EpisodeApiService {
    @GET("episode/{id}")
    suspend fun getEpisodeDetails(@Path("id") id: Int): Response<Episode>

    @GET("episode")
    suspend fun getEpisodesByPage(@Query("page") page: Int): Response<EpisodeList>

    @GET("episode/{ids}")
    suspend fun getEpisodesByIds(@Path("ids") ids: String): Response<MutableList<Episode>>

    @GET("episode")
    suspend fun getEpisodesByFilterParams(
        @Query("page") page: Int = -1,
        @Query("name") name: String? = null,
        @Query("episode") status: String? = null
    ): Response<EpisodeList>

}