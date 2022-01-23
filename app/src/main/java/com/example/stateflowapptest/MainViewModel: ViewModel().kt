package com.example.stateflowapptest

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlin.random.Random
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {

    private val _uiStateFlow = MutableStateFlow<UiState>(UiState.Initial)
    val uiStateFlow: StateFlow<UiState> get() = _uiStateFlow

    private var error: Boolean = false

    fun getSomething() = viewModelScope.launch {
        _uiStateFlow.value = UiState.Loading

        delay(2000L)

        error = Random.nextBoolean()

        _uiStateFlow.value = if (error) UiState.Error else UiState.Success
    }

    sealed class UiState {
        object Success : UiState()
        object Loading : UiState()
        object Error : UiState()
        object Initial : UiState()
    }
}