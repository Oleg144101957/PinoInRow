package com.boo.me.ran.g.domain

sealed class LoadingState {
    data object InitState : LoadingState()
    data object NoNetworkState : LoadingState()
    data object MenuState : LoadingState()
    data class ContentState(val url: String) : LoadingState()
}