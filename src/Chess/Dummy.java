package Chess;

import java.awt.*;

public class Dummy extends ChessPiece {

    Dummy() {
        super("None");
    }

    @Override
    protected void findMoveSet(Point loc, ChessBoard board) {

    }

    @Override
    public String toString() {
        return "  ";
    }


}
