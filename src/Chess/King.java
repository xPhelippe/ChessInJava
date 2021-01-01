package Chess;

import java.awt.*;

public class King extends ChessPiece {

    private boolean isInCheck;

    public void setInCheck(boolean inCheck) {
        isInCheck = inCheck;
    }

    public boolean isInCheck() {
        return isInCheck;
    }

    King(String team) {
        super(team);
    }

    @Override
    protected void findMoveSet(Point loc, ChessBoard board) {

        Point possibleMoves[] = {
                new Point(loc.x - 1, loc.y - 1), // top left
                new Point( loc.x, loc.y -1), // top middle
                new Point(loc.x + 1, loc.y - 1), // top right
                new Point(loc.x - 1, loc.y), // middle left
                new Point(loc.x + 1, loc.y), // middle right
                new Point( loc.x - 1, loc.y + 1),// bottom left
                new Point( loc.x, loc.y + 1), // bottom middle
                new Point( loc.x + 1, loc.y + 1) //bottom right
        };

        for(Point move: possibleMoves){
            if(canKingMoveto(loc, move, board)){
                moveSet.add(move);
            }
        }
    }

    /*
        returns the size of the move set for the king
     */
    public int sizeOfMoveSet(Point loc, ChessBoard board) {
        findMoveSet(loc, board);

        return moveSet.size();

    }

    /*
        checks the king of a specific team to see if it can safely move to the specified point.
     */
    private boolean canKingMoveto(Point start, Point end, ChessBoard board) {
        boolean canSafelyMove = false;

        // see if the king can move to the specified location
        if(board.isOnBoard(end)) {
            // need to store piece at end
            ChessPiece temp = board.getPieceAt(end);

            // if chess piece is on the same team as the king, then return false
            if(temp.getTeam().equalsIgnoreCase(team)) {
                return false;
            }

            // move king to that spot
            board.setPieceAt(end, board.getPieceAt(start));
            board.setPieceAt(start, new Dummy());

            // see if king is in danger if he moves there...
            canSafelyMove =
                    !canBishoporQueenKill(end, board) &&
                            !canKingKill(end, board) &&
                            !canKnightKill(end, board) &&
                            !canPawnKill(end, board) &&
                            !canRookorQueenKill(end, board);

            // move king back to its original spot
            board.setPieceAt(start, board.getPieceAt(end));
            board.setPieceAt(end, temp);
        }
        return canSafelyMove;
    }

    /*
    checks to see if the diagonals around a point contain an enemy queen or a bishop
     */
    private boolean canBishoporQueenKill(Point loc, ChessBoard board){
        boolean containsBishoporQueen = false;

        //check all digaonals to see if they contain a bishop or a queen
        // check top left diagonal

        int x = loc.x - 1;
        int y = loc.y - 1;
        while((x >= 0 && x < board.getBoardLength()) && (y >=0 && y < board.getBoardWidth())) {

            ChessPiece cur = board.getPieceAt(new Point(x,y));

            if( cur instanceof Dummy) {
                //if space is empty, continue to next location

            } else if (cur.getTeam().equals(this.team)) {
                // if space contains team member, break
                break;
            } else if ( cur instanceof Queen || cur instanceof Bishop){
                // if space contains enemy, set to true, and then break
                containsBishoporQueen = true;
                break;
            } else {
                // if enemy piece is not queen or bishop, then we can move on to next diagonal
                break;
            }

            x--;
            y--;

        }

        if(containsBishoporQueen) return true;


        // check top right diagonal

        x = loc.x + 1;
        y = loc.y - 1;
        while((x >= 0 && x < board.getBoardLength()) && (y >=0 && y < board.getBoardWidth())) {

            ChessPiece cur = board.getPieceAt(new Point(x,y));

            if( cur instanceof Dummy) {
                //if space is empty, continue to next location

            } else if (cur.getTeam().equals(team)) {
                // if space contains team member, break
                break;
            } else if ( cur instanceof Queen || cur instanceof Bishop){
                // if space contains enemy, set to true, and then break
                containsBishoporQueen = true;
                break;
            } else {
                // if enemy piece is not queen or bishop, then we can move on to next diagonal
                break;
            }

            x++;
            y--;

        }

        if(containsBishoporQueen) return true;

        // check bottom left diagonal

        x = loc.x - 1;
        y = loc.y + 1;
        while((x >= 0 && x < board.getBoardLength()) && (y >=0 && y < board.getBoardWidth())) {

            ChessPiece cur = board.getPieceAt(new Point(x,y));

            if( cur instanceof Dummy) {
                //if space is empty, continue to next location

            } else if (cur.getTeam().equals(team)) {
                // if space contains team member, break
                break;
            } else if ( cur instanceof Queen || cur instanceof Bishop){
                // if space contains enemy, set to true, and then break
                containsBishoporQueen = true;
                break;
            } else {
                // if enemy piece is not queen or bishop, then we can move on to next diagonal
                break;
            }

            x--;
            y++;

        }

        if(containsBishoporQueen) return true;

        //check bottom right diagonal

        x = loc.x + 1;
        y = loc.y + 1;
        while((x >= 0 && x < board.getBoardLength()) && (y >=0 && y < board.getBoardWidth())) {

            ChessPiece cur = board.getPieceAt(new Point(x,y));

            if( cur instanceof Dummy) {
                //if space is empty, continue to next location

            } else if (cur.getTeam().equals(team)) {
                // if space contains team member, break
                break;
            } else if ( cur instanceof Queen || cur instanceof Bishop){
                // if space contains enemy, set to true, and then break
                containsBishoporQueen = true;
                break;
            } else {
                // if enemy piece is not queen or bishop, then we can move on to next diagonal
                break;
            }

            x++;
            y++;

        }

        return containsBishoporQueen;
    }

    /*
        checks to see if the verticals and horizontals contain an enemy rook or a queen
     */
    private boolean canRookorQueenKill(Point loc, ChessBoard board ) {

        boolean containsRookorQueen = false;

        // left of piece
        int x = loc.x - 1;
        int y = loc.y;

        while(x >= 0 && x < board.getBoardLength()) {

            ChessPiece cur = board.getPieceAt(new Point(x,y));

            if( cur instanceof Dummy) {
                //if space is empty, continue to next location

            } else if (cur.getTeam().equals(team)) {
                // if space contains team member, break
                break;
            } else if ( cur instanceof Rook || cur instanceof Queen){
                // if space contains enemy, set to true, and then break
                containsRookorQueen = true;
                break;
            } else {
                // if enemy piece is not queen or bishop, then we can move on to next part
                break;
            }

            x--;
        }

        if(containsRookorQueen) return true;

        //right of piece
        x = loc.x + 1;
        y = loc.y;

        while(x >= 0 && x < board.getBoardLength()) {

            ChessPiece cur = board.getPieceAt(new Point(x,y));

            if( cur instanceof Dummy) {
                //if space is empty, continue to next location

            } else if (cur.getTeam().equals(team)) {
                // if space contains team member, break
                break;
            } else if ( cur instanceof Rook || cur instanceof Queen){
                // if space contains enemy, set to true, and then break
                containsRookorQueen = true;
                break;
            } else {
                // if enemy piece is not queen or bishop, then we can move on to next part
                break;
            }

            x++;
        }

        if(containsRookorQueen) return true;

        // above piece

        x = loc.x;
        y = loc.y - 1;

        while(y >= 0 && y < board.getBoardWidth()) {

            ChessPiece cur = board.getPieceAt(new Point(x,y));

            if( cur instanceof Dummy) {
                //if space is empty, continue to next location

            } else if (cur.getTeam().equals(team)) {
                // if space contains team member, break
                break;
            } else if ( cur instanceof Rook || cur instanceof Queen){
                // if space contains enemy, set to true, and then break
                containsRookorQueen = true;
                break;
            } else {
                // if enemy piece is not queen or bishop, then we can move on to next part
                break;
            }

            y--;
        }

        if(containsRookorQueen) return true;

        // below piece

        x = loc.x;
        y = loc.y + 1;

        while(y >= 0 && y < board.getBoardWidth()) {

            ChessPiece cur = board.getPieceAt(new Point(x,y));

            if( cur instanceof Dummy) {
                //if space is empty, continue to next location

            } else if (cur.getTeam().equals(team)) {
                // if space contains team member, break
                break;
            } else if ( cur instanceof Rook || cur instanceof Queen){
                // if space contains enemy, set to true, and then break
                containsRookorQueen = true;
                break;
            } else {
                // if enemy piece is not queen or bishop, then we can move on to next part
                break;
            }

            y++;
        }

        return containsRookorQueen;
    }

    /*
        Checks to see if an enemy knight can kill the king
     */
    private boolean canKnightKill(Point loc, ChessBoard board) {
        boolean containsKnight = false;

        // create all locations that can contain a knight
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

        // if the point contains a knight and the knight is on the enemy team, then return true;
        for(Point move: possibleMoves){
            if(board.isOnBoard(move)) {
                ChessPiece cur = board.getPieceAt(move);
                if(cur instanceof Knight && !cur.getTeam().equals(team)){
                    containsKnight = true;
                    break;
                }
            }


        }

        return containsKnight;
    }

    /*
        checks to see if a pawn on the four corners of the king can kill the king
     */
    private boolean canPawnKill(Point loc, ChessBoard board) {
        boolean pawnCanKill = false;

        // create points for corners around the king
        Point[] possibleMoves = {
                new Point(loc.x - 1, loc.y - 1),
                new Point(loc.x + 1, loc.y - 1),
                new Point(loc.x + 1, loc.y + 1),
                new Point(loc.x - 1, loc.y + 1)
        };

        // if the corner contains a pawn, and that pawn can move to the location, then return true
        for (Point move: possibleMoves){
            ChessPiece cur = board.getPieceAt(move);

            if(cur instanceof Pawn) {
                if(!cur.getTeam().equalsIgnoreCase(team)) {
                    if(cur.canMove(move,loc,board)) {
                        pawnCanKill = true;
                        break;
                    }
                }

            }
        }

        return pawnCanKill;
    }

    /*
        checks to see if a king is anywhere near the current king
        assumes that the other king is on the enemy team.

        note: this function might be unecessary, but I chose to leave it in just in case
     */
    private boolean canKingKill(Point loc, ChessBoard board) {
        boolean containsKing = false;

        // create all possible locations arund the king
        Point[] possibleMoves = {
                new Point(loc.x - 1, loc.y - 1), // top left
                new Point( loc.x, loc.y -1), // top middle
                new Point(loc.x + 1, loc.y - 1), // top right
                new Point(loc.x - 1, loc.y), // middle left
                new Point(loc.x + 1, loc.y), // middle right
                new Point( loc.x - 1, loc.y + 1),// bottom left
                new Point( loc.x, loc.y + 1), // bottom middle
                new Point( loc.x + 1, loc.y + 1) //bottom right
        };

        // if the location contains a king, return true
        for(Point move: possibleMoves){
            if(board.getPieceAt(move) instanceof King) {
                containsKing = true;
                break;
            }
        }

        return containsKing;
    }

    /*
        see if the king is safe to move in its current location
     */
    public boolean isSafe(Point loc, ChessBoard board) {
        boolean canSafelyMove = false;


        // see if king is in danger where he stands...
        canSafelyMove =
                !canBishoporQueenKill(loc, board) &&
                        !canKingKill(loc, board) &&
                        !canKnightKill(loc, board) &&
                        !canPawnKill(loc, board) &&
                        !canRookorQueenKill(loc, board);


        return canSafelyMove;
    }

}
