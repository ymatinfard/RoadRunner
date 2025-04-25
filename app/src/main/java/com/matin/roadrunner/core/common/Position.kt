package com.matin.roadrunner.core.common

data class Position(val x: Int, val y: Int) {
    operator fun plus(other: Position): Position {
        return Position(x + other.x, y + other.y)
    }

    operator fun minus(other: Position): Position {
        return Position(x - other.x, y - other.y)
    }
}
