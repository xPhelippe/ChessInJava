package Chess;

import java.awt.*;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;

public abstract class ChessPiece {
    protected String team;
    protected Set<Point> moveSet;

    public String getTeam() {
        return team;
    }

    ChessPiece() {

    }

    ChessPiece(String team) {
        this.team = team;
        moveSet = new HashSet<>();
    }

    /*
        tests to see if a piece can move from one location to the next
     */
    public boolean canMove(Point start, Point end, ChessBoard board){
        //clear the current move set
        moveSet.clear();

        //find the spots where that piece can go based on start point
        findMoveSet(start, board);

        //see if end point is in the set
        return moveSet.contains(end);

    }

    /*
        calculates and returns the move set for a piece
     */
    public Set<Point> getMoveSet(Point loc, ChessBoard board) {
        findMoveSet(loc, board);

        return this.moveSet;
    }

    /*
        calculates the move set for a piece. This method is different for each type of piece
     */
    protected abstract void findMoveSet(Point loc, ChessBoard board);

    /*
        prints the move set of a piece on a chess board
     */
    public void printMoveSet(Point loc, ChessBoard board) {
        moveSet.clear();
        findMoveSet(loc, board);
        System.out.println("Move Set");
        for(int y = 0; y < board.getBoardWidth(); y++) {
            for(int x = 0; x < board.getBoardLength(); x++) {

                System.out.print("[");
                if(moveSet.contains(new Point(x,y))){
                    System.out.print("00");
                } else {
                    System.out.print(board.getPieceAt(new Point(x,y)));
                }
                System.out.print("]");

            }

            System.out.println();
        }

    }

    // TODO refactor isOnBoard. get rid of call here and only invoke the call through the board class
    protected boolean isOnBoard(Point loc, ChessBoard board) {
        return board.isOnBoard(loc);
    }

    // TODO bring isEmpty() and isEnemy() into parent class

    protected boolean isEmpty(Point loc, ChessBoard board) {
        return board.getPieceAt(loc) instanceof Dummy;
    }

    protected boolean isEnemy(Point loc, ChessBoard board) {
        return !(board.getPieceAt(loc).getTeam().equals(this.getTeam()));
    }

    @Override
    public String toString() {
        String team = this.team.substring(0,1).toLowerCase();
        String pieceName = this.getClass().getName().substring(6,7);

        return team + pieceName;
    }
}
