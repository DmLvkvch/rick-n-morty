package com.example.ricknmortyapp.ui.adapter.character

import com.example.domain.entities.character.CharacterList
import com.example.domain.interactors.ICharacterInteractor
import com.example.ricknmortyapp.ui.adapter.Action
import com.example.ricknmortyapp.ui.adapter.PagingByFilter

class CharacterFilterPagingAdapter constructor(
    private val interactor: ICharacterInteractor,
    private val name: String = "",
    private val status: String = "",
    private val species: String = "",
    private val type: String = "",
    private val gender: String = ""
) :
    PagingByFilter<CharacterList>() {

    override suspend fun getNextPagingData(): CharacterList {
        val charactersByFilter =
            interactor.getCharactersByFilter(page, name, status, species, type, gender)
        info = charactersByFilter.info
        page = getNextPage()
        return charactersByFilter
    }
}