package chessPieces;

import javafx.geometry.Point2D;
import phil.ChessBoard;

import java.util.HashSet;
import java.util.Set;
import java.awt.Point;

public abstract class ChessPiece {

    public ChessPiece() {
        moveSet = new HashSet<>();
        attackSet = new HashSet<>();
        team = "";
        location = new Point(0,0);
    }

    public ChessPiece(Point location, String team){
        moveSet = new HashSet<>();
        attackSet = new HashSet<>();
        this.team = team;
        this.location = location;
    }

    public ChessPiece(Point location){
        moveSet = new HashSet<>();
        attackSet = new HashSet<>();
        team = "";
        this.location = location;
    }

    // it needs a team
    private String team;

    //it needs to move
    protected Set<Point> moveSet;
    public abstract void move(ChessBoard board);

    // it needs to attack;
    protected Set<Point> attackSet;
    public abstract void attack(ChessBoard board);

    //it needs a location

    protected Point location;

    public String getTeam() {
        return team;
    }

    public void setTeam(String team) {
        this.team = team;
    }

    @Override
    public String toString() {
        return "B";
    }
}
