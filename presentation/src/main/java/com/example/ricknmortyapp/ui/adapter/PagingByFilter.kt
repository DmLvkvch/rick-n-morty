package com.example.ricknmortyapp.ui.adapter

import com.example.domain.repository.Info

abstract class PagingByFilter<T> : PagingAdapter<T> {

    var info: Info = Info()
    var page = 1

    override fun isLast(): Boolean {
        return info.next == null
    }

    override fun getNextPage(): Int {
        return info.next?.split("?page=")?.get(1)?.split("&")?.get(0)?.toInt() ?: -1
    }

    override fun reset() {
        page = 1
    }
}