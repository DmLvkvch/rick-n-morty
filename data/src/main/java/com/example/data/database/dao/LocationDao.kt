package com.example.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.domain.entities.location.Location

@Dao
interface LocationDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveAllLocations(value: MutableList<Location>)

    @Query("SELECT * FROM location WHERE id=:id")
    suspend fun findLocationById(id: Int): Location?

    @Query("SELECT * FROM location")
    suspend fun getAllLocations(): MutableList<Location>?

    @Query("SELECT * FROM location WHERE id in (:ids)")
    suspend fun getLocationsByIds(ids: MutableList<Int>): MutableList<Location>?

    @Query("SELECT * FROM location")
    suspend fun getLocationsByPage(): MutableList<Location>?

    @Query(
        "SELECT * FROM location WHERE name LIKE '%' || :name || '%' COLLATE NOCASE " +
                "AND type LIKE '%' || :type || '%' COLLATE NOCASE " +
                "AND dimension LIKE '%' || :dimension || '%' COLLATE NOCASE"
    )
    suspend fun getLocationsByFilterParams(
        name: String?,
        type: String?,
        dimension: String?
    ): MutableList<Location>?
}