package com.matin.roadrunner.feature.mainqeust.model

data class GroundCellUiModel(
    val cellId: Long,
    val hasRunner: Boolean = false,
    val driver: DriverUiModel? = null,
)