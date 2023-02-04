package com.example.data

import com.example.data.network.CharacterApiService
import com.example.domain.entities.character.Character
import com.example.domain.entities.character.CharacterList
import com.example.domain.repository.Info
import io.mockk.coEvery
import io.mockk.mockk
import junit.framework.Assert.assertEquals
import kotlinx.coroutines.runBlocking
import org.junit.Test
import retrofit2.Response


class ExampleUnitTest {
    @Test
    fun test1() {
        val api = mockk<CharacterApiService>()
        coEvery { api.getCharacterDetails(1) } returns Response.success(Character(1))
        val status = runBlocking { api.getCharacterDetails(1) }
        assertEquals(Character(1), status.body())
    }

    @Test
    fun test2() {
        val api = mockk<CharacterApiService>()
        coEvery { api.getCharactersByIds("1,2") } returns Response.success(
            mutableListOf(
                Character(1),
                Character(2)
            )
        )
        val status = runBlocking { api.getCharactersByIds("1,2") }
        assertEquals(mutableListOf(Character(1), Character(2)), status.body())
    }

    @Test
    fun test3() {
        val api = mockk<CharacterApiService>()
        coEvery { api.getCharactersByPage(1) } returns Response.success(
            CharacterList(
                Info(), mutableListOf(
                    Character(1), Character
                        (2)
                )
            )
        )
        val status = runBlocking { api.getCharactersByPage(1) }
        assertEquals(
            CharacterList(
                Info(), mutableListOf(
                    Character(1), Character
                        (2)
                )
            ), status.body()
        )
    }
}