package com.matin.roadrunner.feature.mainqeust

import com.matin.roadrunner.core.common.Position
import org.junit.Test

class RandomMovementStrategyTest {
    @Test
    fun calculateNextMove() {
        val gameConfig = GameConfig(10)
        val strategy = RandomMovementStrategy(gameConfig)
        val current = Position(2, 2)
        val runner = Position(7, 7)
        val possibleMoves =
            listOf(
                Position(1, 0), // Right
                Position(-1, 0), // Left
                Position(0, 1), // Top
                Position(0, -1), // Bottom
            ).map { current + it }
        val nextMove = strategy.calculateNextMove(current, runner)

        assert(nextMove in possibleMoves)
    }
}
