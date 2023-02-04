package com.example.data.repository

import com.example.data.database.DatabaseStorage
import com.example.data.network.EpisodeApiService
import com.example.domain.entities.episode.Episode
import com.example.domain.entities.episode.EpisodeList
import com.example.domain.repository.IEpisodeRepository
import com.example.domain.repository.Info
import javax.inject.Inject

class EpisodeRepository @Inject constructor(
    private val databaseStorage: DatabaseStorage,
    private val api: EpisodeApiService
) : IEpisodeRepository {

    override suspend fun getAllEpisodesByPage(page: Int): EpisodeList = try {
        val results = api.getEpisodesByPage(page).body()
        results?.let { databaseStorage.episodeDao.saveAllEpisodes(it.results) }
        results ?: throw IllegalStateException()
    } catch (e: Exception) {
        val mutableList = (databaseStorage.episodeDao.getEpisodesByPage()
            ?: throw IllegalStateException())
        EpisodeList(Info(count = mutableList.size, pages = 1), mutableList)
    }

    override suspend fun getAllEpisodes(): MutableList<Episode> = try {
        api.getEpisodesByPage(1).body()?.results ?: throw IllegalStateException()
    } catch (e: Exception) {
        databaseStorage.episodeDao.getAllEpisodes() ?: throw IllegalStateException()
    }

    override suspend fun getEpisodeById(id: Int): Episode = try {
        api.getEpisodeDetails(id).body() ?: throw IllegalStateException()
    } catch (e: Exception) {
        databaseStorage.episodeDao.findEpisodeById(id) ?: throw IllegalStateException()
    }

    override suspend fun getEpisodesByIds(ids: String): MutableList<Episode> = try {
        api.getEpisodesByIds(ids).body() ?: throw IllegalStateException()
    } catch (e: Exception) {
        val values = ids.split(",")
        val list = values.subList(0, values.size - 1).map { it.toInt() }.toMutableList()
        val charactersByIds = databaseStorage.episodeDao.getEpisodesByIds(list)
        charactersByIds ?: throw IllegalStateException()
    }

    override suspend fun getEpisodeByFilter(
        page: Int,
        name: String?,
        episode: String?
    ): EpisodeList = try {
        api.getEpisodesByFilterParams(page, name, episode).body() ?: throw IllegalStateException()
    } catch (e: Exception) {
        val episodesByFilterParams =
            databaseStorage.episodeDao.getEpisodesByFilterParams(name, episode)
        EpisodeList(episodesByFilterParams?.let { Info(it.size, 1) } ?: Info(),
            episodesByFilterParams ?: mutableListOf())
    }
}