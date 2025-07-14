package com.cat.cher.oma.ui.screens.game

import androidx.lifecycle.ViewModel
import com.cat.cher.oma.domain.DataStoreRepository
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