package com.matin.roadrunner.feature.mainqeust

import com.matin.roadrunner.core.common.Position

interface MovementStrategy {
    fun calculateNextMove(current: Position, target: Position?): Position
}
