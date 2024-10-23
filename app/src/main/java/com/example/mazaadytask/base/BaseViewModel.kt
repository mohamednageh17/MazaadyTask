package com.example.mazaadytask.base

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.domain.remote.response.category.Categories
import com.example.mazaadytask.ui.main_activity.TAG
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.flow.MutableStateFlow

open class BaseViewModel:ViewModel() {

    protected val exHandler by lazy {
        CoroutineExceptionHandler { coroutineContext, throwable ->
            Log.e(TAG, "ex:${throwable.message}")
        }
    }

    protected fun MutableStateFlow<MainViewState>.setLoading() {
        this.value = MainViewState.Loading
    }

    protected fun MutableStateFlow<MainViewState>.setError(exception: Exception) {
        this.value = MainViewState.Error(exception)
    }
}