package solutions;

import problems.Game;

import java.util.List;
import java.util.ArrayList;

import static solutions.ConnectFourGame.EMPTY_SLOT;

public class ConnectFourGame implements Game<ConnectFourBoard, Integer> {
    private static final int ROWS = 6;
    public static final int COLUMNS = 7;
    private static final int WINNING_LENGTH = 6;
    public static final int EMPTY_SLOT = 0;
    private static final int PLAYER1 = 1;
    private static final int PLAYER2 = 2;

    @Override
    public boolean isTerminal(ConnectFourBoard state) {
        return state.isFull() || checkWinner(state, PLAYER1) || checkWinner(state, PLAYER2);
    }

    @Override
    public int utility(ConnectFourBoard state) {
        if (checkWinner(state, PLAYER1)) {
            return 100;
        } else if (checkWinner(state, PLAYER2)) {
            return -100;
        } else {
            return 0; // Draw
        }
    }

    @Override
    public List<Integer> actions(ConnectFourBoard state) {
        List<Integer> validMoves = new ArrayList<>();
        for (int col = 0; col < ConnectFourGame.COLUMNS; col++) {
            validMoves.add(col);
        }
        return validMoves;
    }

    @Override
    public ConnectFourBoard execute(Integer action, ConnectFourBoard state) {
        state = state.dropDisc(action, state.getCurrentPlayer());
        // Mark the column as full to prevent further moves
        state.markColumnFull(action);
        // Update the current player
        state.updateCurrentPlayer();
        return state;
    }

    @Override
    public ConnectFourBoard undo(Integer action, ConnectFourBoard state) {
        state.removeDisc(action);
        return state;
    }

    private boolean checkWinner(ConnectFourBoard board, int player) {
        // Check horizontal
        for (int row = 0; row < ROWS; row++) {
            for (int col = 0; col <= COLUMNS - WINNING_LENGTH; col++) {
                boolean found = true;
                for (int k = 0; k < WINNING_LENGTH; k++) {
                    if (board.get(row, col + k) != player) {
                        found = false;
                        break;
                    }
                }
                if (found) {
                    return true;
                }
            }
        }
        // Check vertical
        for (int col = 0; col < COLUMNS; col++) {
            for (int row = 0; row <= ROWS - WINNING_LENGTH; row++) {
                boolean found = true;
                for (int k = 0; k < WINNING_LENGTH; k++) {
                    if (board.get(row + k, col) != player) {
                        found = false;
                        break;
                    }
                }
                if (found) {
                    return true;
                }
            }
        }
        // Check diagonal (down-right)
        for (int row = 0; row <= ROWS - WINNING_LENGTH; row++) {
            for (int col = 0; col <= COLUMNS - WINNING_LENGTH; col++) {
                boolean found = true;
                for (int k = 0; k < WINNING_LENGTH; k++) {
                    if (board.get(row + k, col + k) != player) {
                        found = false;
                        break;
                    }
                }
                if (found) {
                    return true;
                }
            }
        }
        // Check diagonal (up-right)
        for (int row = WINNING_LENGTH - 1; row < ROWS; row++) {
            for (int col = 0; col <= COLUMNS - WINNING_LENGTH; col++) {
                boolean found = true;
                for (int k = 0; k < WINNING_LENGTH; k++) {
                    if (board.get(row - k, col + k) != player) {
                        found = false;
                        break;
                    }
                }
                if (found) {
                    return true;
                }
            }
        }
        return false;
    }
}

class ConnectFourBoard {
    private final int[][] board;
    private int currentPlayer;

    public ConnectFourBoard() {
        this.board = new int[6][7];
        this.currentPlayer = 1;
    }

    public boolean isValidMove(int column) {
        return board[0][column] == EMPTY_SLOT;
    }

    public List<Integer> getValidMoves() {
        List<Integer> validMoves = new ArrayList<>();
        for (int col = 0; col < ConnectFourGame.COLUMNS; col++) {
            if (isValidMove(col)) {
                validMoves.add(col);
            }
        }
        return validMoves;
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
        for (row = 0; row < 6; row++) {
            if (board[row][column] == EMPTY_SLOT) {
                break;
            }
        }
        if (row == 0) {
            // Column is full, return the same board
            return this;
        }

        ConnectFourBoard newBoard = new ConnectFourBoard();
        newBoard.currentPlayer = player;
        // Copy the existing board state
        for (int r = 0; r < 6; r++) {
            for (int c = 0; c < 7; c++) {
                newBoard.board[r][c] = board[r][c];
            }
        }
        // Place the disc on top of existing ones in the column
        newBoard.board[row - 1][column] = player;
        return newBoard;
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
        for (int row = 0; row < 6; row++) {
            for (int col = 0; col < 7; col++) {
                if (board[row][col] == EMPTY_SLOT) {
                    return false;
                }
            }
        }
        return true;
    }

    public int getCurrentPlayer() {
        return currentPlayer;
    }

    public void updateCurrentPlayer() {
        currentPlayer = 3 - currentPlayer; // Switch player
    }
}

