package com.example.data.network

import com.example.domain.entities.location.Location
import com.example.domain.entities.location.LocationList
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface LocationApiService {
    @GET("location/{id}")
    suspend fun getLocationDetails(@Path("id") id: Int): Response<Location>

    @GET("location")
    suspend fun getLocationsByPage(@Query("page") page: Int): Response<LocationList>

    @GET("location/{ids}")
    suspend fun getLocationsByIds(@Path("ids") ids: String): Response<MutableList<Location>>

    @GET("location")
    suspend fun getLocationsByFilterParams(
        @Query("page") page: Int = -1,
        @Query("name") name: String? = null,
        @Query("type") type: String? = null,
        @Query("dimension") dimension: String? = null
    ): Response<LocationList>

}