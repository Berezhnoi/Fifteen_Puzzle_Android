package com.igla.fifteen_puzzle

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun PuzzleGameUI(
    state: List<Int>,
    steps: Int,
    onCellClick: (Int) -> Unit,
    onResetGame: () -> Unit,
    isVictory: Boolean
) {
    // Detect screen orientation
    val configuration = LocalConfiguration.current
    val isPortrait = configuration.orientation == android.content.res.Configuration.ORIENTATION_PORTRAIT

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        if (isPortrait) {
            PortraitLayout(
                state = state,
                steps = steps,
                onCellClick = onCellClick,
                onResetGame = onResetGame,
            )
        } else {
            LandscapeLayout(
                state = state,
                steps = steps,
                onCellClick = onCellClick,
                onResetGame = onResetGame
            )
        }

        // Overlay for the victory message
        if (isVictory) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        Brush.verticalGradient(
                            colors = listOf(Color(0xFF0057B7), Color(0xFFFFD700))
                        )
                    ),
                contentAlignment = Alignment.Center
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "Victory!",
                        color = Color.White,
                        fontSize = 36.sp,
                        modifier = Modifier.padding(bottom = 8.dp)
                    )
                    Button(
                        onClick = onResetGame,
                        modifier = Modifier.padding(top = 8.dp)
                    ) {
                        Text("Start Again", fontSize = 20.sp)
                    }
                }
            }
        }
    }
}

@Composable
fun PortraitLayout(
    state: List<Int>,
    steps: Int,
    onCellClick: (Int) -> Unit,
    onResetGame: () -> Unit,
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier.padding(16.dp)
    ) {
        // Display the game title
        Text(
            text = "15 Puzzle Game",
            fontSize = 28.sp,
            color = Color.Black,
            modifier = Modifier.padding(bottom = 16.dp, top = 16.dp)
        )

        Column(
            modifier = Modifier
                .weight(1f)
                .fillMaxHeight(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            // Display Game Board
            GameBoard(state, onCellClick)

            Spacer(modifier = Modifier.height(16.dp))

            // Display the step counter
            Text(
                text = "Steps: $steps",
                fontSize = 24.sp,
                color = Color.Gray,
                modifier = Modifier.padding(top = 16.dp)
            )
        }

        // Display the reset button
        Button(
            onClick = onResetGame,
            modifier = Modifier.padding(top = 8.dp, bottom = 24.dp)
        ) {
            Text("Reset Game", fontSize = 20.sp)
        }
    }
}

@Composable
fun LandscapeLayout(
    state: List<Int>,
    steps: Int,
    onCellClick: (Int) -> Unit,
    onResetGame: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Display Game Board
        Box(
            modifier = Modifier
                .weight(2f)
                .fillMaxHeight(),
            contentAlignment = Alignment.Center
        ) {
            GameBoard(state, onCellClick)
        }

        Column(
            modifier = Modifier
                .weight(1f)
                .fillMaxHeight()
                .padding(start = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            // Display the game title
            Text(
                text = "15 Puzzle Game",
                fontSize = 28.sp,
                color = Color.Black,
                modifier = Modifier.padding(bottom = 16.dp, top = 16.dp)
            )

            // Display the step counter
            Text(
                text = "Steps: $steps",
                fontSize = 24.sp,
                color = Color.Gray,
                modifier = Modifier.padding(bottom = 16.dp)
            )

            // Display the reset button
            Button(
                onClick = onResetGame,
                modifier = Modifier.padding(top = 8.dp)
            ) {
                Text("Reset Game", fontSize = 20.sp)
            }
        }
    }
}

@Composable
fun GameBoard(state: List<Int>, onCellClick: (Int) -> Unit) {
    Column {
        for (row in 0 until FifteenEngine.DIM) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                for (col in 0 until FifteenEngine.DIM) {
                    val cellValue = state[row * FifteenEngine.DIM + col]
                    GameCell(cellValue, onCellClick)
                }
            }
        }
    }
}

@Composable
fun GameCell(value: Int, onClick: (Int) -> Unit) {
    Box(
        modifier = Modifier
            .size(80.dp)
            .padding(8.dp)
            .border(BorderStroke(2.dp, Color.Black), RoundedCornerShape(8.dp))
            .clickable { onClick(value) },
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = if (value == FifteenEngine.EMPTY) "" else value.toString(),
            fontSize = 28.sp
        )
    }
}

