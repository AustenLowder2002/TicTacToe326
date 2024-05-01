package solutions;

import problems.Game;

import java.util.List;
import java.util.ArrayList;

import static solutions.ConnectFourGame.EMPTY_SLOT;

public class ConnectFourGame implements Game<ConnectFourBoard, Integer> {
    private static final int ROWS = 6;
    public static final int COLUMNS = 7;
    private static final int WINNING_LENGTH = 4;
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
        return state.getValidMoves();
    }

    @Override
    public ConnectFourBoard execute(Integer action, ConnectFourBoard state) {
        return state.dropDisc(action, state.getCurrentPlayer());
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

    public ConnectFourBoard dropDisc(int column, int player) {
        ConnectFourBoard newBoard = new ConnectFourBoard();
        for (int row = 5; row >= 0; row--) {
            if (board[row][column] == EMPTY_SLOT) {
                newBoard.board[row][column] = player;
                break;
            }
        }
        newBoard.currentPlayer = 3 - player; // Switch player
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
}

