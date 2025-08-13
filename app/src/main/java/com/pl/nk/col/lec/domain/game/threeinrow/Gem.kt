package com.pl.nk.col.lec.domain.game.threeinrow

/**
 * Gem is a separate element on the field, has a unique id and type.
 */
data class Gem(
    val id: Long,
    val type: ThreeInRowElementType
) 