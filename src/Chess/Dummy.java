package Chess;

import java.awt.*;

public class Dummy extends ChessPiece {

    public boolean move(Point start, Point end, int length, int width){
        return false;
    }

    public boolean canMove(Point start, Point end, int length, int width){
        return false;
    }

    @Override
    public String toString() {
        return "  ";
    }

}
