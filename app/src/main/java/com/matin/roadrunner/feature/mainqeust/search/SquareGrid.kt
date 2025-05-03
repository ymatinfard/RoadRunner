package com.matin.roadrunner.feature.mainqeust.search

import com.matin.roadrunner.core.common.Position
import com.matin.roadrunner.feature.mainqeust.GameConfig
import com.matin.roadrunner.feature.mainqeust.engine.WallProvider
import javax.inject.Inject

class SquareGrid
    @Inject
    constructor(private val gc: GameConfig, private val wallsProvider: WallProvider) {
        private val directions = listOf(Position(1, 0), Position(0, 1), Position(-1, 0), Position(0, -1))

        private fun inBounds(position: Position): Boolean {
            return position.x in 0 until gc.cellCount && position.y in 0 until gc.cellCount
        }

        private fun passable(position: Position): Boolean {
            return wallsProvider.walls.value.contains(position).not()
        }

        fun neighbors(cell: Position): List<Position> {
            val neighbors =
                directions.map { direction -> cell + direction }
                    .filter { passable(it) }
                    .filter { inBounds(it) }
            return neighbors
        }
    }
