package com.example.mazaadytask.ui.main_activity

import androidx.lifecycle.viewModelScope
import com.example.domain.remote.response.category.Categories
import com.example.domain.remote.response.property.Property
import com.example.domain.usecase.GetCategoriesUseCase
import com.example.domain.usecase.GetOptionsChildUseCase
import com.example.domain.usecase.GetPropertiesUseCase
import com.example.mazaadytask.base.BaseViewModel
import com.example.mazaadytask.base.MainViewState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val getCategoriesUseCase: GetCategoriesUseCase,
    private val getPropertiesUseCase: GetPropertiesUseCase,
    private val getOptionsChildUseCase: GetOptionsChildUseCase,
) : BaseViewModel() {


    private val _state =MutableStateFlow<MainViewState>(MainViewState.Idle)
    val state: StateFlow<MainViewState> get() = _state

    var selectedProperty: Property? = null
    init {
        getCategories()
    }


    private fun getCategories() = viewModelScope.launch(Dispatchers.IO + exHandler) {
        _state.setLoading()
        try {
            getCategoriesUseCase.invoke(null).collect {categories->
                withContext(Dispatchers.Main) {
                    _state.value = MainViewState.Success(categories = categories)
                }
            }

        } catch (e: Exception) {
            withContext(Dispatchers.Main) {
                _state.setError(e)
            }
        }
    }

    fun getProperties(catId: Int) = viewModelScope.launch(Dispatchers.IO + exHandler) {
        _state.setLoading()
        try {
            getPropertiesUseCase.invoke(catId).collect {properties->
                withContext(Dispatchers.Main) {
                    _state.value = MainViewState.Success(properties = properties)
                }
            }

        } catch (e: Exception) {
            withContext(Dispatchers.Main) {
                _state.setError(e)
            }
        }
    }

    fun getOptionsChild(id: Int) = viewModelScope.launch(Dispatchers.IO + exHandler) {
        _state.setLoading()
        try {
            getOptionsChildUseCase.invoke(id).collect {optionsChild->
                withContext(Dispatchers.Main) {
                    _state.value = MainViewState.Success(optionsChild = optionsChild)
                }
            }

        } catch (e: Exception) {
            withContext(Dispatchers.Main) {
                _state.setError(e)
            }
        }
    }
}