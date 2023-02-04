package com.example.ricknmortyapp.ui.adapter

interface PagingAdapter<T> {

    suspend fun getNextPagingData(): T

    fun isLast(): Boolean

    fun getNextPage(): Int

    fun reset()
}