package com.example.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.data.database.dao.CharacterDao
import com.example.data.database.dao.EpisodeDao
import com.example.data.database.dao.LocationDao
import com.example.domain.entities.character.Character
import com.example.domain.entities.episode.Episode
import com.example.domain.entities.location.Location


@Database(entities = [Character::class, Location::class, Episode::class], version = 1)
@TypeConverters(com.example.data.database.TypeConverters::class)
abstract class DatabaseStorage : RoomDatabase() {

    abstract val characterDao: CharacterDao

    abstract val locationDao: LocationDao

    abstract val episodeDao: EpisodeDao

    companion object {
        const val DATA_BASE_STORAGE = "DATA_BASE_STORAGE"
    }

}