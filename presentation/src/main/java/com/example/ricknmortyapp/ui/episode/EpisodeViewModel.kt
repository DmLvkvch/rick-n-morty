package com.example.ricknmortyapp.ui.episode

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.domain.entities.episode.Episode
import com.example.domain.interactors.IEpisodeInteractor
import com.example.domain.repository.Resource
import com.example.ricknmortyapp.ui.BaseViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

class EpisodeViewModel @Inject constructor(private val interactor: IEpisodeInteractor) :
    BaseViewModel<Episode>() {

    val episode = MutableLiveData<Resource<Episode>>()

    var id: Int = -1

    fun fetch() {
        getItem(id)
    }

    private fun getItem(id: Int) = viewModelScope.launch {
        fetchItem(id)
    }

    private suspend fun fetchItem(id: Int) {
        episode.postValue(Resource.Loading())
        try {
            val response = interactor.getEpisodeById(id)
            episode.postValue(handleResponse(response))
        } catch (t: Throwable) {
            episode.postValue(t.message?.let { Resource.Error(it) })
        }
    }
}