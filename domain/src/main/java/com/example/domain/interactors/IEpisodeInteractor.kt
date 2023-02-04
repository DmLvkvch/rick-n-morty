package com.example.domain.interactors

import com.example.domain.entities.episode.Episode
import com.example.domain.entities.episode.EpisodeList
import com.example.domain.repository.IEpisodeRepository
import javax.inject.Inject

interface IEpisodeInteractor {

    suspend fun getAllEpisodesByPage(page: Int = -1): EpisodeList

    suspend fun getAllEpisodes(): MutableList<Episode>

    suspend fun getEpisodesByIds(ids: String = ""): MutableList<Episode>

    suspend fun getEpisodeById(id: Int = -1): Episode

    suspend fun getEpisodesByFilter(page: Int, name: String?, episode: String?): EpisodeList

}

class EpisodeInteractor @Inject constructor(private val repository: IEpisodeRepository) :
    IEpisodeInteractor {

    override suspend fun getAllEpisodesByPage(page: Int) = repository.getAllEpisodesByPage(page)

    override suspend fun getAllEpisodes() = repository.getAllEpisodes()

    override suspend fun getEpisodesByIds(ids: String): MutableList<Episode> =
        repository.getEpisodesByIds(ids)

    override suspend fun getEpisodeById(id: Int): Episode = repository.getEpisodeById(id)

    override suspend fun getEpisodesByFilter(page: Int, name: String?, episode: String?) =
        repository.getEpisodeByFilter(page, name, episode)


}