package com.example.data.repository

import com.example.data.database.DatabaseStorage
import com.example.data.network.LocationApiService
import com.example.domain.entities.location.Location
import com.example.domain.entities.location.LocationList
import com.example.domain.repository.ILocationRepository
import com.example.domain.repository.Info
import javax.inject.Inject

class LocationRepository @Inject constructor(
    private val databaseStorage: DatabaseStorage,
    private val api: LocationApiService
) : ILocationRepository {

    override suspend fun getAllLocationsByPage(page: Int): LocationList = try {
        val results = api.getLocationsByPage(page).body()
        results?.let { databaseStorage.locationDao.saveAllLocations(it.results) }
        results ?: throw IllegalStateException()
    } catch (e: Exception) {
        val results =
            (databaseStorage.locationDao.getLocationsByPage()
                ?: throw IllegalStateException())
        LocationList(Info(count = results.size, pages = 1), results)

    }

    override suspend fun getAllLocations(): MutableList<Location> = try {
        api.getLocationsByPage(1).body()?.results ?: throw IllegalStateException()
    } catch (e: Exception) {
        databaseStorage.locationDao.getAllLocations() ?: throw IllegalStateException()
    }

    override suspend fun getLocationById(id: Int): Location = try {
        api.getLocationDetails(id).body() ?: throw IllegalStateException()
    } catch (e: Exception) {
        databaseStorage.locationDao.findLocationById(id) ?: throw IllegalStateException()
    }

    override suspend fun getLocationsByIds(ids: String): MutableList<Location> = try {
        throw IllegalStateException()
    } catch (e: Exception) {
        throw IllegalStateException()
    }

    override suspend fun getLocationsByFilterParams(
        page: Int,
        name: String?,
        type: String?,
        dimension: String?
    ): LocationList = try {
        api.getLocationsByFilterParams(page, name, type, dimension).body()
            ?: throw IllegalStateException()
    } catch (e: Exception) {
        val locationsByFilterParams =
            databaseStorage.locationDao.getLocationsByFilterParams(name, type, dimension)
        LocationList(
            locationsByFilterParams?.let { Info(it.size, 1) } ?: Info(),
            locationsByFilterParams ?: mutableListOf()
        )
    }

}