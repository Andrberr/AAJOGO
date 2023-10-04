package com.aajogo.jogo.sure.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aajogo.jogo.sure.domain.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val repository: Repository
) : ViewModel() {
    private var _viewEffect = MutableLiveData<ViewEffect>()
    val viewEffect: LiveData<ViewEffect> = _viewEffect

    fun handleEvents(event: ViewEvent) {
        when (event) {
            is ViewEvent.GetResponseFromUrl -> getResponse()
        }
    }

    private fun getResponse() {
        viewModelScope.launch {
            _viewEffect.value = ViewEffect.UrlResponse(repository.makeRequest())
        }
    }

    sealed class ViewEvent {
        object GetResponseFromUrl : ViewEvent()
    }

    sealed class ViewEffect {
        data class UrlResponse(val response: String) : ViewEffect()
    }
}