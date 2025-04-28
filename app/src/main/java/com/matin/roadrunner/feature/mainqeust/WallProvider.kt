package com.matin.roadrunner.feature.mainqeust

import com.matin.roadrunner.core.common.Position
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class WallProvider
    @Inject
    constructor() {
        var _walls = MutableStateFlow<List<Position>>(emptyList())
        val walls = _walls.asStateFlow()

        init {
            generateWalls()
        }

        fun generateWalls() {
            _walls.update {
                listOf(
                    Position(3, 3),
                    Position(4, 3),
                    Position(5, 3),
                    Position(6, 6),
                    Position(6, 7),
                    Position(6, 8),
                    Position(1, 8),
                    Position(1, 9),
                )
            }
        }
    }
