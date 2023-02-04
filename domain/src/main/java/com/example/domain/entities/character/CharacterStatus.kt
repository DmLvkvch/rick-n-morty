package com.example.domain.entities.character

import com.google.gson.annotations.SerializedName

enum class CharacterStatus(val value: String) {
    @SerializedName("Alive")
    ALIVE("Alive"),

    @SerializedName("Dead")
    DEAD("Dead"),

    @SerializedName("unknown")
    UNKNOWN("unknown")
}