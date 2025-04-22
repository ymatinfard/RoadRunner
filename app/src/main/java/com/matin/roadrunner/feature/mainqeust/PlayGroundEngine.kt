package com.matin.roadrunner.feature.mainqeust

import com.matin.roadrunner.core.common.Position
import com.matin.roadrunner.feature.mainqeust.model.GroundCellUiModel
import com.matin.roadrunner.feature.mainqeust.model.TaxiUiModel
import kotlin.random.Random

class PlayGroundEngine(private val gridSize: Int, private val taxiCount: Int) {
    val runner = Position(Random.nextInt(0, gridSize), Random.nextInt(0, gridSize))

    private var taxis =
        List(taxiCount) {
            TaxiUiModel(
                id = it.toString(),
                name = "Taxi $it",
                point = 5,
                x = Random.nextInt(0, gridSize),
                y = Random.nextInt(0, gridSize),
            )
        }

    private val possibleMoves =
        listOf(
            Position(1, 0), // Right
            Position(-1, 0), // Left
            Position(0, 1), // Top
            Position(0, -1), // Bottom
        )

    private fun initPlayground(): List<List<GroundCellUiModel>> {
        val taxisMap = taxis.associateBy { it.x to it.y }
        return List(gridSize) { row ->
            List(gridSize) { column ->
                if (row == runner.x && column == runner.y) {
                    GroundCellUiModel(cellId = Random.nextLong(), hasRunner = true)
                } else {
                    val taxi = taxisMap[row to column]
                    GroundCellUiModel(cellId = Random.nextLong(), taxi = taxi)
                }
            }
        }
    }

    private fun moveTaxis() {
        taxis =
            taxis.map { taxi ->
                val nextPosition = calculateTaxiNextPosition(taxi)
                taxi.copy(x = nextPosition.x, y = nextPosition.y)
            }
    }

    fun getPlaygroundCellState(): List<List<GroundCellUiModel>> {
        moveTaxis()
        return initPlayground()
    }

    private fun calculateTaxiNextPosition(taxi: TaxiUiModel): Position {
        return possibleMoves.map { position -> Position(taxi.x + position.x, taxi.y + position.y) }
            .filter { isValidMove(it.x, it.y) }.randomOrNull() ?: generateRandomMove()
    }

    private fun isValidMove(x: Int, y: Int) = x in 0 until gridSize && y in 0 until gridSize && x != runner.x && y != runner.y

    private fun generateRandomMove() =
        Position(Random.nextInt(0, gridSize), Random.nextInt(0, gridSize))
}
