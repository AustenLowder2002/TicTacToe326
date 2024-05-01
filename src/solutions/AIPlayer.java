package solutions;

import java.util.List;
import java.util.Random;

public class AIPlayer {
    public int makeMove(ConnectFourBoard board) {
        // Generate a random valid move
        List<Integer> validMoves = board.getValidMoves();
        Random random = new Random();
        return validMoves.get(random.nextInt(validMoves.size()));
    }
}
