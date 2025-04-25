package com.matin.roadrunner.feature.mainqeust

import com.matin.roadrunner.core.common.Position
import com.matin.roadrunner.feature.mainqeust.di.RandomMovement
import com.matin.roadrunner.feature.mainqeust.model.TaxiModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.random.Random

@Singleton
class TaxiManager
    @Inject
    constructor(
        private val runnerController: RunnerController,
        private val gameConfig: GameConfig,
        @RandomMovement private val randomMovementStrategy: MovementStrategy,
    ) {
        private val _taxisState = MutableStateFlow<List<TaxiModel>>(emptyList())
        val taxis = _taxisState.asStateFlow()

        init {
            initTaxis()
        }

        private fun initTaxis() {
            _taxisState.update {
                List(gameConfig.taxiCount) {
                    TaxiModel(
                        id = it.toString(),
                        name = "Taxi $it",
                        point = 5,
                        position =
                            Position(
                                x = Random.nextInt(0, gameConfig.playGroundSize),
                                y = Random.nextInt(0, gameConfig.playGroundSize),
                            ),
                    )
                }
            }
        }

        fun moveTaxis() {
            _taxisState.update { currentTaxis ->
                currentTaxis.map { taxi ->
                    val nextPosition = randomMovementStrategy.calculateNextMove(taxi.position, runnerController.runner.value)
                    taxi.copy(position = nextPosition)
                }
            }
        }
    }
