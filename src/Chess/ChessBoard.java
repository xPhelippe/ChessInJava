package Chess;

import java.awt.Point;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

public class ChessBoard {

    private ChessPiece [][] board;

    private HashMap<String, Point> kingLocs;

    /*
        returns the piece at a given location
     */
    public ChessPiece getPieceAt(Point loc) {
        if(isOnBoard(loc)) {
            return this.board[loc.x][loc.y];
        } else {
            return null;
        }

    }

    /*
        sets the piece at a given location
        TODO possible remove all uses of this method as only the board should be able to move pieces
     */
    public void setPieceAt(Point loc, ChessPiece piece) {
        this.board[loc.x][loc.y] = piece;
    }

    ChessBoard() {
        kingLocs = new HashMap<>();

        init();
    }

    public int getBoardLength() {
        return this.board.length;
    }

    public int getBoardWidth() {
        return this.board[0].length;
    }

    /*
        testing initializations for the board
     */
    private void testInit() {

          board = new ChessPiece[8][8];
        board[0][0] = new Dummy();
        board[1][0] = new Dummy();
        board[2][0] = new Dummy();
        board[3][0] = new Dummy();
        board[4][0] = new Dummy();
        board[5][0] = new Dummy();
        board[6][0] = new Dummy();
        board[7][0] = new Dummy();

        board[0][1] = new Queen("White");
        board[1][1] = new Dummy();
        board[2][1] = new Dummy();
        board[3][1] = new Dummy();
        board[4][1] = new Dummy();
        board[5][1] = new Dummy();
        board[6][1] = new Dummy();
        board[7][1] = new Dummy();

        board[0][2] = new Dummy();
        board[1][2] = new Dummy();
        board[2][2] = new Dummy();
        board[3][2] = new Dummy();
        board[4][2] = new Dummy();
        board[5][2] = new Dummy();
        board[6][2] = new Dummy();
        board[7][2] = new Dummy();

        board[0][3] = new Dummy();
        board[1][3] = new Dummy();
        board[2][3] = new Dummy();
        board[3][3] = new King("Black");
        board[4][3] = new Dummy();
        board[5][3] = new Dummy();
        board[6][3] = new Dummy();
        board[7][3] = new Dummy();

        board[0][4] = new Dummy();
        board[1][4] = new Dummy();
        board[2][4] = new Dummy();
        board[3][4] = new Dummy();
        board[4][4] = new Dummy();
        board[5][4] = new Dummy();
        board[6][4] = new Dummy();
        board[7][4] = new Dummy();

        board[0][5] = new Dummy();
        board[1][5] = new Dummy();
        board[2][5] = new Dummy();
        board[3][5] = new Dummy();
        board[4][5] = new Dummy();
        board[5][5] = new Dummy();
        board[6][5] = new Dummy();
        board[7][5] = new Dummy();

        board[0][6] = new Dummy();
        board[1][6] = new Dummy();
        board[2][6] = new Queen("White");
        board[3][6] = new Dummy();
        board[4][6] = new Pawn("Black");
        board[5][6] = new Dummy();
        board[6][6] = new Dummy();
        board[7][6] = new Dummy();


        board[0][7] = new Dummy();
        board[1][7] = new Dummy();
        board[2][7] = new Dummy();
        board[3][7] = new Dummy();
        board[4][7] = new Dummy();
        board[5][7] = new Dummy();
        board[6][7] = new Dummy();
        board[7][7] = new King("White");

    }

    /*
        the classing board layout for a game of chess
     */
    private void realInit() {
        board = new ChessPiece[8][8];
        board[0][0] = new Rook("White");
        board[1][0] = new Knight("White");
        board[2][0] = new Bishop("White");
        board[3][0] = new King("White");
        kingLocs.put("White", new Point(3,0));
        board[4][0] = new Queen("White");
        board[5][0] = new Bishop("White");
        board[6][0] = new Knight("White");
        board[7][0] = new Rook("White");

        board[0][1] = new Pawn("White");
        board[1][1] = new Pawn("White");
        board[2][1] = new Pawn("White");
        board[3][1] = new Pawn("White");
        board[4][1] = new Pawn("White");
        board[5][1] = new Pawn("White");
        board[6][1] = new Pawn("White");
        board[7][1] = new Pawn("White");

        board[0][2] = new Dummy();
        board[1][2] = new Dummy();
        board[2][2] = new Dummy();
        board[3][2] = new Dummy();
        board[4][2] = new Dummy();
        board[5][2] = new Dummy();
        board[6][2] = new Dummy();
        board[7][2] = new Dummy();

        board[0][3] = new Dummy();
        board[1][3] = new Dummy();
        board[2][3] = new Dummy();
        board[3][3] = new Dummy();
        board[4][3] = new Dummy();
        board[5][3] = new Dummy();
        board[6][3] = new Dummy();
        board[7][3] = new Dummy();

        board[0][4] = new Dummy();
        board[1][4] = new Dummy();
        board[2][4] = new Dummy();
        board[3][4] = new Dummy();
        board[4][4] = new Dummy();
        board[5][4] = new Dummy();
        board[6][4] = new Dummy();
        board[7][4] = new Dummy();

        board[0][5] = new Dummy();
        board[1][5] = new Dummy();
        board[2][5] = new Dummy();
        board[3][5] = new Dummy();
        board[4][5] = new Dummy();
        board[5][5] = new Dummy();
        board[6][5] = new Dummy();
        board[7][5] = new Dummy();

        board[0][6] = new Pawn("Black");
        board[1][6] = new Pawn("Black");
        board[2][6] = new Pawn("Black");
        board[3][6] = new Pawn("Black");
        board[4][6] = new Pawn("Black");
        board[5][6] = new Pawn("Black");
        board[6][6] = new Pawn("Black");
        board[7][6] = new Pawn("Black");


        board[0][7] = new Rook("Black");
        board[1][7] = new Knight("Black");
        board[2][7] = new Bishop("Black");
        board[3][7] = new King("Black");
        kingLocs.put("Black", new Point(3,7));
        board[4][7] = new Queen("Black");
        board[5][7] = new Bishop("Black");
        board[6][7] = new Knight("Black");
        board[7][7] = new Rook("Black");
    }

    /*
        function for initailizing the pieces on the board
     */
    public void init() {
        //testInit();
        realInit();

    }

    /*
        checks to see if a piece can move from one location to the next.
        Does NOT move the piece
        Does NOT account for the king being in check
     */
    public SuccessMessage canMovePiece(Point start, Point end, String team) {

        SuccessMessage successMessage = new SuccessMessage();

        // check to see if  both points are on board
        if(!isOnBoard(start)) {
            successMessage.setResult(MoveResult.FAILED);
            successMessage.setMessage("Starting point is not on board");

            return successMessage;
        }

        if(!isOnBoard(end)) {
            successMessage.setResult(MoveResult.FAILED);
            successMessage.setMessage("Ending point is not on board.");
            return successMessage;
        }

        // see if start point has a piece in it
        if(getPieceAt(start) instanceof Dummy){
            successMessage.setResult(MoveResult.FAILED);
            successMessage.setMessage("No piece selected to move");
            return successMessage;
        }

        // TODO move to chess game class
        // see if piece being selected belongs to current team
        if(!(getPieceAt(start).getTeam().equals(team))) {
            successMessage.setResult(MoveResult.FAILED);
            successMessage.setMessage("Please select a piece on the " + team + " team");
            return successMessage;
        }

        // test to see if piece can move to desired location
        boolean canItMove = this.getPieceAt(start).canMove(start, end, this);

        // print the move set of the piece (used for debugging purposes, but is also really cool)
        this.getPieceAt(start).printMoveSet(start, this);

        if(canItMove) {
            successMessage.setResult(MoveResult.SUCCESS);
            return successMessage;
        } else {
            successMessage.setResult(MoveResult.FAILED);
            successMessage.setMessage("Invalid move. Try again.");
            return successMessage;
        }

    }

    /*
        sees if a point is located on the board
     */
    public boolean isOnBoard(Point loc) {
        return (loc.x >= 0 && loc.x < this.board[0].length) && (loc.y >= 0 && loc.y < this.board.length);
    }

    /*
        tests to see if a piece can move from one point to the other and moves that piece
     */
    public SuccessMessage movePiece(Point start, Point end, String team) {

        SuccessMessage canMoveResult = canMovePiece(start, end, team);

        if(canMoveResult.getResult() == MoveResult.SUCCESS) {

            // temporary storage in case the piece needs to be swapped back
            ChessPiece temp = getPieceAt(end);

            //swap the two pieces
            setPieceAt(end, getPieceAt(start));
            setPieceAt(start, new Dummy());

            ChessPiece cur = getPieceAt(end);

            // Check to see if the team's king is in check.
            // If king is in check, see if this move saved it
            King king  =  (King) getPieceAt(kingLocs.get(cur.getTeam()));

            if(king.isInCheck() && king.isSafe(kingLocs.get(cur.getTeam()), this)) {
                // If king is safe, remove check
                king.setInCheck(false);
            } else if (king.isInCheck() && !king.isSafe(kingLocs.get(cur.getTeam()),this)){
                // If king is not safe, then move piece back and the move results in a fail
                setPieceAt(start, getPieceAt(end));
                setPieceAt(end, temp);

                canMoveResult.setResult(MoveResult.FAILED);
                canMoveResult.setMessage("King is in Check");
                return canMoveResult;
            }


            // If it is a pawn, set isFirstMove to false
            if(cur instanceof Pawn) {
                ((Pawn) cur).setFirstMove(false);
            }

            // Once the piece has been moved successfully (and the king is safe),
            // a few checks need to be made
            if(cur instanceof King) {
                // If king is moved, then update the king's location
                kingLocs.put(cur.getTeam(), end);

                // Mark king as being not in check
                ((King) cur).setInCheck(false);
            } else {
                // If the piece is not a king, see if the enemy king has been put in check
                Point enemyKingLoc;

                // Get the enemy king's location
                if(cur.getTeam().equalsIgnoreCase("white")) {
                    enemyKingLoc = kingLocs.get("Black");
                } else {
                    enemyKingLoc = kingLocs.get("White");
                }

                // If the piece can kill the king, then mark the king as being in check
                if(cur.canMove(end, enemyKingLoc, this)) {
                    ((King)getPieceAt(enemyKingLoc)).setInCheck(true);
                    canMoveResult.setResult(MoveResult.SUCCESS_W_MESSAGE);
                    canMoveResult.setMessage("King is in Check");
                }
            }

            return canMoveResult;

        } else {
            // If move is not successful, return error code
            return canMoveResult;
        }


    }

    /*
        checks to see if the king is in checkmate.
        Uses brute force algorithm.
     */
    public boolean isKingInCheckmate(String team) {

        King king = (King) getPieceAt(kingLocs.get(team));


        // first, we must see if the king can move
        int numofMoves = king.sizeOfMoveSet(kingLocs.get(team),this);

        if(numofMoves > 0) {
            return false;
        }


        // if the king cannot move, then we must see if it can be protected.
        // this is where the brute force comes in

        boolean isKinginCheckMate = true;

        for(int x = 0; x < getBoardLength(); x++) {
            for(int y = 0; y < getBoardWidth();y++){
                Point curLoc = new Point(x,y);

                ChessPiece curPiece = getPieceAt(curLoc);

                // if piece is on enemy team, continue to next piece
                if(!curPiece.getTeam().equalsIgnoreCase(king.getTeam())){
                    continue;
                }

                // since we are seeing if the king can move somewhere to get out of check mate
                // we do not want to move the king itself
                if(curPiece instanceof King) {
                    continue;
                }

                // get the move set for the piece in question
                Set<Point> curPieceMoveSet = curPiece.getMoveSet(curLoc, this);

                // used for testing
                //System.out.println("Piece being moved: " + curPiece.toString());

                // see if the king is safe when this piece is moved to a point in its moveSet
                for (Point point : curPieceMoveSet) {
                    System.out.println("got the point");
                    // move the piece to the new location
                    ChessPiece temp = getPieceAt(point);
                    board[point.x][point.y] = board[curLoc.x][curLoc.y];
                    board[curLoc.x][curLoc.y] = new Dummy();

                    // see if the king is safe when this move is made
                    boolean isKingSafe = king.isSafe(kingLocs.get(king.getTeam()), this);
                    if (isKingSafe) {
                        isKinginCheckMate = false;
                    }

                    // move pieces back
                    setPieceAt(curLoc, getPieceAt(point));
                    setPieceAt(point, temp);
                }
            }
        }

        return isKinginCheckMate;
        /*
        kings are NOT in check mate when
        - they can move out of the way
        - the attacking piece can be killed
        - the king can be blocked by another piece
         */


        // if I put the constraint that the number of pieces attacking the king is equal to 1, then
        // could checking for blocking and piece killing be easier?

        // see if that piece is in danger of being killed
        // see if any piece on the king's team can be placed in between the two pieces
        // (with exception of knight, pawn, and king)
    }


    /*
        checks to see if the king of a team is in check
     */
    public boolean isKinginCheck(String team) {
        return ((King)getPieceAt(kingLocs.get(team))).isInCheck();
    }

    /*
        print out of chess board
     */
    @Override
    public String toString() {

        //Lesson learned:
        // %s will print out the string as as is,
        // but %S will print it out all upper case
        return String.format(
                "  %3s %3s %3s %3s %3s %3s %3s %3s%n" +
                "1 [%s][%s][%s][%s][%s][%s][%s][%s]%n" +
                        "2 [%s][%s][%s][%s][%s][%s][%s][%s]%n" +
                        "3 [%s][%s][%s][%s][%s][%s][%s][%s]%n" +
                        "4 [%s][%s][%s][%s][%s][%s][%s][%s]%n" +
                        "5 [%s][%s][%s][%s][%s][%s][%s][%s]%n" +
                        "6 [%s][%s][%s][%s][%s][%s][%s][%s]%n" +
                        "7 [%s][%s][%s][%s][%s][%s][%s][%s]%n" +
                        "8 [%s][%s][%s][%s][%s][%s][%s][%s]%n",
                "A", "B", "C","D","E","F","G","H",
                board[0][0].toString(), board[1][0].toString(), board[2][0].toString(), board[3][0].toString(), board[4][0].toString(), board[5][0].toString(), board[6][0].toString(), board[7][0].toString(),
                board[0][1].toString(), board[1][1].toString(), board[2][1].toString(), board[3][1].toString(), board[4][1].toString(), board[5][1].toString(), board[6][1].toString(), board[7][1].toString(),
                board[0][2].toString(), board[1][2].toString(), board[2][2].toString(), board[3][2].toString(), board[4][2].toString(), board[5][2].toString(), board[6][2].toString(), board[7][2].toString(),
                board[0][3].toString(), board[1][3].toString(), board[2][3].toString(), board[3][3].toString(), board[4][3].toString(), board[5][3].toString(), board[6][3].toString(), board[7][3].toString(),
                board[0][4].toString(), board[1][4].toString(), board[2][4].toString(), board[3][4].toString(), board[4][4].toString(), board[5][4].toString(), board[6][4].toString(), board[7][4].toString(),
                board[0][5].toString(), board[1][5].toString(), board[2][5].toString(), board[3][5].toString(), board[4][5].toString(), board[5][5].toString(), board[6][5].toString(), board[7][5].toString(),
                board[0][6].toString(), board[1][6].toString(), board[2][6].toString(), board[3][6].toString(), board[4][6].toString(), board[5][6].toString(), board[6][6].toString(), board[7][6].toString(),
                board[0][7].toString(), board[1][7].toString(), board[2][7].toString(), board[3][7].toString(), board[4][7].toString(), board[5][7].toString(), board[6][7].toString(), board[7][7].toString()
        );
    }
}
