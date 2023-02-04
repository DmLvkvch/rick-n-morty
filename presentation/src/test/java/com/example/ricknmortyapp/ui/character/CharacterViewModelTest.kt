package com.example.ricknmortyapp.ui.character

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.data.network.CharacterApiService
import com.example.domain.entities.character.Character
import com.example.domain.entities.character.CharacterList
import com.example.domain.interactors.ICharacterInteractor
import com.example.domain.repository.Info
import io.mockk.coEvery
import io.mockk.mockk
import io.mockk.mockkClass
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import retrofit2.Response

@ExperimentalCoroutinesApi
class CharacterViewModelTest {

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

    private val dispatcher = TestCoroutineDispatcher()

    @get:Rule
    val rule = InstantTaskExecutorRule()

    private lateinit var myUseCase: ICharacterInteractor
    private lateinit var myViewModel: CharacterViewModel

    @Before
    fun setup() {
        Dispatchers.setMain(dispatcher)

        myUseCase = mockkClass(ICharacterInteractor::class)
        myViewModel = CharacterViewModel(myUseCase)
    }

    @After
    fun cleanup() {
        Dispatchers.resetMain()
    }

    @Test
    fun `button click should put screen into loading state`() {
        dispatcher.runBlockingTest {
            coEvery { myUseCase.getCharacterById() } returns Character(1)
            myViewModel.fetch()

            val state = myViewModel.character.value
            assertEquals(Character(1), state?.data)
        }
    }
}