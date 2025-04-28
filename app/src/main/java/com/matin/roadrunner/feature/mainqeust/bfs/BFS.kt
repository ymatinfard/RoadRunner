package com.matin.roadrunner.feature.mainqeust.bfs

import com.matin.roadrunner.core.common.Position
import java.util.LinkedList
import java.util.Queue
import javax.inject.Inject

class BFS
    @Inject
    constructor() {
        fun search(grid: SquareGrid, start: Position): Map<Position, Position> {
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

        fun getPath(grid: SquareGrid, runner: Position, taxi: Position): List<Position> {
            val path = search(grid, runner)
            var nextPath = path[taxi] as Position
            val pathList = mutableListOf<Position>()
            while (nextPath != runner) {
                pathList.add(nextPath)
                nextPath = path[nextPath] as Position
            }

            return pathList
        }
    }
