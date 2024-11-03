package com.igla.fifteen_puzzle

interface FifteenEngine {
    fun transitionState(oldState: List<Int>, cell: Int): List<Int>
    fun isWin(state: List<Int>): Boolean
    fun getInitialState(): List<Int>

    companion object : FifteenEngine {
        private const val GRID_SIZE = 4
        private const val EMPTY_CELL = 16
        private const val TOTAL_CELLS = GRID_SIZE * GRID_SIZE
        private const val WIN_SEQUENCE_END = TOTAL_CELLS - 1

        var DIM: Int = GRID_SIZE
            private set

        var EMPTY: Int = EMPTY_CELL
            private set

        override fun getInitialState(): List<Int> {
            var state: List<Int>
            do {
                state = (1..TOTAL_CELLS).toList().shuffled()
            } while (!isSolvable(state))
            return state
        }

        override fun transitionState(oldState: List<Int>, cell: Int): List<Int> {
            val emptyIndex = oldState.indexOf(EMPTY_CELL)
            val targetIndex = oldState.indexOf(cell)

            if (!isAdjacent(emptyIndex, targetIndex)) return oldState

            val newState = oldState.toMutableList()
            newState[emptyIndex] = cell
            newState[targetIndex] = EMPTY_CELL
            return newState
        }

        override fun isWin(state: List<Int>): Boolean {
            for (i in 0 until WIN_SEQUENCE_END) {
                if (state[i] != i + 1) return false
            }
            return state[WIN_SEQUENCE_END] == EMPTY_CELL
        }

        private fun isAdjacent(index1: Int, index2: Int): Boolean {
            if (index1 < 0 || index1 >= TOTAL_CELLS || index2 < 0 || index2 >= TOTAL_CELLS) return false

            val row1 = index1 / GRID_SIZE
            val col1 = index1 % GRID_SIZE
            val row2 = index2 / GRID_SIZE
            val col2 = index2 % GRID_SIZE

            return (row1 == row2 && kotlin.math.abs(col1 - col2) == 1) ||
                    (col1 == col2 && kotlin.math.abs(row1 - row2) == 1)
        }

        private fun isSolvable(state: List<Int>): Boolean {
            // Convert the flat list to a 2D array representation for easier calculations
            val puzzle = Array(DIM) { IntArray(DIM) }
            var index = 0

            for (row in 0 until DIM) {
                for (col in 0 until DIM) {
                    puzzle[row][col] = state[index++]
                }
            }

            // Count inversions in the given puzzle
            val invCount = getInvCount(state)

            // Find the position of the empty space
            val emptyRow = findEmptyPosition(puzzle)

            // If the grid is odd, return true if the inversion count is even
            return if (DIM % 2 == 1) {
                invCount % 2 == 0
            } else { // For even grid
                if (emptyRow % 2 == 1) {
                    invCount % 2 == 0 // Odd row from the bottom, inversion count must be even
                } else {
                    invCount % 2 == 1 // Even row from the bottom, inversion count must be odd
                }
            }
        }

        // Utility function to count inversions in the given array
        private fun getInvCount(arr: List<Int>): Int {
            var invCount = 0
            for (i in arr.indices) {
                for (j in i + 1 until arr.size) {
                    if (arr[j] != EMPTY && arr[i] != EMPTY && arr[i] > arr[j]) {
                        invCount++
                    }
                }
            }
            return invCount
        }

        // Find the position of the empty space (16) from the bottom
        private fun findEmptyPosition(puzzle: Array<IntArray>): Int {
            for (i in DIM - 1 downTo 0) {
                for (j in DIM - 1 downTo 0) {
                    if (puzzle[i][j] == EMPTY) {
                        return DIM - i // Return the row position from the bottom (1-based)
                    }
                }
            }
            return -1
        }
    }
}