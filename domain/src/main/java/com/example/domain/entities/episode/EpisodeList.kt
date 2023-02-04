package com.example.domain.entities.episode

import androidx.room.Entity
import com.example.domain.repository.Info

@Entity
class EpisodeList(val info: Info, val results: MutableList<Episode>)