package com.matin.roadrunner.feature.mainqeust.engine

import com.matin.roadrunner.core.common.Position
import com.matin.roadrunner.feature.mainqeust.GameConfig
import com.matin.roadrunner.feature.mainqeust.model.Direction
import com.matin.roadrunner.feature.mainqeust.model.TaxiModel
import javax.inject.Inject

class RandomMovementStrategy
    @Inject
    constructor(
        private val gameConfig: GameConfig,
        private val wallsProvider: WallProvider,
        private val runnerController: RunnerController,
    ) : MovementStrategy {
        private fun inBounds(position: Position) =
            position.x in 0 until gameConfig.cellCount && position.y in 0 until gameConfig.cellCount

        override fun calculateNextMove(taxi: TaxiModel): Position {
            var nextMove = taxi.position + taxi.direction.move

            while (isSafeMove(nextMove).not()) {
                nextMove = taxi.position + Direction.values.random().move
            }

            return nextMove
        }

        private fun isSafeMove(nextMove: Position) =
            inBounds(nextMove) && nextMove.hasCollision(runnerController.runner.value).not() && passable(nextMove)

        private fun passable(position: Position): Boolean {
            return wallsProvider.walls.value.contains(position).not()
        }
    }

fun Position.hasCollision(target: Position) = this == target
