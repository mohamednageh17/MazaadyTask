package com.example.mazaadytask.base

import com.example.domain.remote.response.category.Categories
import com.example.domain.remote.response.property.Property

sealed class MainViewState {
    data object Idle : MainViewState()
    data object Loading : MainViewState()
    data class Success(
        val categories: List<Categories>? = null,
        val properties: List<Property>? = null,
        val optionsChild: List<Property>? = null
    ) : MainViewState()

    data class Error(val exception: Exception) : MainViewState()
}
