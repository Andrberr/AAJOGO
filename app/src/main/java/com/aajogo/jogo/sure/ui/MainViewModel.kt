package com.aajogo.jogo.sure.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aajogo.jogo.sure.domain.Repository
import com.aajogo.jogo.sure.ui.game.spin.GameFragment.Companion.START_BALANCE
import com.aajogo.jogo.sure.ui.game.spin.GameFragment.Companion.BET_SIZE
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val repository: Repository
) : ViewModel() {
    private var _viewEffect = MutableLiveData<ViewEffect>()
    val viewEffect: LiveData<ViewEffect> = _viewEffect

    var isSoundOn = true
    var isMusicOn = true
    var isVibrationOn = true
    var balance = START_BALANCE
    var betSize = BET_SIZE
    var win = 0

    private val networkHandler = CoroutineExceptionHandler { _, _ ->
        viewModelScope.launch {
            _viewEffect.value = ViewEffect.NetworkError
        }
    }

    fun handleEvents(event: ViewEvent) {
        when (event) {
            is ViewEvent.GetResponseFromUrl -> getResponse()
        }
    }

    private fun getResponse() {
        viewModelScope.launch(networkHandler) {
            _viewEffect.value = ViewEffect.UrlResponse(repository.makeRequest())
        }
    }

    sealed class ViewEvent {
        object GetResponseFromUrl : ViewEvent()
    }

    sealed class ViewEffect {
        data class UrlResponse(val response: String) : ViewEffect()
        object NetworkError : ViewEffect()
    }
}