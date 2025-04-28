package com.matin.roadrunner.feature.mainqeust.search

import com.matin.roadrunner.core.common.Position
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import java.util.LinkedList
import java.util.Queue
import javax.inject.Inject

class ShortestPathFinder
    @Inject
    constructor() {
        private val _path = MutableStateFlow<List<Position>>(emptyList())
        val path = _path.asStateFlow()

        private fun search(grid: SquareGrid, start: Position): Map<Position, Position> {
            val frontier: Queue<Position> = LinkedList()
            val reached: MutableSet<Position> = mutableSetOf()
            val cameFrom: MutableMap<Position, Position> = mutableMapOf()

            frontier.add(start)
            reached.add(start)
            cameFrom[start] = start

            while (frontier.isNotEmpty()) {
                val current = frontier.remove()
                for (next in grid.neighbors(current)) {
                    if (next !in reached) {
                        frontier.add(next)
                        reached.add(next)
                        cameFrom[next] = current
                    }
                }
            }

            return cameFrom
        }

        fun getPath(grid: SquareGrid, runner: Position, taxi: Position) {
            val path = search(grid, runner)
            var nextPath = path[taxi] as Position
            val pathList = mutableListOf<Position>()
            while (nextPath != runner) {
                pathList.add(nextPath)
                nextPath = path[nextPath] as Position
            }

            _path.update {
                pathList.reversed()
            }
        }

        fun reset() {
            _path.update {
                emptyList()
            }
        }
    }
