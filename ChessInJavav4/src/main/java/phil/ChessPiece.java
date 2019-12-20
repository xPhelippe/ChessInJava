package phil;

import javafx.geometry.Point2D;
import java.util.Set;
import java.awt.Point;

public abstract class ChessPiece {

    // it needs a team
    private String team;


    //it needs to move
    private Set<Point> moveSet;
    public abstract void move();

    // it needs to attack;
    private Set<Point> attackSet;
    public abstract void attack();

    //it needs a location

    private Point location;

    public String getTeam() {
        return team;
    }

    public void setTeam(String team) {
        this.team = team;
    }

}
