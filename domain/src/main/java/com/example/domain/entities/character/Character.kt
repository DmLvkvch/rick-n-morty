package com.example.domain.entities.character

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Character(
    @PrimaryKey val id: Int = -1,
    val name: String? = null,
    val status: CharacterStatus = CharacterStatus.UNKNOWN,
    val species: String? = null,
    val type: String? = null,
    val gender: CharacterGender = CharacterGender.UNKNOWN,
    @Embedded(prefix = "origin")
    val origin: CharacterOrigin? = null,
    @Embedded(prefix = "location")
    val location: CharacterLocation? = null,
    val image: String? = null,
    val episode: MutableList<String>? = null,
    val url: String? = null,
    val created: String? = null
)
