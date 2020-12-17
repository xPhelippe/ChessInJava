package Chess;

import java.awt.*;

public class Knight extends ChessPiece {

    Knight(String team) {
        super(team);
    }

    @Override
    protected void findMoveSet(Point loc, ChessBoard board) {
        // for each space:
        // is it on the board?
        // is it a valid loc?

        Point possibleMoves[] = {
                new Point(loc.x - 2, loc.y - 1),
                new Point(loc.x - 1, loc.y - 2),
                new Point(loc.x + 2, loc.y - 1),
                new Point(loc.x + 1, loc.y - 2),
                new Point(loc.x - 2, loc.y + 1),
                new Point(loc.x - 1, loc.y + 2),
                new Point(loc.x + 2, loc.y + 1),
                new Point(loc.x + 1, loc.y + 2)
        };

        for(Point move: possibleMoves){
            if(isOnBoard(move, board) && isEmptyorEnemy(move, board)){
                moveSet.add(move);
            }
        }

    }

    private boolean isEmptyorEnemy(Point loc, ChessBoard board) {
        //if the piece is a dummy (blank) or an enemy piece then return true
        return board.getBoard()[loc.x][loc.y] instanceof Dummy || !(board.getBoard()[loc.x][loc.y].getTeam().equals(this.getTeam()));
    }

    @Override
    public  String toString() {
        return getTeam().substring(0,1).toLowerCase() + 'N';
    }
}
