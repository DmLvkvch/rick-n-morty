package com.example.domain.interactors

import com.example.domain.entities.character.Character
import com.example.domain.entities.character.CharacterList
import com.example.domain.repository.ICharacterRepository
import javax.inject.Inject

interface ICharacterInteractor {

    suspend fun getAllCharactersByPage(page: Int = -1): CharacterList

    suspend fun getAllCharacters(): MutableList<Character>

    suspend fun getCharactersByIds(ids: String = ""): MutableList<Character>

    suspend fun getCharacterById(id: Int = -1): Character

    suspend fun getCharactersByFilter(
        page: Int,
        name: String?,
        status: String?,
        species: String?,
        type: String?,
        gender: String?
    ): CharacterList

}

class CharacterInteractor @Inject constructor(private val repository: ICharacterRepository) :
    ICharacterInteractor {

    override suspend fun getAllCharactersByPage(page: Int) = repository.getAllCharactersByPage(page)

    override suspend fun getAllCharacters() = repository.getAllCharacters()

    override suspend fun getCharactersByIds(ids: String): MutableList<Character> =
        repository.getCharactersByIds(ids)

    override suspend fun getCharacterById(id: Int): Character = repository.getCharacterById(id)

    override suspend fun getCharactersByFilter(
        page: Int,
        name: String?,
        status: String?,
        species: String?,
        type: String?,
        gender: String?
    ): CharacterList = repository.getCharacterByFilter(page, name, status, species, type, gender)

}