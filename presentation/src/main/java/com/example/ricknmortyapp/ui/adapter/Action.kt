package com.example.ricknmortyapp.ui.adapter

fun interface Action<T> {
    fun commit(): T
}