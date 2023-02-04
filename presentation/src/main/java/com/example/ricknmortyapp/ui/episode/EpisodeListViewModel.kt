package com.example.ricknmortyapp.ui.episode

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.domain.entities.episode.Episode
import com.example.domain.entities.episode.EpisodeList
import com.example.domain.interactors.IEpisodeInteractor
import com.example.domain.repository.Resource
import com.example.ricknmortyapp.ui.BaseViewModel
import com.example.ricknmortyapp.ui.adapter.PagingAdapter
import com.example.ricknmortyapp.ui.adapter.episode.EpisodeFilterPagingAdapter
import com.example.ricknmortyapp.ui.adapter.episode.EpisodeIdsPagingAdapter
import kotlinx.coroutines.launch
import javax.inject.Inject

class EpisodeListViewModel @Inject constructor(private val interactor: IEpisodeInteractor) :
    BaseViewModel<MutableList<Episode>>() {

    var items: MutableLiveData<Resource<MutableList<Episode>>> = MutableLiveData()

    var episodes: MutableList<Episode> = mutableListOf()

    var isLoading = false

    var adapter: PagingAdapter<EpisodeList> = EpisodeFilterPagingAdapter(interactor)

    fun fetch() {
        adapter.reset()
        reset()
        getNext()
    }

    fun getNext() = viewModelScope.launch {
        isLoading = true
        fetchData()
        isLoading = false
    }

    fun getData(ids: String) = viewModelScope.launch {
        adapter = EpisodeIdsPagingAdapter(interactor, ids)
        fetchData(ids)
    }

    private suspend fun fetchData() {
        items.postValue(Resource.Loading())
        try {
            val response = adapter.getNextPagingData()
            response.let { items.value?.data?.addAll(it.results) }
            episodes.addAll(response.results)
            items.postValue(Resource.Success(episodes))
        } catch (t: Throwable) {
        }
    }

    private suspend fun fetchData(ids: String) {
        items.postValue(Resource.Loading())
        try {
            val response = interactor.getEpisodesByIds(ids)
            items.postValue(handleResponse(response))
        } catch (t: Throwable) {
            items.postValue(t.message?.let { Resource.Error(it) })
        }
    }

    fun filter(name: String = "", episode: String = "") {
        adapter = EpisodeFilterPagingAdapter(
            interactor, name, episode
        )
        items.postValue(Resource.Loading())
        episodes.clear()
        getNext()
    }

    fun reset() {
        items.postValue(Resource.Loading())
        episodes.clear()
    }

    fun isLastPage(): Boolean {
        return adapter.isLast()
    }
}