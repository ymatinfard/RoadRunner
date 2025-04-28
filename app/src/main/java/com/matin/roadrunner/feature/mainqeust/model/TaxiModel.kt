package com.matin.roadrunner.feature.mainqeust.model

import com.matin.roadrunner.core.common.Position

data class TaxiModel(
    val id: String,
    val name: String,
    val point: Int,
    val position: Position,
    val direction: Direction = Direction.DOWN,
)

enum class Direction(val move: Position) {
    UP(Position(0, -1)),
    DOWN(Position(0, 1)),
    LEFT(Position(-1, 0)),
    RIGHT(Position(1, 0)),
    ;

    companion object {
        val values = entries.toTypedArray()
    }
}
