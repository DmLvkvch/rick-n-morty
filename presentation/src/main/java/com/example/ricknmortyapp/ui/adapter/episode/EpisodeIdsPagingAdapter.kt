package com.example.ricknmortyapp.ui.adapter.episode

import com.example.domain.entities.episode.EpisodeList
import com.example.domain.interactors.IEpisodeInteractor
import com.example.domain.repository.Info
import com.example.ricknmortyapp.ui.adapter.Action
import com.example.ricknmortyapp.ui.adapter.PagingByIds

class EpisodeIdsPagingAdapter constructor(
    private val interactor: IEpisodeInteractor,
    private val ids: String
) :
    PagingByIds<EpisodeList> {

    override suspend fun getNextPagingData(): EpisodeList {

        val items = interactor.getEpisodesByIds(ids)
        return EpisodeList(Info(items.size, 1), items)
    }
}