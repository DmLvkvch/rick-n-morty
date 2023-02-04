package com.example.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.domain.entities.episode.Episode

@Dao
interface EpisodeDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveAllEpisodes(value: MutableList<Episode>)

    @Query("SELECT * FROM episode WHERE id=:id")
    suspend fun findEpisodeById(id: Int): Episode?

    @Query("SELECT * FROM episode")
    suspend fun getAllEpisodes(): MutableList<Episode>?

    @Query("SELECT * FROM episode WHERE id in (:ids)")
    suspend fun getEpisodesByIds(ids: MutableList<Int>): MutableList<Episode>?

    @Query("SELECT * FROM episode")
    suspend fun getEpisodesByPage(): MutableList<Episode>?

    @Query("SELECT * FROM episode WHERE name LIKE '%' || :name || '%' COLLATE NOCASE AND episode LIKE '%' || :episode || '%' COLLATE NOCASE")
    suspend fun getEpisodesByFilterParams(
        name: String?,
        episode: String?
    ): MutableList<Episode>?
}

