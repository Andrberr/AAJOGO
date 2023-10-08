package com.aajogo.jogo.sure.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aajogo.jogo.sure.domain.AajogoRepository
import com.aajogo.jogo.sure.ui.game.spin.GameFragment.Companion.START_BALANCE
import com.aajogo.jogo.sure.ui.game.spin.GameFragment.Companion.BET_SIZE
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val aajogoRepository: AajogoRepository
) : ViewModel() {
    private var _viewEffect = MutableLiveData<ViewAajogoEffect>()
    val viewEffect: LiveData<ViewAajogoEffect> = _viewEffect

    var isSoundOn = true
    var isMusicOn = true
    var isVibrationOn = true
    var balance = START_BALANCE
    var betSize = BET_SIZE
    var win = 0

    private val networkHandler = CoroutineExceptionHandler { _, _ ->
        viewModelScope.launch {
            _viewEffect.value = ViewAajogoEffect.NetworkError
        }
    }

    fun handleEvents(event: GetLinkFromAajogoUrlEvent) {
        when (event) {
            is GetLinkFromAajogoUrlEvent.GetResponseFromUrl -> getResponse()
        }
    }

    private fun getResponse() {
        viewModelScope.launch(networkHandler) {
            _viewEffect.value = ViewAajogoEffect.AajogoUrlResponse(aajogoRepository.getLinkFromAajogoUrl())
        }
    }

    sealed class GetLinkFromAajogoUrlEvent {
        object GetResponseFromUrl : GetLinkFromAajogoUrlEvent()
    }

    sealed class ViewAajogoEffect {
        data class AajogoUrlResponse(val response: String) : ViewAajogoEffect()
        object NetworkError : ViewAajogoEffect()
    }
}