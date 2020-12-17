package Chess;

import java.awt.*;

public class Rook extends ChessPiece {

    Rook(String team) {
        super(team);
    }

    @Override
    protected void findMoveSet(Point loc, ChessBoard board) {
        // left
        int x = loc.x - 1;
        int y = loc.y;

        while(x >= 0 && x < board.getBoard().length) {

            ChessPiece cur = board.getBoard()[x][y];

            if(cur instanceof Dummy) {
                moveSet.add(new Point(x, y));
            } else if (this.getTeam().equals(cur.getTeam())) {
                break;
            } else {
                moveSet.add(new Point(x,y));
                break;
            }

            x--;
        }

        //right
        x = loc.x + 1;
        y = loc.y;

        while(x >= 0 && x < board.getBoard().length) {

            ChessPiece cur = board.getBoard()[x][y];

            if(cur instanceof Dummy) {
                moveSet.add(new Point(x, y));
            } else if (this.getTeam().equals(cur.getTeam())) {
                break;
            } else {
                moveSet.add(new Point(x,y));
                break;
            }

            x++;
        }

        //up

        x = loc.x;
        y = loc.y - 1;

        while(y >= 0 && y < board.getBoard()[0].length) {

            ChessPiece cur = board.getBoard()[x][y];

            if(cur instanceof Dummy) {
                moveSet.add(new Point(x, y));
            } else if (this.getTeam().equals(cur.getTeam())) {
                break;
            } else {
                moveSet.add(new Point(x,y));
                break;
            }

            y--;
        }

        //down

        x = loc.x;
        y = loc.y + 1;

        while(y >= 0 && y < board.getBoard()[0].length) {

            ChessPiece cur = board.getBoard()[x][y];

            if(cur instanceof Dummy) {
                moveSet.add(new Point(x, y));
            } else if (this.getTeam().equals(cur.getTeam())) {
                break;
            } else {
                moveSet.add(new Point(x,y));
                break;
            }

            y++;
        }
    }
}
