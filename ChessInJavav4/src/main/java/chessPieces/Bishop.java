package chessPieces;

import chessPieces.ChessPiece;
import phil.ChessBoard;

import java.awt.*;

public class Bishop extends ChessPiece {

    @Override
    public void move(ChessBoard board) {
        //Bishops can move diagonally until they run into someone

        int i = location.x; //keep track of horizontal
        int j = location.y; //keep track of vertical

        //up and right

        while(i < board.getWidth() && j >= 0 && board.getBoard()[i][j] == null){
            moveSet.add(new Point(i, j));

            i++;//move right
            j--;//move up
        }

        i = location.x;
        j = location.y;

        //up and left
        while(i >= 0 && j >= 0 && board.getBoard()[i][j] == null){
            moveSet.add(new Point(i, j));

            i--; //move left
            j--; //move up
        }

        i = location.x;
        j = location.y;

        //down and right

        while(i < board.getWidth() && j < board.getHeight() && board.getBoard()[i][j] == null){
            moveSet.add(new Point(i, j));

            i++; //move right
            j++; //move down
        }

        //down and left

        i = location.x;
        j = location.y;

        while(i >= 0 && j < board.getHeight() && board.getBoard()[i][j] == null){
            moveSet.add(new Point(i, j));

            i--; //move right
            j++; //move down
        }
    }

    @Override
    public void attack(ChessBoard board) {

    }
}
