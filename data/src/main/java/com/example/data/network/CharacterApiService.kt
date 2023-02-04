package com.example.data.network

import com.example.domain.entities.character.Character
import com.example.domain.entities.character.CharacterList
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface CharacterApiService {

    @GET("character")
    suspend fun getCharactersByPage(@Query("page") page: Int): Response<CharacterList>

    @GET("character/{id}")
    suspend fun getCharacterDetails(@Path("id") id: Int): Response<Character>

    @GET("character/{ids}")
    suspend fun getCharactersByIds(@Path("ids") ids: String): Response<MutableList<Character>>

    @GET("character")
    suspend fun getCharactersByFilterParams(
        @Query("page") page: Int = -1,
        @Query("name") name: String? = null,
        @Query("status") status: String? = null,
        @Query("species") species: String? = null,
        @Query("type") type: String? = null,
        @Query("gender") gender: String? = null
    ): Response<CharacterList>

}