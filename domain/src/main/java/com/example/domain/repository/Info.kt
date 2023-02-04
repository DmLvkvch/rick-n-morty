package com.example.domain.repository

data class Info(
    val count: Int = -1,
    val pages: Int = -1,
    val next: String? = null,
    val prev: String? = null
)
