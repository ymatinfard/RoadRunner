package com.matin.roadrunner.feature.mainqeust

import com.matin.roadrunner.core.common.Position
import javax.inject.Inject
import kotlin.random.Random

class RandomMovementStrategy
    @Inject
    constructor(private val gameConfig: GameConfig) : MovementStrategy {
        private val possibleMoves =
            listOf(
                Position(1, 0), // Right
                Position(-1, 0), // Left
                Position(0, 1), // Top
                Position(0, -1), // Bottom
            )

        private fun isValidPosition(position: Position) =
            position.x in 0 until gameConfig.playGroundSize && position.y in 0 until gameConfig.playGroundSize

        override fun calculateNextMove(taxi: Position, runner: Position?): Position {
            val availableMoves =
                possibleMoves.map { possibleMove -> taxi + possibleMove }
                    .filter { isValidPosition(it) }
                    .filter { isCollision(it, runner).not() }
            return availableMoves.randomOrNull() ?: generateRandomMove()
        }

        private fun isCollision(taxi: Position, runner: Position?): Boolean {
            if (runner == null) return false
            return taxi.x == runner.x && taxi.y == runner.y
        }

        private fun generateRandomMove(): Position =
            Position(Random.nextInt(0, gameConfig.playGroundSize), Random.nextInt(0, gameConfig.playGroundSize))
    }
