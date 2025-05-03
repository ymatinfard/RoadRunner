package com.matin.roadrunner.feature.mainqeust.engine

import com.matin.roadrunner.core.common.Position
import com.matin.roadrunner.feature.mainqeust.model.TaxiModel
import com.matin.roadrunner.feature.mainqeust.search.ShortestPathFinder
import com.matin.roadrunner.feature.mainqeust.search.SquareGrid
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch
import javax.inject.Inject

class GameEngine
    @Inject
    constructor(
        private val ticker: Ticker,
        private val runnerController: RunnerController,
        private val taxiManager: TaxiManager,
        private val pathFinder: ShortestPathFinder,
        private val squareGrid: SquareGrid,
        private val wallProvider: WallProvider,
    ) {
        var gameUpdateJob: Job? = null
        var gameLoopJob: Job? = null

        private var _gameState = MutableStateFlow(GameState())
        val gameState = _gameState.asStateFlow()

        fun start(scope: CoroutineScope) {
            stopGame()
            ticker.start(scope)

            gameUpdateJob =
                scope.launch {
                    combine(
                        runnerController.runner,
                        taxiManager.taxis,
                        pathFinder.path,
                        wallProvider.walls,
                    ) {
                        runner, taxis, path, walls ->
                        GameState(runner, taxis, path, walls)
                    }.collectLatest {
                        _gameState.value = it
                    }
                }

            gameLoopJob =
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
            gameLoopJob?.cancel()
            gameLoopJob = null
        }
    }

data class GameState(
    val runner: Position = Position(0, 0),
    val taxis: List<TaxiModel> = emptyList(),
    val path: List<Position> = emptyList(),
    val walls: List<Position> = emptyList(),
)
