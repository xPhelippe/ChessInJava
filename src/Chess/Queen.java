package Chess;

import java.awt.*;

public class Queen extends ChessPiece {

    Queen(String team) {
        super(team);
    }

    @Override
    protected void findMoveSet(Point loc, ChessBoard board) {
        findupandDownMoveSet(loc, board);
        findDiagonalMoveSet(loc, board);
    }

    private void findupandDownMoveSet(Point loc, ChessBoard board) {
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
    private void findDiagonalMoveSet(Point loc, ChessBoard board) {
        // check top left diagonal

        int x = loc.x - 1;
        int y = loc.y - 1;
        while((x >= 0 && x < board.getBoard()[0].length) && (y >=0 && y < board.getBoard().length)) {

            ChessPiece cur = board.getBoard()[x][y];

            if( cur instanceof Dummy) {//if space is empty, add to moveSet
                moveSet.add(new Point(x,y));
            } else if (cur.getTeam().equals(this.getTeam())) {// if space contains team member, break and do NOT add to move set
                break;
            } else { // if space contains enemy, add to move set, and then break
                moveSet.add(new Point(x, y));
                break;
            }

            x--;
            y--;

        }


        // check top right diagonal

        x = loc.x + 1;
        y = loc.y - 1;
        while((x >= 0 && x < board.getBoard()[0].length) && (y >=0 && y < board.getBoard().length)) {

            ChessPiece cur = board.getBoard()[x][y];

            if( cur instanceof Dummy) {//if space is empty, add to moveSet
                moveSet.add(new Point(x,y));
            } else if (cur.getTeam().equals(this.getTeam())) {// if space contains team member, break and do NOT add to move set
                break;
            } else { // if space contains enemy, add to move set, and then break
                moveSet.add(new Point(x, y));
                break;
            }

            x++;
            y--;

        }

        // check bottom left diagonal

        x = loc.x - 1;
        y = loc.y + 1;
        while((x >= 0 && x < board.getBoard()[0].length) && (y >=0 && y < board.getBoard().length)) {

            ChessPiece cur = board.getBoard()[x][y];

            if( cur instanceof Dummy) {//if space is empty, add to moveSet
                moveSet.add(new Point(x,y));
            } else if (cur.getTeam().equals(this.getTeam())) {// if space contains team member, break and do NOT add to move set
                break;
            } else { // if space contains enemy, add to move set, and then break
                moveSet.add(new Point(x, y));
                break;
            }

            x--;
            y++;

        }


        //check bottom right diagonal

        x = loc.x + 1;
        y = loc.y + 1;
        while((x >= 0 && x < board.getBoard()[0].length) && (y >=0 && y < board.getBoard().length)) {

            ChessPiece cur = board.getBoard()[x][y];

            if( cur instanceof Dummy) {//if space is empty, add to moveSet
                moveSet.add(new Point(x,y));
            } else if (cur.getTeam().equals(this.getTeam())) {// if space contains team member, break and do NOT add to move set
                break;
            } else { // if space contains enemy, add to move set, and then break
                moveSet.add(new Point(x, y));
                break;
            }

            x++;
            y++;

        }


    }
}
