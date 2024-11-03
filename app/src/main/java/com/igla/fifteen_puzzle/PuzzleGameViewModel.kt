package com.igla.fifteen_puzzle

import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel

class PuzzleGameViewModel(private val engine: FifteenEngine = FifteenEngine) : ViewModel() {
    var state = mutableStateOf(engine.getInitialState())
        private set

    var steps = mutableIntStateOf(0)
        private set

    fun onCellClick(cellValue: Int) {
        val updatedState = engine.transitionState(state.value, cellValue)
        if (updatedState != state.value) {
            steps.intValue += 1
        }
        state.value = updatedState
    }

    fun isVictory(): Boolean = engine.isWin(state.value)

    fun resetGame() {
        state.value = engine.getInitialState()
        steps.intValue = 0
    }
}
