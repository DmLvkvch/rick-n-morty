package com.example.domain.repository

import com.example.domain.entities.episode.Episode
import com.example.domain.entities.episode.EpisodeList

interface IEpisodeRepository {

    suspend fun getAllEpisodesByPage(page: Int = -1): EpisodeList

    suspend fun getAllEpisodes(): MutableList<Episode>

    suspend fun getEpisodeById(id: Int = 1): Episode

    suspend fun getEpisodesByIds(ids: String): MutableList<Episode>

    suspend fun getEpisodeByFilter(
        page: Int,
        name: String?,
        episode: String?
    ): EpisodeList
}