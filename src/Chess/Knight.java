package Chess;

import java.awt.*;

public class Knight extends ChessPiece {

    Knight(String team) {
        super(team);
    }

    @Override
    protected void findMoveSet(Point loc, ChessBoard board) {

    }

    @Override
    public  String toString() {
        return getTeam().substring(0,1).toLowerCase() + 'N';
    }
}
