package com.example.domain.repository

import com.example.domain.entities.location.Location
import com.example.domain.entities.location.LocationList

interface ILocationRepository {

    suspend fun getAllLocationsByPage(page: Int = -1): LocationList

    suspend fun getAllLocations(): MutableList<Location>

    suspend fun getLocationById(id: Int = 1): Location

    suspend fun getLocationsByIds(ids: String): MutableList<Location>

    suspend fun getLocationsByFilterParams(
        page: Int,
        name: String?,
        type: String?,
        dimension: String?
    ): LocationList
}


