package Chess;


import java.awt.*;
import java.util.HashSet;
import java.util.Locale;
import java.util.Scanner;
import java.util.Set;

/*
    class used for creating a game of chess.
    This class is meant to handle:
    - initializing the game
    - win conditions
    - turn management
 */
public class ChessGame {

    // board where the pieces reside
    private ChessBoard board;

    private String currentPlayer;

    // TODO refactor these into the king class
    private Point whiteKingLoc;
    private Point blackKingLoc;
    private boolean isKinginCheck;
    private String teamInCheck;
    private Set<Point> validKingMovements;

    public ChessGame() {
        board = new ChessBoard();
        whiteKingLoc = new Point(0,0);
        blackKingLoc = new Point(0,0);
        isKinginCheck = false;
        teamInCheck = "none";
        validKingMovements = new HashSet<>();
    }

    /*
        initializes the chess board and some key values
     */
    public void init() {
        //initialize chess board
        board.init();

        //set current player to white
        currentPlayer = "White";

        //initialize some key values
        isKinginCheck = false;
        teamInCheck = "none";
        validKingMovements.clear();

        //find kings on the board
        findKings();
    }

    /*
        tries to move a piece from the starting location to the ending location
     */
    public String attemptMovePiece(Point start, Point end) {

        String moveSuccess;


        //if king is in check and will still be in check after that movement, then don't allow player to move
        if(isKinginCheck && this.board.getPieceAt(start) instanceof King && !validKingMovements.contains(end) && currentPlayer.equals(teamInCheck)) {
            moveSuccess = "King is in check";
        }
        else if(isKinginCheck && !(this.board.getPieceAt(start) instanceof King)) {
            // if the king is in check and the piece being moved is not a king. don't allow the movement
            // TODO change this logic as it does not allow for pieces to move in to protect the king
            moveSuccess = "Must move King. King is in Check";
        }
        else if (!isKinginCheck && this.board.getPieceAt(start) instanceof King) {
            // if the king is not in check, see if king will put itself in check before it is moved
            boolean canmovehere = canKingMoveto(end, this.board.getPieceAt(start).getTeam());
            if(!canmovehere) {
                moveSuccess = "Move will put King in Check";
            } else {
                moveSuccess = board.movePiece(start, end, currentPlayer);
            }

        }
        else {
            //try to move piece chosen by user to new location
            moveSuccess = board.movePiece(start, end, currentPlayer);
        }

        //check and see if move was successful
        if (moveSuccess.equalsIgnoreCase("success")) {

            ChessPiece pieceMoved = this.board.getPieceAt(end);

            //if the piece is a king, then update the kings location variable
            if(pieceMoved instanceof King) {
                if(this.board.getPieceAt(end).getTeam().equalsIgnoreCase("white")){
                    whiteKingLoc = end;
                } else {
                    blackKingLoc = end;
                }

                // If the king was in check, then make it not in check.
                // We can do this without checking to see if the king was in check
                // Because the king in check can only move to locations that will make it out of check
                if(isKinginCheck && teamInCheck.equals(currentPlayer)) {
                    isKinginCheck = false;
                    teamInCheck = "none";
                    validKingMovements.clear();
                }


            } else {
                //if piece is not a king, we need to see if the enemy's king is in check now
                if (pieceMoved.getTeam().equals("White")) {

                    // see if piece can take out king on opposite team
                    isKinginCheck = pieceMoved.canMove(end,blackKingLoc, this.board);
                    if(isKinginCheck){
                        teamInCheck = "Black";
                        System.out.println("King is in Check");

                        //Set the return string to say the king is in check
                        moveSuccess = teamInCheck + "'s King is in check";
                    }

                } else {

                    // see if piece can take out king on opposite team
                    isKinginCheck = pieceMoved.canMove(end,whiteKingLoc, this.board);
                    if(isKinginCheck){
                        teamInCheck = "White";
                        System.out.println("King is in Check");

                        //Set the return string to say the king is in check
                        moveSuccess = teamInCheck + "'s King is in check";
                    }
                }

                // if piece is a pawn and the pawn has reached the opposite side of the stage
                // then I need to allow the player to replace the pawn with a new piece
                if(pieceMoved instanceof Pawn) {
                    //see if it is at its opposite end
                    if(end.y == 0 || end.y == 7) {
                        moveSuccess = "Change " + pieceMoved.getTeam() + " pawn";
                    }
                }
            }

            // if king is in check
            boolean hasWon = false;
            if(isKinginCheck) {
                hasWon = !canKingMove(teamInCheck);
            }


            // change teams
            if(currentPlayer.equals("White")) {
                currentPlayer = "Black";
            } else {
                currentPlayer = "White";
            }

            if(hasWon) {
                moveSuccess = "Contrats, team " + teamInCheck + " has won!";
            }
        }

        System.out.println(board.toString());

        return moveSuccess;



    }

    /*
        replaces the pawn of a given team with the correct piece
        this function assumes only one pawn exists in the top or bottom rows.
     */
    public void replacePawn(String replacement, String team) {

        // create new piece to replace pawn with
        ChessPiece replace = new Dummy();

        switch (replacement.toLowerCase()) {
            case "bishop" -> replace = new Bishop(team);
            case "queen" -> replace = new Queen(team);
            case "rook" -> replace = new Rook(team);
            case "knight" -> replace = new Knight(team);
        }

        // look for pawn along the top row of the board
        int y = 0;

        for(int x = 0; x < board.getBoardLength(); x++) {
            ChessPiece cur = board.getPieceAt(new Point(x, y));

            if(cur instanceof  Pawn && cur.getTeam().equalsIgnoreCase(team)) {
                board.setPieceAt(new Point(x,y), replace);
            }
        }

        // look for pawn along the bottom row of the board

        y = 7;

        for(int x = 0; x < board.getBoardLength(); x++) {
            ChessPiece cur = board.getPieceAt(new Point(x, y));

            if(cur instanceof  Pawn && cur.getTeam().equalsIgnoreCase(team)) {
                board.setPieceAt(new Point(x,y), replace);
            }
        }
    }

    /*
        return the team who's move it is
     */
    public String getCurrentPlayer() {
        return this.currentPlayer;
    }


    public ChessBoard getBoard() {
        return this.board;
    }

    /*
    function used for text based version of the game
     */
    public void play(){

        // initialize the game
        init();

        //let the game begin!
        while(true) {

            // print board to user
            System.out.println(board.toString());

            // ask user for points to move
            System.out.print("Please enter piece you want to move: ");
            Scanner sc = new Scanner(System.in);

            Point start = makePoint(sc.next());

            System.out.print("Please enter where you want to move the piece: ");

            Point end = makePoint(sc.next());

            String moveSuccess;


            //if king is in check and will still be in check after that movement, then don't allow player to move
            if(isKinginCheck && this.board.getPieceAt(start) instanceof King && !validKingMovements.contains(end) && currentPlayer.equals(teamInCheck)) {
                moveSuccess = "King is in check";
            } else if(isKinginCheck && !(this.board.getPieceAt(start) instanceof King)) {
                // if the king is in check and the piece being moved is not a king. don't allow the movement
                // TODO change this logic as it does not allow for pieces to move in to protect the king
                moveSuccess = "Must move King. King is in Check";
            } else if (!isKinginCheck && this.board.getPieceAt(start) instanceof King) {
                // if the king is not in check, see if king will put itself in check before it is moved
                boolean canmovehere = canKingMoveto(end, this.board.getPieceAt(start).getTeam());

                if(!canmovehere) {
                    moveSuccess = "Move will put King in Check";
                } else {
                    moveSuccess = board.movePiece(start, end, currentPlayer);
                }

            }  else {
                // try to move piece chosen by user to new location
                moveSuccess = board.movePiece(start, end, currentPlayer);
            }

            //check and see if move was successful
            if (moveSuccess.equalsIgnoreCase("success")) {

                ChessPiece pieceMoved = this.board.getPieceAt(end);

                //if the piece is a king, then update the kings location
                if(pieceMoved instanceof King) {
                    if(this.board.getPieceAt(end).getTeam().equalsIgnoreCase("white")){
                        whiteKingLoc = end;
                    } else {
                        blackKingLoc = end;
                    }

                    // If the king was in check, then make it not in check.
                    // We can do this without checking to see if the king was in check
                    // Because the king in check can only move to locations that will take it out of check
                    if(isKinginCheck && teamInCheck.equals(currentPlayer)) {
                        isKinginCheck = false;
                        teamInCheck = "none";
                        validKingMovements.clear();
                    }

                } else {
                    //if piece is not a king, we need to see if the enemy's king is in check now
                    if (pieceMoved.getTeam().equals("White")) {

                        // see if piece can take out king on opposite team
                        isKinginCheck = pieceMoved.canMove(end,blackKingLoc, this.board);

                        if(isKinginCheck){
                            teamInCheck = "Black";
                            System.out.println("King is in Check");
                        }

                    } else {

                        // see if piece can take out king on opposite team
                        isKinginCheck = pieceMoved.canMove(end,whiteKingLoc, this.board);

                        if(isKinginCheck){
                            teamInCheck = "White";
                            System.out.println("King is in Check");
                        }
                    }

                // if piece is a pawn and the pawn has reached the opposite side of the stage
                // then I need to allow the player to replace the pawn with a new piece
                    if(pieceMoved instanceof Pawn) {
                        //see if it is at its opposite end
                        if(end.y == 0 || end.y == 7) {
                            replacePawn(end);
                        }
                    }
                }

                // If king is in check, see if the king has a location it can move to to get it out of check.
                // If not, then the current team has won
                boolean hasWon = false;
                if(isKinginCheck) {
                    hasWon = !canKingMove(teamInCheck);
                }

                if(hasWon) {
                    System.out.println("Contratulations, team " + teamInCheck + " has won!");
                    break;
                }

                // Change teams and restart if no team has won
                if(currentPlayer.equals("White")) {
                    currentPlayer = "Black";
                } else {
                    currentPlayer = "White";
                }
            }
            else {
                // If the move was not successful, then the error string is printed
                System.out.println(moveSuccess);
            }

            System.out.println();
        }

    }


    /*
        finds the white and black king on the board.
        Assumes that the board contains one white king and one black king
     */
    private void findKings() {

        for(int x = 0; x < board.getBoardLength(); x++){
            for(int y = 0; y < board.getBoardWidth();y++ ){
                ChessPiece piece = board.getPieceAt(new Point(x,y));

                // if instance is king, set the correspoinding team's location
                if(piece instanceof King && piece.getTeam().equals("White")){
                    whiteKingLoc = new Point(x,y);
                } else if(piece instanceof King && piece.getTeam().equals("Black")){
                    blackKingLoc = new Point(x,y);
                }
            }
        }

    }

    /*
        this function translates a human readable coordinate (a4, f6, b1, etc.)
        into an point indexable by an array ( (0,0), (1,4), etc.)
     */
    private Point makePoint (String  text) {
        text = text.toLowerCase();

        // a in ascii is 97
        int  row = text.charAt(0) - 97;

        // 0 in ascii is 48
        // indexes start at 0
        int col = text.charAt(1) - 48 - 1;

        return new Point(row, col);
    }

    /*
        checks the king of a specific team to see if it can safely move to the specified point.
     */
    private boolean canKingMoveto(Point loc, String team) {
        boolean canSafelyMove = false;

        //find king of the player
        ChessPiece king;
        Point kingLoc;

        if(team.equalsIgnoreCase("white")) {
            kingLoc = whiteKingLoc;
            king = this.board.getPieceAt(whiteKingLoc);
        } else {
            kingLoc = blackKingLoc;
            king = this.board.getPieceAt(blackKingLoc);
        }

        //if there is not enemy king, return true
        if(king instanceof Dummy) {
            return canSafelyMove;
        }


        //see if the king can move to the specified location
        if(board.isOnBoard(loc)) {
            // need to store piece at that spot
            ChessPiece temp = board.getPieceAt(loc);

            // if chess piece is on the same team as the king, then return false
            if(temp.getTeam().equalsIgnoreCase(team)) {
                return false;
            }

            // move king to that spot
            board.setPieceAt(loc, new King(king.getTeam()));
            board.setPieceAt(kingLoc, new Dummy());

            // see if king is in danger if he moves there...

            canSafelyMove =
                    !canBishoporQueenKill(loc, board, team) &&
                            !canKingKill(loc, board) &&
                            !canKnightKill(loc, board, team) &&
                            !canPawnKill(loc, board) &&
                            !canRookorQueenKill(loc, board, team);

            // move king back to its original spot
            board.setPieceAt(kingLoc, new King(king.getTeam()));
            board.setPieceAt(loc, temp);
        }
        return canSafelyMove;
    }


    /*
        checks to see if the king has any potential places to move.
        TODO think if I should revise this method to be a linear scan through all of the enemy team's pieces
     */
    private boolean canKingMove(String team){

        //start by clearing the king's valid moves
        validKingMovements.clear();

        //get location of king
        Point kingLoc;

        if(teamInCheck.equalsIgnoreCase("white")) {
            kingLoc = whiteKingLoc;
        } else {
            kingLoc = blackKingLoc;
        }

        //create all possible moves a king can make
        Point[] kingMoves = {
                new Point(kingLoc.x - 1, kingLoc.y - 1), // top left
                new Point( kingLoc.x, kingLoc.y -1), // top middle
                new Point(kingLoc.x + 1, kingLoc.y - 1), // top right
                new Point(kingLoc.x - 1, kingLoc.y), // middle left
                new Point(kingLoc.x + 1, kingLoc.y), // middle right
                new Point( kingLoc.x - 1, kingLoc.y + 1),// bottom left
                new Point( kingLoc.x, kingLoc.y + 1), // bottom middle
                new Point( kingLoc.x + 1, kingLoc.y + 1) //bottom right
        };

        // see if the king can move to any of the locations
        boolean canSafelyMove = false;

        for(Point move: kingMoves) {

            canSafelyMove = canKingMoveto(move, team);

            if(canSafelyMove) {
                //if king can safely move here, the point should be added to the king's move set.
                validKingMovements.add(move);
            }
        }

        //if the king has a place to go, return true;
        return validKingMovements.size() > 0;
    }

    /*
        checks to see if the diagonals around a point contain an enemy queen or a bishop
     */
    private boolean canBishoporQueenKill(Point loc, ChessBoard board, String team){
        boolean containsBishoporQueen = false;

        //check all digaonals to see if they contain a bishop or a queen
        // check top left diagonal

        int x = loc.x - 1;
        int y = loc.y - 1;
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
    private boolean canRookorQueenKill(Point loc, ChessBoard board, String team) {

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
    private boolean canKnightKill(Point loc, ChessBoard board, String team) {
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
            if(this.board.isOnBoard(move)) {
                ChessPiece cur = this.board.getPieceAt(move);
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
                if(cur.canMove(move,loc,board)) {
                    pawnCanKill = true;
                    break;
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
        function created to allow pawns to be replaced once they reach the end of the board
        this function assumes that pawns spawn at their standard locations
     */
    private void replacePawn(Point loc) {

        Pawn pawn = (Pawn) board.getPieceAt(loc);

        //ask user to pick a piece to replace it with
        System.out.println("Please select a piece to replace your pawn with:");
        System.out.println("Bishop (b), Rook (r), Knight (k), Queen (q)");

        Scanner sc = new Scanner(System.in);

        String result = sc.next();

        // replace pawn with the correct piece based on the user's answer
        switch(result){
            case "b":
                board.setPieceAt(loc,new Bishop(pawn.getTeam()));
                break;
            case "r":
                board.setPieceAt(loc,new Rook(pawn.getTeam()));
                break;
            case "k":
                board.setPieceAt(loc,new Knight(pawn.getTeam()));
                break;
            case "q":
                board.setPieceAt(loc,new Queen(pawn.getTeam()));
                break;
        }

    }


}
