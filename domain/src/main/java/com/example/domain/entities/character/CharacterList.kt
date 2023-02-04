package com.example.domain.entities.character

import com.example.domain.repository.Info

data class CharacterList(
    val info: Info,
    val results: MutableList<Character>
)