package com.matin.roadrunner.feature.mainqeust.engine

import com.matin.roadrunner.core.common.Position
import com.matin.roadrunner.feature.mainqeust.GameConfig
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
    ) {
        private var _runner =
            MutableStateFlow(
                generateRandomPosition(to = gameConfig.cellCount),
            )
        val runner = _runner.asStateFlow()

        fun reset() {
            _runner.update {
                generateRandomPosition(to = gameConfig.cellCount)
            }
        }
    }

fun generateRandomPosition(from: Int = 0, to: Int) =
    Position(
        Random.nextInt(from, to),
        Random.nextInt(from, to),
    )
