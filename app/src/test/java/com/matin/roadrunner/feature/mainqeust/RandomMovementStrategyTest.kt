package com.matin.roadrunner.feature.mainqeust

import com.matin.roadrunner.core.common.Position
import com.matin.roadrunner.feature.mainqeust.engine.RandomMovementStrategy
import com.matin.roadrunner.feature.mainqeust.engine.RunnerController
import com.matin.roadrunner.feature.mainqeust.engine.WallProvider
import com.matin.roadrunner.feature.mainqeust.model.Direction
import com.matin.roadrunner.feature.mainqeust.model.TaxiModel
import org.junit.Assert.assertEquals
import org.junit.Test

class RandomMovementStrategyTest {
    @Test
    fun calculateNextMove() {
        val gameConfig = GameConfig(10)
        val wallsProvider = WallProvider()
        val runnerController = RunnerController(gameConfig)
        val strategy = RandomMovementStrategy(gameConfig, wallsProvider, runnerController)
        val current =
            TaxiModel(
                id = "1",
                name = "taxi1",
                point = 10,
                position = Position(5, 5),
                direction = Direction.DOWN,
            )

        val expectedNextMove = Position(5, 6)

        val nextMove = strategy.calculateNextMove(current)

        assertEquals(expectedNextMove, nextMove)
    }
}
