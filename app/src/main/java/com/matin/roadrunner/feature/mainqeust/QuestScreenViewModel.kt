package com.matin.roadrunner.feature.mainqeust

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.matin.roadrunner.core.common.MessageType
import com.matin.roadrunner.core.common.ToastMessageModel
import com.matin.roadrunner.feature.mainqeust.model.GroundCellUiModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class QuestScreenViewModel
    @Inject
    constructor() : ViewModel() {
        private val _playGroundCellsState = MutableStateFlow(listOf(listOf<GroundCellUiModel>()))
        val playGroundCellsState = _playGroundCellsState.asStateFlow()
        private val playGroundEngine = PlayGroundEngine(gridSize = 10, driverCount = 5)

        private val _toastMessage = MutableSharedFlow<ToastMessageModel>()
        val toastMessage = _toastMessage.asSharedFlow()

        private fun updateGroundCellsState() {
            _playGroundCellsState.update { playGroundEngine.getPlaygroundCellState() }
        }

        init {
            startGame()
        }

        private fun startGame() {
            viewModelScope.launch {
                while (true) {
                    updateGroundCellsState()
                    delay(2000)
                }
            }
        }

        fun onCellClick(cell: GroundCellUiModel) {
            viewModelScope.launch {
                if (cell.driver != null) {
                } else {
                    _toastMessage.emit(ToastMessageModel(MessageType.EMPTY_CELL))
                }
            }
        }
    }
