package com.example.ricknmortyapp.ui

import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.domain.repository.Resource
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineName
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlin.coroutines.CoroutineContext

abstract class BaseViewModel<T> : ViewModel(), CoroutineScope {

    private val coroutineExceptionHandler =
        CoroutineExceptionHandler { coroutineContext, throwable ->
            showError(coroutineContext, throwable)
        }

    override val coroutineContext =
        Dispatchers.Main + CoroutineName("${this::class}") + coroutineExceptionHandler

    protected open fun showError(coroutineContext: CoroutineContext, throwable: Throwable) {
        Log.e("AAA", "$throwable where $coroutineContext")
    }

    protected open fun handleResponse(response: T?): Resource<T> {
        response?.let { resultResponse ->
            return Resource.Success(resultResponse)
        }
        return Resource.Error("Error")
    }
}