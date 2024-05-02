package solutions;

import java.util.ArrayList;
import java.util.List;

import static solutions.ConnectFourGame.*;

class ConnectFourBoard {
    private final int[][] board;
    private int currentPlayer;

    public ConnectFourBoard() {
        this.board = new int[6][7];
        this.currentPlayer = PLAYER1;
    }

    public boolean isValidMove(int column) {
        return board[0][column] == EMPTY_SLOT;
    }

    public List<Integer> getValidMoves() {
        List<Integer> validMoves = new ArrayList<>();
        for (int col = 0; col < COLUMNS; col++) {
            if (isValidMove(col)) {
                validMoves.add(col);
            }
        }
        return validMoves;
    }
    public boolean isColumnFull(int column) {
        return board[0][column] != EMPTY_SLOT; // If the top slot is not empty, the column is full
    }


    public void markColumnFull(int column) {
        // Find the lowest empty row in the column and mark it as full
        for (int row = 0; row < 6; row++) {
            if (board[row][column] == EMPTY_SLOT) {
                board[row][column] = currentPlayer; // Mark it as the current player's disc
                break;
            }
        }
    }



    public ConnectFourBoard dropDisc(int column, int player) {
        int row;
        System.out.println("Dropping disc in column: " + column);
        for (row = 0; row < 6; row++) {
            if (board[row][column] == EMPTY_SLOT) {
                break;
            }
        }
        if (row == 0) {
            // Column is full, return the same board
            return this;
        }

        // Place the disc on top of existing ones in the column
        board[row - 1][column] = player;
        currentPlayer = (player == PLAYER1) ? PLAYER2 : PLAYER1; // Update current player
        return this;
    }

    public void removeDisc(int column) {
        for (int row = 0; row < 6; row++) {
            if (board[row][column] != EMPTY_SLOT) {
                board[row][column] = EMPTY_SLOT;
                break;
            }
        }
        currentPlayer = 3 - currentPlayer; // Switch player back
    }

    public int get(int row, int column) {
        return board[row][column];
    }

    public boolean isFull() {
        boolean full = true;
        for (int row = 0; row < 6; row++) {
            for (int col = 0; col < 7; col++) {
                if (board[row][col] == EMPTY_SLOT) {
                    full = false;
                    break;
                }
            }
        }
        System.out.println("Board is full: " + full);
        return full;
    }

    public int getCurrentPlayer() {
        System.out.println(currentPlayer);
        return currentPlayer;
    }

    public void updateCurrentPlayer() {
        currentPlayer = (currentPlayer == PLAYER1) ? PLAYER2 : PLAYER1; // Switch player
    }


}
