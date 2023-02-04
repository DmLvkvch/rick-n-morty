package com.example.ricknmortyapp.ui.location

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.domain.entities.location.Location
import com.example.domain.interactors.ILocationInteractor
import com.example.domain.repository.Resource
import com.example.ricknmortyapp.ui.BaseViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

class LocationViewModel @Inject constructor(private val interactor: ILocationInteractor) :
    BaseViewModel<Location>() {

    val location = MutableLiveData<Resource<Location>>()

    var id: Int = -1

    fun fetch() {
        getItem(id)
    }

    private fun getItem(id: Int) = viewModelScope.launch {
        fetchItem(id)
    }

    private suspend fun fetchItem(id: Int) {
        location.postValue(Resource.Loading())
        try {
            val response = interactor.getLocationById(id)
            location.postValue(handleResponse(response))
        } catch (t: Throwable) {
            location.postValue(t.message?.let { Resource.Error(it) })
        }
    }
}

