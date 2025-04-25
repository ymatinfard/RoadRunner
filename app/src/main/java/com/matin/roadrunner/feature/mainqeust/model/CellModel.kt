package com.matin.roadrunner.feature.mainqeust.model

data class CellModel(
    var cellId: Long = 0,
    var hasRunner: Boolean = false,
    var taxi: TaxiModel? = null,
)
