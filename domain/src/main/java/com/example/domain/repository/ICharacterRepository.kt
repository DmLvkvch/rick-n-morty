package com.example.domain.repository

import com.example.domain.entities.character.Character
import com.example.domain.entities.character.CharacterList

interface ICharacterRepository {

    suspend fun getAllCharactersByPage(page: Int = -1): CharacterList

    suspend fun getAllCharacters(): MutableList<Character>

    suspend fun getCharacterById(id: Int = 1): Character

    suspend fun getCharactersByIds(ids: String): MutableList<Character>

    suspend fun getCharacterByFilter(
        page: Int,
        name: String?,
        status: String?,
        species: String?,
        type: String?,
        gender: String?
    ): CharacterList

}