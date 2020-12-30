package Chess;

import java.awt.*;
import java.util.ArrayList;

public class Pawn extends ChessPiece {

    private boolean isFirstMove;
    private boolean advanceUp;

    Pawn(String team) {
        super(team);
        isFirstMove = true;
    }



    @Override
    protected void findMoveSet(Point loc, ChessBoard board) {
        //see what team its on
        //see if this is its first move


        if(isFirstMove) {
            //see what direction the pawn will advance in
            advanceUp = loc.y > board.getBoardWidth()/2;

        }

        // TODO refactor this section to copy less code;
        if(advanceUp) {
            //add right in front of the pawn
            Point forward1 = new Point(loc.x, loc.y - 1);

            if(isOnBoard(forward1, board)){
                if(isEmpty(forward1, board)) {
                    moveSet.add(forward1);
                }
            }


            //add second movement if this is the first move
            Point forward2 = new Point(loc.x, loc.y - 2);
            if(isOnBoard(forward2, board)) {
                if(isFirstMove && isEmpty(forward2, board)){
                    moveSet.add(forward2);

                }
            }

            //add diagonals if they contain enemies
            Point upLeft = new Point(loc.x - 1, loc.y - 1);
            Point upRight = new Point(loc.x + 1, loc.y - 1);

            if(isOnBoard(upLeft, board)) {
                if(!isEmpty(upLeft, board) && isEnemy(upLeft, board)) {
                    moveSet.add(upLeft);
                }
            }

            if(isOnBoard(upRight, board)){
                if(!isEmpty(upRight, board) && isEnemy(upRight, board)) {
                    moveSet.add(upRight);
                }
            }

        } else {
            //add right in front of the pawn
            Point forward1 = new Point(loc.x, loc.y + 1);

            if(isOnBoard(forward1, board) ) {
                if(isEmpty(forward1, board)) {
                    moveSet.add(forward1);
                }
            }


            //add second movement if this is the first move
            Point forward2 = new Point(loc.x, loc.y + 2);

            if(isOnBoard(forward2, board)) {
                if(isFirstMove && isEmpty(forward2, board)){
                    moveSet.add(forward2);

                }
            }


            //add diagonals if they contain enemies
            Point upLeft = new Point(loc.x - 1, loc.y + 1);
            Point upRight = new Point(loc.x + 1, loc.y + 1);

            if(isOnBoard(upLeft, board)) {
                if(!isEmpty(upLeft, board) && isEnemy(upLeft, board)) {
                    moveSet.add(upLeft);
                }
            }

            if(isOnBoard(upRight, board)){
                if(!isEmpty(upRight, board) && isEnemy(upRight, board)) {
                    moveSet.add(upRight);
                }
            }
        }
    }


    public void setFirstMove(boolean firstMove) {
        isFirstMove = firstMove;
    }
}
