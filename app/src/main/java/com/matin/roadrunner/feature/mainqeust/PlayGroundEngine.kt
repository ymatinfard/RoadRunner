package com.matin.roadrunner.feature.mainqeust

import com.matin.roadrunner.feature.mainqeust.model.TaxiUiModel
import com.matin.roadrunner.feature.mainqeust.model.GroundCellUiModel
import kotlin.random.Random

class PlayGroundEngine(private val gridSize: Int, private val taxiCount: Int) {
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

    private val validMoves =
        listOf(
            1 to 0, // Right
            -1 to 0, // Left
            0 to 1, // Top
            0 to -1, // Bottom
        )

    private fun initPlayground(): List<List<GroundCellUiModel>> {
        val taxisMap = taxis.associateBy { it.x to it.y }
        return List(gridSize) { row ->
            List(gridSize) { column ->
                val taxi = taxisMap[row to column]
                GroundCellUiModel(cellId = Random.nextLong(), taxi = taxi)
            }
        }
    }

    private fun moveTaxis() {
        taxis =
            taxis.map { taxi ->
                val nextPosition = calculateTaxiNextPosition(taxi)
                taxi.copy(x = nextPosition.first, y = nextPosition.second)
            }
    }

    fun getPlaygroundCellState(): List<List<GroundCellUiModel>> {
        moveTaxis()
        return initPlayground()
    }

    private fun calculateTaxiNextPosition(taxi: TaxiUiModel): Pair<Int, Int> {
        return validMoves.map { (x, y) -> taxi.x + x to taxi.y + y }
            .filter { isWithInGrid(it.first, it.second) }.randomOrNull() ?: generateRandomMove()
    }

    private fun isWithInGrid(x: Int, y: Int) = x in 0 until gridSize && y in 0 until gridSize

    private fun generateRandomMove() =
        Pair(Random.nextInt(0, gridSize), Random.nextInt(0, gridSize))
}
