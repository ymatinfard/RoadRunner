package com.matin.roadrunner.feature.mainqeust

import com.matin.roadrunner.feature.mainqeust.model.DriverUiModel
import com.matin.roadrunner.feature.mainqeust.model.GroundCellUiModel
import kotlin.random.Random

class PlayGroundEngine(private val gridSize: Int, private val driverCount: Int) {
    private var drivers =
        List(driverCount) {
            DriverUiModel(
                id = it.toString(),
                name = "Driver $it",
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
        val driversMap = drivers.associateBy { it.x to it.y }
        return List(gridSize) { row ->
            List(gridSize) { column ->
                val driver = driversMap[row to column]
                GroundCellUiModel(cellId = Random.nextLong(), driver = driver)
            }
        }
    }

    private fun moveDrivers() {
        drivers =
            drivers.map { driver ->
                val nextPosition = calculateDriverNextPosition(driver)
                driver.copy(x = nextPosition.first, y = nextPosition.second)
            }
    }

    fun getPlaygroundCellState(): List<List<GroundCellUiModel>> {
        moveDrivers()
        return initPlayground()
    }

    private fun calculateDriverNextPosition(driver: DriverUiModel): Pair<Int, Int> {
        return validMoves.map { (x, y) -> driver.x + x to driver.y + y }
            .filter { isWithInGrid(it.first, it.second) }.randomOrNull() ?: generateRandomMove()
    }

    private fun isWithInGrid(x: Int, y: Int) = x in 0 until gridSize && y in 0 until gridSize

    private fun generateRandomMove() =
        Pair(Random.nextInt(0, gridSize), Random.nextInt(0, gridSize))
}
