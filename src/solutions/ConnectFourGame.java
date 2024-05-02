package solutions;

import problems.Game;

import java.util.List;
import java.util.ArrayList;

public class ConnectFourGame implements Game<ConnectFourBoard, Integer> {
    static final int ROWS = 6;
    public static final int COLUMNS = 7;
    public static final int WINNING_LENGTH = 4;
    public static final int EMPTY_SLOT = 0;
    static final int PLAYER1 = 1;
    static final int PLAYER2 = 2;

    @Override
    public boolean isTerminal(ConnectFourBoard state) {
        boolean terminal = state.isFull() || checkWinner(state, PLAYER1) || checkWinner(state, PLAYER2);
        System.out.println("Terminal state detected: " + terminal);
        return terminal;
    }

    @Override
    public int utility(ConnectFourBoard state) {
        int utility = 0;
        if (checkWinner(state, PLAYER1)) {
            utility = 100;
            System.out.println("Utility: Player 1 wins!");
        } else if (checkWinner(state, PLAYER2)) {
            utility = -100;
            System.out.println("Utility: AI wins!");
        } else {
            int score = 0;
            // Evaluation of winning potential and favorable situations
            // Omitted for brevity
            utility = score;
            System.out.println("Utility: " + utility);
        }
        return utility;
    }

    @Override
    public List<Integer> actions(ConnectFourBoard state) {
        List<Integer> validMoves = new ArrayList<>();
        for (int col = 0; col < ConnectFourGame.COLUMNS; col++) {
            if (state.isValidMove(col)) {
                validMoves.add(col);
            }
        }
        System.out.println("Valid moves: " + validMoves);
        return validMoves;
    }
    @Override
    public ConnectFourBoard execute(Integer action, ConnectFourBoard state) {
        System.out.println("Executing move in column: " + action);
        state = state.dropDisc(action, state.getCurrentPlayer());
        // Mark the column as full to prevent further moves
        state.markColumnFull(action);
        // Update the current player
        state.updateCurrentPlayer(); // Update the method call
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


    private int evaluateHorizontal(ConnectFourBoard board, int player) {
        int score = 0;
        for (int row = 0; row < ROWS; row++) {
            for (int col = 0; col <= COLUMNS - WINNING_LENGTH; col++) {
                int playerCount = 0;
                int opponentCount = 0;
                for (int k = 0; k < WINNING_LENGTH; k++) {
                    if (board.get(row, col + k) == player) {
                        playerCount++;
                    } else if (board.get(row, col + k) != EMPTY_SLOT) {
                        opponentCount++;
                    }
                }
                if (playerCount == WINNING_LENGTH) {
                    score += 100; // A winning potential
                } else if (playerCount > 0 && opponentCount == 0) {
                    score += playerCount * playerCount; // Favorable situation for player
                }
            }
        }
        return score;
    }

    private int evaluateVertical(ConnectFourBoard board, int player) {
        int score = 0;
        for (int col = 0; col < COLUMNS; col++) {
            for (int row = 0; row <= ROWS - WINNING_LENGTH; row++) {
                int playerCount = 0;
                int opponentCount = 0;
                for (int k = 0; k < WINNING_LENGTH; k++) {
                    if (board.get(row + k, col) == player) {
                        playerCount++;
                    } else if (board.get(row + k, col) != EMPTY_SLOT) {
                        opponentCount++;
                    }
                }
                if (playerCount == WINNING_LENGTH) {
                    score += 100; // A winning potential
                } else if (playerCount > 0 && opponentCount == 0) {
                    score += playerCount * playerCount; // Favorable situation for player
                }
            }
        }
        return score;
    }

    private int evaluateDiagonal(ConnectFourBoard board, int player) {
        int score = 0;
        // Down-right direction
        for (int row = 0; row <= ROWS - WINNING_LENGTH; row++) {
            for (int col = 0; col <= COLUMNS - WINNING_LENGTH; col++) {
                int playerCount = 0;
                int opponentCount = 0;
                for (int k = 0; k < WINNING_LENGTH; k++) {
                    if (board.get(row + k, col + k) == player) {
                        playerCount++;
                    } else if (board.get(row + k, col + k) != EMPTY_SLOT) {
                        opponentCount++;
                    }
                }
                if (playerCount == WINNING_LENGTH) {
                    score += 100; // A winning potential
                } else if (playerCount > 0 && opponentCount == 0) {
                    score += playerCount * playerCount; // Favorable situation for player
                }
            }
        }

        // Up-right direction
        for (int row = WINNING_LENGTH - 1; row < ROWS; row++) {
            for (int col = 0; col <= COLUMNS - WINNING_LENGTH; col++) {
                int playerCount = 0;
                int opponentCount = 0;
                for (int k = 0; k < WINNING_LENGTH; k++) {
                    if (board.get(row - k, col + k) == player) {
                        playerCount++;
                    } else if (board.get(row - k, col + k) != EMPTY_SLOT) {
                        opponentCount++;
                    }
                }
                if (playerCount == WINNING_LENGTH) {
                    score += 100; // A winning potential
                } else if (playerCount > 0 && opponentCount == 0) {
                    score += playerCount * playerCount; // Favorable situation for player
                }
            }
        }
        return score;
    }

}



