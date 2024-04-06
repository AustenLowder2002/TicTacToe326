package problems;

import java.util.ArrayList;
import java.util.List;

public class TicTacToe implements Game<char[][], int[]>{

    // e.g., 3 means a 3x3 board
    private final int BOARD_SIZE;
    private final char[][] board;
    private final boolean[][] marked;
    private Marks turn;

    public enum Marks{X,O}

    public TicTacToe(int BOARD_SIZE, Marks turn) {
        this.BOARD_SIZE = BOARD_SIZE;
        this.turn = turn;
        this.board = new char[BOARD_SIZE][BOARD_SIZE];
        this.marked = new boolean[BOARD_SIZE][BOARD_SIZE];
        for(int row=0; row<BOARD_SIZE; row++){
            for(int column=0; column<BOARD_SIZE;column++){
                board[row][column] = ' ';
                marked[row][column] = false;
            }
        }
    }

    public boolean isTerminal(char[][] board){
        int utility = utility(board);
        if(utility == 1 || utility == -1){
            return true;
        }
        for(int row=0; row<BOARD_SIZE; row++){
            for(int column=0; column<BOARD_SIZE; column++){
                if(!marked[row][column]){
                    return false;
                }
            }
        }
        return true;
    }

    public char[][] execute(int[] position, char[][] board){
        if(turn == Marks.X){
            board[position[0]][position[1]] =
                    Marks.X.toString().charAt(0);
        }else{
            board[position[0]][position[1]] =
                    Marks.O.toString().charAt(0);
        }
        marked[position[0]][position[1]] = true;
        switchTurn();
        return board;
    }

    public char[][] undo(int[] position, char[][] board){
        board[position[0]][position[1]] = ' ';
        marked[position[0]][position[1]] = false;
        switchTurn();
        return board;
    }

    public void switchTurn(){
        if(turn == Marks.X){
            turn = Marks.O;
        }else{
            turn = Marks.X;
        }
    }

    public int utility(char[][] board){
        //check rows
        for(int row=0; row<BOARD_SIZE; row++) {
            int rowSum = 0;
            for (int column = 0; column < BOARD_SIZE; column++) {
                if (board[row][column] == Marks.X.toString().charAt(0)) {
                    rowSum++;
                } else if (board[row][column] == Marks.O.toString().charAt(0)) {
                    rowSum--;
                }
            }
            if (rowSum == BOARD_SIZE) {
                return 1;
            } else if (rowSum == 0 - BOARD_SIZE) {
                return -1;
            }
        }

        //check columns
        for(int column=0; column<BOARD_SIZE; column++) {
            int colSum = 0;
            for (int row = 0; row < BOARD_SIZE; row++) {
                if (board[row][column] == Marks.X.toString().charAt(0)) {
                    colSum++;
                } else if (board[row][column] == Marks.O.toString().charAt(0)) {
                    colSum--;
                }
            }
            if (colSum == BOARD_SIZE) {
                return 1;
            } else if (colSum == 0 - BOARD_SIZE) {
                return -1;
            }
        }

        //check diagonal -- top left to bottom right
        int diaSum = 0;
        for(int d=0; d<BOARD_SIZE; d++) {
            if (board[d][d] == Marks.X.toString().charAt(0)) {
                diaSum++;
            } else if (board[d][d] == Marks.O.toString().charAt(0)) {
                diaSum--;
            }
        }
        if (diaSum == BOARD_SIZE) {
            return 1;
        } else if (diaSum == 0 - BOARD_SIZE) {
            return -1;
        }

        //check diagonal -- top right to bottom left
        diaSum = 0;
        for(int d=0; d<BOARD_SIZE; d++){
            if(board[d][BOARD_SIZE-1-d]==Marks.X.toString().charAt(0)){
                diaSum++;
            }else if(board[d][BOARD_SIZE-1-d]==Marks.O.toString().charAt(0)){
                diaSum--;
            }
        }
        if (diaSum == BOARD_SIZE) {
            return 1;
        } else if (diaSum == 0 - BOARD_SIZE) {
            return -1;
        } else{
            return 0;
        }
    }

    public List<int[]> actions(char[][] board){
        List<int[]> results = new ArrayList<>();
        for(int row=0; row<BOARD_SIZE; row++){
            for(int column=0; column<BOARD_SIZE; column++){
                if(marked[row][column]) {
                    int[] position = new int[2];
                    position[0] = row;
                    position[1] = column;
                    results.add(position);
                }
            }
        }
        return results;
    }


}
