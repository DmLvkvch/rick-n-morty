package com.example.ricknmortyapp.ui.adapter

interface PagingByIds<T> : PagingAdapter<T> {

    override fun isLast(): Boolean {
        return true
    }

    override fun getNextPage(): Int {
        return -1
    }

    override fun reset() {

    }
}