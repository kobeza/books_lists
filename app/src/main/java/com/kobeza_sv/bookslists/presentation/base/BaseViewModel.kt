package com.kobeza_sv.bookslists.presentation.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavDirections
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch

open class BaseViewModel : ViewModel() {
    private val _navDirections =
        MutableSharedFlow<NavDirections>()
    val navDirections: SharedFlow<NavDirections>
        get() = _navDirections
    fun navigateTo(direction: NavDirections) {
        viewModelScope.launch {
            _navDirections.emit(direction)
        }
    }
}