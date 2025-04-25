package com.matin.roadrunner.feature.mainqeust

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.matin.roadrunner.core.common.MessageType
import com.matin.roadrunner.core.common.ToastMessageModel
import com.matin.roadrunner.feature.mainqeust.model.CellModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class QuestScreenViewModel
    @Inject
    constructor(private val gameEngine: GameEngine) : ViewModel() {
        val playGroundCellsState: StateFlow<List<List<CellModel>>> = gameEngine.playgroundState

        private val _toastMessage = MutableSharedFlow<ToastMessageModel>()
        val toastMessage = _toastMessage.asSharedFlow()

        init {
            startGame()
        }

        private fun startGame() {
            gameEngine.start(viewModelScope)
        }

        fun onCellClick(cell: CellModel) {
            viewModelScope.launch {
                if (cell.taxi != null) {
                    gameEngine.selectTaxi(cell.taxi!!)
                } else {
                    _toastMessage.emit(ToastMessageModel(MessageType.EMPTY_CELL))
                }
            }
        }
    }
