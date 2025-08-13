package com.pl.nk.col.lec.domain.game.threeinrow

import com.pl.nk.col.lec.R

/**
 * Type of item for the game "3 in Row". Each type has its own picture.
 */
enum class ThreeInRowElementType(val imageRes: Int) {
    RED(R.drawable.gem_red),
    BLUE(R.drawable.gem_blue),
    GREEN(R.drawable.gem_green),
    YELLOW(R.drawable.coin),
    PURPLE(R.drawable.gem_purple),
    ORANGE(R.drawable.gem_orange);

    companion object {
        val defaultTypes = listOf(RED, BLUE, GREEN, YELLOW, PURPLE, ORANGE)
    }
}