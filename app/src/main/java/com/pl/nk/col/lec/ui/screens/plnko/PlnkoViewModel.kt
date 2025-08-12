package com.pl.nk.col.lec.ui.screens.plnko

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.pl.nk.col.lec.domain.DataStoreRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class PlnkoViewModel @Inject constructor(
    private val repository: DataStoreRepository
) : ViewModel() {

    private val _balance = MutableStateFlow(repository.getBalance())
    val balance: StateFlow<Int> = _balance.asStateFlow()

    fun placeStake(stake: Int): Boolean {
        val currentBalance = _balance.value
        if (currentBalance == null || currentBalance < stake) return false
        _balance.value = currentBalance - stake
        repository.saveBalance(_balance.value!!)
        return true
    }

    fun addWin(amount: Int) {
        val currentBalance = _balance.value ?: 0
        val newBalance = currentBalance + amount
        _balance.value = newBalance
        repository.saveBalance(newBalance)
    }

}

