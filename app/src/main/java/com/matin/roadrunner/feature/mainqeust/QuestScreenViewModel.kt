package com.matin.roadrunner.feature.mainqeust

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.matin.roadrunner.core.common.Position
import com.matin.roadrunner.core.common.ToastMessageModel
import com.matin.roadrunner.feature.mainqeust.model.TaxiModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import javax.inject.Inject

@HiltViewModel
class QuestScreenViewModel
    @Inject
    constructor(private val gameEngine: GameEngine) : ViewModel() {
        val runnerPosition: StateFlow<Position> = gameEngine.runnerController.runner
        val texis: StateFlow<List<TaxiModel>> = gameEngine.taxiManager.taxis
        val path: StateFlow<List<Position>> = gameEngine.pathState
        val walls: StateFlow<List<Position>> = gameEngine.walls

        private val _toastMessage = MutableSharedFlow<ToastMessageModel>()
        val toastMessage = _toastMessage.asSharedFlow()

        init {
            startGame()
        }

        private fun startGame() {
            gameEngine.start(viewModelScope)
        }

        fun restartGame() {
            gameEngine.restart(viewModelScope)
        }

        fun onTaxiClick(taxi: TaxiModel) {
            gameEngine.selectTaxi(taxi)
        }
    }
