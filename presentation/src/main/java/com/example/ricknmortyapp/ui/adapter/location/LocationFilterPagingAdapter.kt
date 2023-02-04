package com.example.ricknmortyapp.ui.adapter.location

import com.example.domain.entities.location.LocationList
import com.example.domain.interactors.ILocationInteractor
import com.example.ricknmortyapp.ui.adapter.PagingByFilter

class LocationFilterPagingAdapter constructor(
    private val interactor: ILocationInteractor,
    private val name: String = "",
    private val type: String = "",
    private val dimension: String = ""
) :
    PagingByFilter<LocationList>() {

    override suspend fun getNextPagingData(): LocationList {
        val charactersByFilter =
            interactor.getLocationsByFilter(page, name, type, dimension)
        info = charactersByFilter.info
        page = getNextPage()
        return charactersByFilter
    }
}