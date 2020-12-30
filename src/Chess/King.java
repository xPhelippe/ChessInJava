package Chess;

import java.awt.*;

public class King extends ChessPiece {

    King(String team) {
        super(team);
    }

    @Override
    protected void findMoveSet(Point loc, ChessBoard board) {

        Point possibleMoves[] = {
                new Point(loc.x - 1, loc.y - 1), // top left
                new Point( loc.x, loc.y -1), // top middle
                new Point(loc.x + 1, loc.y - 1), // top right
                new Point(loc.x - 1, loc.y), // middle left
                new Point(loc.x + 1, loc.y), // middle right
                new Point( loc.x - 1, loc.y + 1),// bottom left
                new Point( loc.x, loc.y + 1), // bottom middle
                new Point( loc.x + 1, loc.y + 1) //bottom right
        };

        for(Point move: possibleMoves){
            if(isOnBoard(move, board)){
                if(isEmptyorEnemy(move,board)){
                    moveSet.add(move);
                }
            }
        }
    }

    private boolean isEmptyorEnemy(Point loc, ChessBoard board) {
        //if the piece is a dummy (blank) or an enemy piece then return true
        return isEmpty(loc, board) || isEnemy(loc, board);
    }
}
