package com.example.ricknmortyapp.ui.adapter.character

import com.example.domain.entities.character.CharacterList
import com.example.domain.interactors.ICharacterInteractor
import com.example.domain.repository.Info
import com.example.ricknmortyapp.ui.adapter.Action
import com.example.ricknmortyapp.ui.adapter.PagingAdapter
import com.example.ricknmortyapp.ui.adapter.PagingByIds

class CharacterIdsPagingAdapter constructor(
    private val interactor: ICharacterInteractor,
    private val ids: String
) :
    PagingByIds<CharacterList> {

    override suspend fun getNextPagingData(): CharacterList {
        val items = interactor.getCharactersByIds(ids)
        return CharacterList(Info(items.size, 1), items)
    }
}