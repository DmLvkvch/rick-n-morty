package com.example.ricknmortyapp.ui.adapter.episode

import com.example.domain.entities.episode.EpisodeList
import com.example.domain.interactors.IEpisodeInteractor
import com.example.ricknmortyapp.ui.adapter.PagingByFilter

class EpisodeFilterPagingAdapter constructor(
    private val interactor: IEpisodeInteractor,
    private val name: String = "", private val episode: String = ""
) :
    PagingByFilter<EpisodeList>() {

    override suspend fun getNextPagingData(): EpisodeList {
        val items =
            interactor.getEpisodesByFilter(page, name, episode)
        info = items.info
        page = getNextPage()
        return items
    }
}