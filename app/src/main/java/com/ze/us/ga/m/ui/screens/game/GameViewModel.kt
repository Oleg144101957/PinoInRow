package com.ze.us.ga.m.ui.screens.game

import androidx.lifecycle.ViewModel
import com.ze.us.ga.m.domain.DataStoreRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class GameViewModel @Inject constructor(
    private val sharedPrefStorage: DataStoreRepository
) : ViewModel() {
    fun getSpeed(): Float {
        return sharedPrefStorage.getSpeed()
    }
}