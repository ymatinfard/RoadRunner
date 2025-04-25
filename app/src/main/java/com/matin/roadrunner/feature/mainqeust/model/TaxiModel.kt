package com.matin.roadrunner.feature.mainqeust.model

import com.matin.roadrunner.core.common.Position

data class TaxiModel(
    val id: String,
    val name: String,
    val point: Int,
    val position: Position,
)
