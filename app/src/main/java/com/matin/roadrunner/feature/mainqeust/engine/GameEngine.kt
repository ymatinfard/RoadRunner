package com.matin.roadrunner.feature.mainqeust.engine

import com.matin.roadrunner.feature.mainqeust.model.TaxiModel
import com.matin.roadrunner.feature.mainqeust.search.ShortestPathFinder
import com.matin.roadrunner.feature.mainqeust.search.SquareGrid
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import javax.inject.Inject

class GameEngine
    @Inject
    constructor(
        private val ticker: Ticker,
        val runnerController: RunnerController,
        val taxiManager: TaxiManager,
        val pathFinder: ShortestPathFinder,
        private val squareGrid: SquareGrid,
        val wallProvider: WallProvider,
    ) {
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
            taxiManager.moveTaxis()
        }

        fun selectTaxi(taxi: TaxiModel) {
            stopGame()
            pathFinder.getPath(squareGrid, runnerController.runner.value, taxi.position)
        }

        fun restart(scope: CoroutineScope) {
            runnerController.reset()
            taxiManager.reset()
            pathFinder.reset()
            start(scope)
        }

        fun stopGame() {
            ticker.stop()
            job?.cancel()
            job = null
        }
    }
