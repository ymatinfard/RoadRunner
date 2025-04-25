package com.matin.roadrunner.feature.mainqeust

import com.matin.roadrunner.core.common.Position
import com.matin.roadrunner.feature.mainqeust.di.DirectMovement
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.random.Random

@Singleton
class RunnerController
    @Inject
    constructor(
        private val gameConfig: GameConfig,
        @DirectMovement val movementStrategy: MovementStrategy,
    ) {
        private var _runner =
            MutableStateFlow(
                Position(
                    Random.nextInt(0, gameConfig.playGroundSize),
                    Random.nextInt(0, gameConfig.playGroundSize),
                ),
            )
        val runner = _runner.asStateFlow()
        private var target: Position? = null

        fun selectTarget(target: Position) {
            this.target = target
        }

        fun targetSelected() = target != null

        fun moveToTarget(): Boolean {
            if (runner.value == target) return false

            val nextPosition = movementStrategy.calculateNextMove(runner.value, target)
            _runner.update {
                nextPosition
            }
            return true
        }
    }
