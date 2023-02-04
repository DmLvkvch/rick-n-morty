package com.example.domain.entities.location

import androidx.room.Entity
import com.example.domain.repository.Info

@Entity
class LocationList(val info: Info, val results: MutableList<Location>)