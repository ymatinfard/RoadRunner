package com.matin.roadrunner.feature.mainqeust

import android.util.Log
import com.matin.roadrunner.core.common.Position
import javax.inject.Inject

class DirectMovementStrategy
    @Inject
    constructor() : MovementStrategy {
        override fun calculateNextMove(current: Position, target: Position?): Position {
            if (target == null) {
                Log.w("DirectMovementStrategy", "Target is null")
                return current
            }

            // First move horizontally
            if (current.x != target.x) {
                val direction = if (current.x < target.x) 1 else -1
                return current.copy(x = current.x + direction)
            }

            // Then move vertically
            if (current.y != target.y) {
                val direction = if (current.y < target.y) 1 else -1
                return current.copy(y = current.y + direction)
            }

            return current
        }
    }
