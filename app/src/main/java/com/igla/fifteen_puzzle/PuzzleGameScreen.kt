package com.igla.fifteen_puzzle

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun PuzzleGameScreen(viewModel: PuzzleGameViewModel = viewModel()) {
    val state by viewModel.state
    val steps by viewModel.steps

    PuzzleGameUI(
        state = state,
        steps = steps,
        onCellClick = { cellValue -> viewModel.onCellClick(cellValue) },
        onResetGame = { viewModel.resetGame() },
        isVictory = viewModel.isVictory()
    )
}
