package com.matin.roadrunner.feature.mainqeust.engine

import com.matin.roadrunner.core.common.Position
import com.matin.roadrunner.feature.mainqeust.model.TaxiModel

interface MovementStrategy {
    fun calculateNextMove(current: TaxiModel): Position
}
