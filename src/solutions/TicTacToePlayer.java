package solutions;

import core_algorithms.Minimax;
import problems.Game;
import problems.TicTacToe;

import java.util.Random;
import java.util.Scanner;

public class TicTacToePlayer {
    public static void main(String[] args) {
        int boardSize = 4;
        Game<char[][], int[]> ticTacToeGame = new TicTacToe(boardSize, TicTacToe.Marks.X);
        Minimax<char[][], int[]> minimax = new Minimax<>(ticTacToeGame, true, 5);
        char[][] board = new char[boardSize][boardSize];
        boolean isHumanTurn = true;

        Scanner scanner = new Scanner(System.in);
        Random random = new Random();

        while (!ticTacToeGame.isTerminal(board)) {
            printBoard(board);

            if (isHumanTurn) {
                System.out.println("Enter row number (1-" + boardSize + "):");
                int row = scanner.nextInt() - 1;
                System.out.println("Enter column number (1-" + boardSize + "):");
                int column = scanner.nextInt() - 1;

                if (isValidMove(row, column, board)) {
                    int[] position = {row, column};
                    board = ticTacToeGame.execute(position, board);
                    if (ticTacToeGame.utility(board) == 1) {
                        System.out.println("Human player wins!");
                        break;
                    }
                    isHumanTurn = false;
                } else {
                    System.out.println("That position is already occupied or out of range. Please try again.");
                }
            } else {
                boolean moveMade = false;
                for (int[] move : ticTacToeGame.actions(board)) {
                    if (!(board[move[0]][move[1]] == ' ')) {
                        char[][] newBoard = ticTacToeGame.execute(move, board);
                        if (ticTacToeGame.utility(newBoard) == 0) { // Check if the move doesn't lead to a loss for AI
                            board = newBoard;
                            moveMade = true;
                            break;
                        }
                    }
                }
                if (!moveMade) {
                    int row, column;
                    do {
                        row = random.nextInt(boardSize);
                        column = random.nextInt(boardSize);
                    } while (!isValidMove(row, column, board));

                    int[] position = {row, column};
                    board = ticTacToeGame.execute(position, board);
                }
                if (ticTacToeGame.utility(board) == -1) {
                    System.out.println("AI player wins!");
                    break;
                }
                isHumanTurn = true;
            }

        }

        printBoard(board);
        int utility = ticTacToeGame.utility(board);
        if (utility == 0) {
            System.out.println("It's a draw!");
        }
    }

    private static boolean isValidMove(int row, int column, char[][] board) {
        int size = board.length;
        return row >= 0 && row < size && column >= 0 && column < size && (board[row][column] != 'X' && board[row][column] != 'O');
    }

    private static void printBoard(char[][] board) {
        for (char[] row : board) {
            for (char cell : row) {
                System.out.print(cell + " ");
            }
            System.out.println();
        }
        System.out.println();
    }
}
