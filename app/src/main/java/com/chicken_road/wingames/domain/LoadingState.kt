package com.chicken_road.wingames.domain

sealed class LoadingState {
    data object InitState : LoadingState()
    data object NoNetworkState : LoadingState()
    data class ContentState(val url: String) : LoadingState()
}