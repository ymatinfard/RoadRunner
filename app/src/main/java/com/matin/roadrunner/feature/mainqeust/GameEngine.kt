package com.matin.roadrunner.feature.mainqeust

import com.matin.roadrunner.feature.mainqeust.model.TaxiModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import javax.inject.Inject

class GameEngine
    @Inject
    constructor(
        private val ticker: Ticker,
        private val runnerController: RunnerController,
        private val taxiManager: TaxiManager,
        private val playgroundStateManager: PlaygroundStateManager,
    ) {
        val playgroundState = playgroundStateManager.playGroundState
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
            runnerController.selectTarget(taxi.position)
        }

        fun restart(scope: CoroutineScope) {
            runnerController.reset()
            taxiManager.reset()
            start(scope)
        }

        fun stopGame() {
            job?.cancel()
            job = null
        }
    }
