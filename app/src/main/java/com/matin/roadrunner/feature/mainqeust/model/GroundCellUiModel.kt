package com.matin.roadrunner.feature.mainqeust.model

data class GroundCellUiModel(
    var cellId: Long = 0,
    var hasRunner: Boolean = false,
    var driver: DriverUiModel? = null,
)
