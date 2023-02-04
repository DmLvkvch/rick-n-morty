package com.example.data.repository

import com.example.data.database.DatabaseStorage
import com.example.data.network.CharacterApiService
import com.example.domain.entities.character.Character
import com.example.domain.entities.character.CharacterList
import com.example.domain.repository.ICharacterRepository
import com.example.domain.repository.Info
import javax.inject.Inject

class CharacterRepository @Inject constructor(
    private val databaseStorage: DatabaseStorage,
    private val api: CharacterApiService
) : ICharacterRepository {

    override suspend fun getAllCharactersByPage(page: Int): CharacterList = try {
        val results = api.getCharactersByPage(page).body()
        results?.let { databaseStorage.characterDao.saveAllCharacters(it.results) }
        results ?: throw IllegalStateException()
    } catch (e: Exception) {
        val results = databaseStorage.characterDao.getCharactersByPage()
            ?: throw IllegalStateException()
        CharacterList(Info(count = results.size, pages = 1), results)
    }

    override suspend fun getAllCharacters(): MutableList<Character> = try {
        val results = api.getCharactersByPage(1).body()?.results
        results ?: throw IllegalStateException()
    } catch (e: Exception) {
        databaseStorage.characterDao.getAllCharacters() ?: throw IllegalStateException()
    }

    override suspend fun getCharacterById(id: Int): Character = try {
        api.getCharacterDetails(id).body() ?: throw IllegalStateException()
    } catch (e: Exception) {
        databaseStorage.characterDao.findCharacterById(id) ?: throw IllegalStateException()
    }

    override suspend fun getCharactersByIds(ids: String): MutableList<Character> = try {
        api.getCharactersByIds(ids).body() ?: throw IllegalStateException()
    } catch (e: Exception) {
        val values = ids.split(",")
        val list = values.subList(0, values.size - 1).map { it.toInt() }.toMutableList()
        val charactersByIds = databaseStorage.characterDao.getCharactersByIds(list)
        charactersByIds ?: throw IllegalStateException()
    }

    override suspend fun getCharacterByFilter(
        page: Int,
        name: String?,
        status: String?,
        species: String?,
        type: String?,
        gender: String?
    ): CharacterList = try {
        api.getCharactersByFilterParams(page, name, status, species, type, gender).body()
            ?: throw IllegalStateException()
    } catch (e: Exception) {
        val charactersByFilterParams = databaseStorage.characterDao.getCharactersByFilterParams(
            name,
            status,
            species,
            type,
            gender
        )
        CharacterList(
            charactersByFilterParams?.let { Info(it.size, 1) } ?: Info(),
            charactersByFilterParams ?: mutableListOf()
        )
    }

}