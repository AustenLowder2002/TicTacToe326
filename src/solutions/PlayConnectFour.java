package solutions;

import java.util.Scanner;
import java.util.List;

public class PlayConnectFour {
    public static void main(String[] args) {
        ConnectFourBoard board = new ConnectFourBoard();
        ConnectFourGame game = new ConnectFourGame();
        boolean usePruning = true; // Or false depending on your preference

        AIPlayer aiPlayer = new AIPlayer(game, usePruning);
        Scanner scanner = new Scanner(System.in);

        System.out.println("Welcome to Connect Four!");

        while (!game.isTerminal(board)) {
            System.out.println(boardToString(board));
            int currentPlayer = board.getCurrentPlayer();
            System.out.println(currentPlayer == 1 ? "Player 1's turn" : "AI's turn");

            int column;
            if (currentPlayer == 1) {
                List<Integer> validMoves = game.actions(board);
                while (true) {
                    System.out.print("Enter column to drop disc (0-6): ");
                    column = scanner.nextInt();
                    if (validMoves.contains(column)) {
                        break;
                    }
                    System.out.println("Invalid move! Please try again.");
                }
            } else { // AI's turn
                System.out.println("AI is thinking...");
                column = aiPlayer.makeMove(board);
                System.out.println("AI chose column " + column);
            }

            board = game.execute(column, board);
        }

        System.out.println(boardToString(board));
        int utility = game.utility(board);
        if (utility > 0) {
            System.out.println("Player 1 wins!");
        } else if (utility < 0) {
            System.out.println("AI wins!");
        } else {
            System.out.println("It's a draw!");
        }
    }

    private static String boardToString(ConnectFourBoard board) {
        StringBuilder sb = new StringBuilder();
        for (int row = 5; row >= 0; row--) { // Iterate in reverse order
            for (int col = 0; col < 7; col++) {
                sb.append(board.get(row, col) == 0 ? "- " : board.get(row, col) + " ");
            }
            sb.append("\n");
        }
        return sb.toString();
    }
}