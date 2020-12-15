package Chess;

import java.awt.*;
import java.util.Locale;
import java.util.Set;

abstract class ChessPiece {
    private String team;
    private Set<Point> moveSet;

    ChessPiece() {

    }
    ChessPiece(String team) {
        this.team = team;
    }

    abstract boolean move(Point start, Point end, int length, int width);
    abstract boolean canMove(Point start, Point end, int length, int width);

    @Override
    public String toString() {
        String team = this.team.substring(0,1).toLowerCase();
        String pieceName = this.getClass().getName().substring(6,7);

        return String.format(team + pieceName
                );
    }
}
