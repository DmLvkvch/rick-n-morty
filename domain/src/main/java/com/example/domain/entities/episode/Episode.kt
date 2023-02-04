package com.example.domain.entities.episode

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Episode(
    @PrimaryKey val id: Int = -1,
    val name: String? = null,
    val air_date: String? = null,
    val episode: String? = null,
    val characters: MutableList<String>? = null,
    val url: String? = null,
    val created: String? = null,
)
