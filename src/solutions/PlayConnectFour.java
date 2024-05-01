package solutions;

import java.util.Scanner;
import java.util.List;

public class PlayConnectFour {
    public static void main(String[] args) {
        ConnectFourBoard board = new ConnectFourBoard();
        ConnectFourGame game = new ConnectFourGame();
        Scanner scanner = new Scanner(System.in);
        AIPlayer aiPlayer = new AIPlayer();

        System.out.println("Welcome to Connect Four!");

        while (!game.isTerminal(board)) {
            // Game loop content remains the same
            System.out.println(boardToString(board));
            int currentPlayer = board.getCurrentPlayer();
            System.out.println("Player " + currentPlayer + "'s turn");

            List<Integer> validMoves = game.actions(board);
            System.out.println("Valid moves: " + validMoves);

            int column;
            if (currentPlayer == 1) {
                while (true) {
                    System.out.print("Enter column to drop disc (0-6): ");
                    column = scanner.nextInt();
                    System.out.println(currentPlayer);
                    if (validMoves.contains(column)) {
                        break;
                    }
                    System.out.println("Invalid move! Please try again.");
                }
            } else { // AI's turn
                column = aiPlayer.makeMove(board);
            }

            board = game.execute(column, board);
        }

        System.out.println(boardToString(board));
        int utility = game.utility(board);
        if (utility > 0) {
            System.out.println("Player 1 wins!");
        } else if (utility < 0) {
            System.out.println("Player 2 wins!");
        } else {
            System.out.println("It's a draw!");
        }
    }

    private static String boardToString(ConnectFourBoard board) {
        StringBuilder sb = new StringBuilder();
        for (int row = 0; row < 6; row++) {
            for (int col = 0; col < 7; col++) {
                sb.append(board.get(row, col) == 0 ? "- " : board.get(row, col) + " ");
            }
            sb.append("\n");
        }
        return sb.toString();
    }
}

