package solutions;

import core_algorithms.Minimax;
import problems.Game;

import java.util.List;

public class AIPlayer {
    private final Minimax<ConnectFourBoard, Integer> minimax;

    public AIPlayer(Game<ConnectFourBoard, Integer> game, boolean pruning) {
        this.minimax = new Minimax<>(game, pruning);
    }

    public int makeMove(ConnectFourBoard board) {
        List<Integer> validMoves = board.getValidMoves();
        if (validMoves.isEmpty()) {
            throw new IllegalStateException("No valid moves available.");
        }
        // Use Minimax to find the best move among valid moves
        return minimax.minimaxSearch(board);
    }
}