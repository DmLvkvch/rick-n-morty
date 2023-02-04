package com.example.ricknmortyapp.ui.character

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.domain.entities.character.Character
import com.example.domain.entities.character.CharacterList
import com.example.domain.interactors.ICharacterInteractor
import com.example.domain.repository.Resource
import com.example.ricknmortyapp.ui.BaseViewModel
import com.example.ricknmortyapp.ui.adapter.PagingAdapter
import com.example.ricknmortyapp.ui.adapter.character.CharacterFilterPagingAdapter
import com.example.ricknmortyapp.ui.adapter.character.CharacterIdsPagingAdapter
import kotlinx.coroutines.launch
import javax.inject.Inject


class CharacterListViewModel @Inject constructor(private val interactor: ICharacterInteractor) :
    BaseViewModel<MutableList<Character>>() {

    var items: MutableLiveData<Resource<MutableList<Character>>> = MutableLiveData()

    var characters: MutableList<Character> = mutableListOf()

    var isLoading = false

    var adapter: PagingAdapter<CharacterList> = CharacterFilterPagingAdapter(interactor)

    fun fetch() {
        adapter.reset()
        reset()
        getCharacters()
    }

    fun getCharacters() = viewModelScope.launch {
        isLoading = true
        fetchCharacters()
        isLoading = false
    }

    fun getCharacters(ids: String) = viewModelScope.launch {
        adapter = CharacterIdsPagingAdapter(interactor, ids)
        fetchCharacters(ids)
    }

    private suspend fun fetchCharacters() {
        items.postValue(Resource.Loading())
        try {
            val response = adapter.getNextPagingData()
            response.let { items.value?.data?.addAll(it.results) }
            characters.addAll(response.results)
            items.postValue(Resource.Success(characters))
        } catch (t: Throwable) {
        }
    }

    private suspend fun fetchCharacters(ids: String) {
        items.postValue(Resource.Loading())
        try {
            val response = interactor.getCharactersByIds(ids)
            items.postValue(handleResponse(response))
        } catch (t: Throwable) {
            items.postValue(t.message?.let { Resource.Error(it) })
        }
    }

    fun isLastPage(): Boolean {
        return adapter.isLast()
    }

    private fun reset() {
        items.postValue(Resource.Loading())
        characters.clear()
    }

    fun filter(
        name: String = "",
        status: String = "",
        species: String = "",
        type: String = "",
        gender: String = ""
    ) {
        adapter = CharacterFilterPagingAdapter(
            interactor, name, status,
            species, type, gender
        )
        reset()
        getCharacters()
    }
}