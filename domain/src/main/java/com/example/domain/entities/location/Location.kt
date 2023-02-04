package com.example.domain.entities.location

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Location(
    @PrimaryKey
    val id: Int = -1,
    val name: String? = null,
    val type: String? = null,
    val dimension: String? = null,
    val residents: MutableList<String>? = null,
    val url: String? = null,
    val created: String? = null,
)
