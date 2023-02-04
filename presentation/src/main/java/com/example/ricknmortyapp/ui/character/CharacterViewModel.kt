package com.example.ricknmortyapp.ui.character

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.domain.entities.character.Character
import com.example.domain.interactors.ICharacterInteractor
import com.example.domain.repository.Resource
import com.example.ricknmortyapp.ui.BaseViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

class CharacterViewModel @Inject constructor(private val interactor: ICharacterInteractor) :
    BaseViewModel<Character>() {

    var character: MutableLiveData<Resource<Character>> = MutableLiveData()

    var id: Int = -1

    fun fetch() {
        getItem(id)
    }

    private fun getItem(id: Int) = viewModelScope.launch {
        fetchItem(id)
    }

    private suspend fun fetchItem(id: Int) {
        character.postValue(Resource.Loading())
        try {
            val response = interactor.getCharacterById(id)
            character.postValue(handleResponse(response))
        } catch (t: Throwable) {
            character.postValue(t.message?.let { Resource.Error(it) })
        }
    }
}
