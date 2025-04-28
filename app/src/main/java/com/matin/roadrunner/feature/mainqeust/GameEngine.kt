package com.matin.roadrunner.feature.mainqeust

import com.matin.roadrunner.core.common.Position
import com.matin.roadrunner.feature.mainqeust.bfs.BFS
import com.matin.roadrunner.feature.mainqeust.bfs.SquareGrid
import com.matin.roadrunner.feature.mainqeust.model.TaxiModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

class GameEngine
    @Inject
    constructor(
        private val ticker: Ticker,
        val runnerController: RunnerController,
        val taxiManager: TaxiManager,
        private val playgroundStateManager: PlaygroundStateManager,
        private val pathFinder: BFS,
        private val squareGrid: SquareGrid,
        private val wallProvider: WallProvider,
    ) {
        val walls = wallProvider.walls
        val pathState = MutableStateFlow<List<Position>>(emptyList())

        var job: Job? = null

        fun start(scope: CoroutineScope) {
            stopGame()
            ticker.start(scope)
            job =
                scope.launch {
                    ticker.tick.collect {
                        gameLoop()
                    }
                }
        }

        private fun gameLoop() {
            if (runnerController.targetSelected()) {
                val isRunnerRunning = runnerController.moveToTarget()
                if (isRunnerRunning.not()) {
                    stopGame()
                }
            } else {
                taxiManager.moveTaxis()
            }

            playgroundStateManager.updateState()
        }

        fun selectTaxi(taxi: TaxiModel) {
            stopGame()
            pathState.update {
                pathFinder.getPath(squareGrid, runnerController.runner.value, taxi.position)
            }
        }

        fun restart(scope: CoroutineScope) {
            runnerController.reset()
            taxiManager.reset()
            start(scope)
        }

        fun stopGame() {
            ticker.stop()
            job?.cancel()
            job = null
        }
    }
