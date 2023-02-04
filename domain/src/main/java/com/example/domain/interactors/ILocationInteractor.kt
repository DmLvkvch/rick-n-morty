package com.example.domain.interactors

import com.example.domain.entities.location.Location
import com.example.domain.entities.location.LocationList
import com.example.domain.repository.ILocationRepository
import javax.inject.Inject


interface ILocationInteractor {

    suspend fun getAllLocationsByPage(page: Int = -1): LocationList

    suspend fun getAllLocations(): MutableList<Location>

    suspend fun getLocationsByIds(ids: String = ""): MutableList<Location>

    suspend fun getLocationById(id: Int = -1): Location

    suspend fun getLocationsByFilter(
        page: Int,
        name: String?,
        type: String?,
        dimension: String?
    ): LocationList

}

class LocationInteractor @Inject constructor(private val repository: ILocationRepository) :
    ILocationInteractor {

    override suspend fun getAllLocationsByPage(page: Int) = repository.getAllLocationsByPage(page)

    override suspend fun getAllLocations() = repository.getAllLocations()

    override suspend fun getLocationsByIds(ids: String): MutableList<Location> =
        repository.getLocationsByIds(ids)

    override suspend fun getLocationById(id: Int): Location = repository.getLocationById(id)

    override suspend fun getLocationsByFilter(
        page: Int,
        name: String?,
        type: String?,
        dimension: String?
    ): LocationList = repository.getLocationsByFilterParams(page, name, type, dimension)
}

