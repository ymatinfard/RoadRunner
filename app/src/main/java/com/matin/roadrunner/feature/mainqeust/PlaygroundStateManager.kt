package com.matin.roadrunner.feature.mainqeust

import com.matin.roadrunner.core.common.Position
import com.matin.roadrunner.feature.mainqeust.model.CellModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

class PlaygroundStateManager
    @Inject
    constructor(
        private val gameConfig: GameConfig,
        private val taxiManager: TaxiManager,
        private val runnerController: RunnerController,
    ) {
        private var _playgroundState = MutableStateFlow(listOf<List<CellModel>>())
        val playGroundState = _playgroundState.asStateFlow()

        fun updateState() {
            val taxisMap = taxiManager.taxis.value.associateBy { it.position }
            var counter = 0
            _playgroundState.update {
                List(gameConfig.playGroundSize) { row ->
                    List(gameConfig.playGroundSize) { column ->
                        counter++
                        if (column == runnerController.runner.value.x && row == runnerController.runner.value.y) {
                            CellModel(cellId = counter.toLong(), hasRunner = true)
                        } else {
                            val taxi = taxisMap[Position(column, row)]
                            CellModel(cellId = counter.toLong(), taxi = taxi)
                        }
                    }
                }
            }
        }
    }
