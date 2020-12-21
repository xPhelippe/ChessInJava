package Chess;


import java.awt.*;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

public class ChessGame {

    private ChessBoard board;
    private String currentPlayer;
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

    public void play(){

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

        //let the game begin!
        while(true) {

            //print board to user
            System.out.println(board.toString());

            //ask user for points to move
            System.out.print("Please enter piece you want to move: ");
            Scanner sc = new Scanner(System.in);

            Point start = makePoint(sc.next());

            System.out.print("Please enter where you want to move the piece: ");

            Point end = makePoint(sc.next());

            String moveSuccess;

            //check to see if move is on board

            //if king is in check and will still be in check after that movement, then don't allow player to move
            if(isKinginCheck && this.board.getPieceAt(start) instanceof King && !validKingMovements.contains(end) && currentPlayer.equals(teamInCheck)) {
                moveSuccess = "King is in check";
            } else if(isKinginCheck && !(this.board.getPieceAt(start) instanceof King)) {
                moveSuccess = "Must move King. King is in Check";
            } else {
                //try to move user to new location
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
                    // Because the king in check can only move to locations that will make it out of check
                    if(isKinginCheck && teamInCheck.equals(currentPlayer)) {
                        isKinginCheck = false;
                        teamInCheck = "none";
                        validKingMovements.clear();
                    }


                } else {
                //if piece is not a king, we need to see if the enemy's king is in check now
                    if (pieceMoved.getTeam().equals("White")) {
                        isKinginCheck = pieceMoved.canMove(end,blackKingLoc, this.board);
                        if(isKinginCheck){
                            teamInCheck = "Black";
                            System.out.println("King is in Check");
                        }

                    } else {
                        isKinginCheck = pieceMoved.canMove(end,whiteKingLoc, this.board);
                        if(isKinginCheck){
                            teamInCheck = "White";
                            System.out.println("King is in Check");
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
                    System.out.println("Contrats, team " + teamInCheck + " has won!");
                    break;
                }
            }
            else {
                System.out.println(moveSuccess);
            }

            System.out.println("");
        }

    }


    private void findKings() {

        //find the kings on the board
        for(int x = 0; x < board.getBoard().length; x++){
            for(int y = 0; y < board.getBoard()[0].length;y++ ){
                ChessPiece piece = board.getBoard()[x][y];

                if(piece instanceof King && piece.getTeam().equals("White")){
                    whiteKingLoc = new Point(x,y);
                } else if(piece instanceof King && piece.getTeam().equals("Black")){
                    blackKingLoc = new Point(x,y);
                }
            }
        }

    }

    private Point makePoint (String  text) {
        text = text.toLowerCase();

        //a in ascii is 97
        int  row = text.charAt(0) - 97;

        //0 in ascii is 48
        int col = text.charAt(1) - 48 - 1;

        return new Point(row, col);
    }

    private boolean canKingMove(String player){

        //start by clearing the king's valid moves
        validKingMovements.clear();

        //find king of the player
        ChessPiece king;
        Point kingLoc;

        if(teamInCheck.equalsIgnoreCase("white")) {
            kingLoc = whiteKingLoc;
            king = this.board.getPieceAt(whiteKingLoc);
        } else {
            kingLoc = blackKingLoc;
            king = this.board.getPieceAt(blackKingLoc);
        }

        //if there is not enemy king, return true
        if(king instanceof Dummy) {
            return true;
        }

        Point kingMoves[] = {
                new Point(kingLoc.x - 1, kingLoc.y - 1), // top left
                new Point( kingLoc.x, kingLoc.y -1), // top middle
                new Point(kingLoc.x + 1, kingLoc.y - 1), // top right
                new Point(kingLoc.x - 1, kingLoc.y), // middle left
                new Point(kingLoc.x + 1, kingLoc.y), // middle right
                new Point( kingLoc.x - 1, kingLoc.y + 1),// bottom left
                new Point( kingLoc.x, kingLoc.y + 1), // bottom middle
                new Point( kingLoc.x + 1, kingLoc.y + 1) //bottom right
        };

        // this section is not needed because the function is only called after the king is already in check
//        boolean isEmptySpace = false;
//        boolean isAllWhite = true;
//        for(Point move: kingMoves) {
//            if(board.isOnBoard(move)) {
//                if(board.getBoard()[move.x][move.y] instanceof Dummy) {
//                    isEmptySpace = true;
//                    isAllWhite = false;
//                    break;
//                } else if (!board.getBoard()[move.x][move.y].getTeam().equals(enemyKing.getTeam())) {
//                    isAllWhite = false;
//                }
//            }
//        }
//
//        // if king is surrounded by white pieces, then the game can continue
//        if(!isEmptySpace && isAllWhite) {
//            return false;
//        }

        // assume the king cannot move to a location
        boolean canSafelyMove = false;

        for(Point move: kingMoves) {


            if(board.isOnBoard(move)) {
                // need to store piece at that spot
                ChessPiece temp = board.getBoard()[move.x][move.y];

                // if chess piece is on the same team as the king, then continue
                if(temp.getTeam().equalsIgnoreCase(player)) {
                    continue;
                }

                // move king to that spot
                board.getBoard()[move.x][move.y] = new King(king.getTeam());
                board.getBoard()[kingLoc.x][kingLoc.y] = new Dummy();

                // see if king is in danger if he moves there...

                canSafelyMove =
                        !canBishoporQueenKill(move, board, player) &&
                                !canKingKill(move, board) &&
                                !canKnightKill(move, board) &&
                                !canPawnKill(move, board) &&
                                !canRookKill(move, board, player);

                if(canSafelyMove) {
                    //if I can safely move here, then I should add this move to the king's viable moveset
                    validKingMovements.add(move);
                }


                // move king back to its original spot
                board.getBoard()[kingLoc.x][kingLoc.y] = new King(king.getTeam());
                board.getBoard()[move.x][move.y] = temp;


            }
        }

        //if the king has a place to go, return true;
        return validKingMovements.size() > 0;
    }

    private boolean canBishoporQueenKill(Point loc, ChessBoard board, String team){
        boolean containsBishoporQueen = false;

        //check all digaonals to see if they contain a bishop or a queen
        // check top left diagonal

        int x = loc.x - 1;
        int y = loc.y - 1;
        while((x >= 0 && x < board.getBoard()[0].length) && (y >=0 && y < board.getBoard().length)) {

            ChessPiece cur = board.getBoard()[x][y];

            if( cur instanceof Dummy) {//if space is empty, continue to next location

            } else if (cur.getTeam().equals(team)) {// if space contains team member, break and do NOT add to move set
                break;
            } else if ( cur instanceof Queen || cur instanceof Bishop){ // if space contains enemy, add to move set, and then break
               containsBishoporQueen = true;
                break;
            } else { // if enemy piece is not queen or bishop, then we can move on to next diagonal
                break;
            }

            x--;
            y--;

        }

        if(containsBishoporQueen) return true;


        // check top right diagonal

        x = loc.x + 1;
        y = loc.y - 1;
        while((x >= 0 && x < board.getBoard()[0].length) && (y >=0 && y < board.getBoard().length)) {

            ChessPiece cur = board.getBoard()[x][y];

            if( cur instanceof Dummy) {//if space is empty, continue to next location

            } else if (cur.getTeam().equals(team)) {// if space contains team member, break and do NOT add to move set
                break;
            } else if ( cur instanceof Queen || cur instanceof Bishop){ // if space contains enemy, add to move set, and then break
                containsBishoporQueen = true;
                break;
            } else { // if enemy piece is not queen or bishop, then we can move on to next diagonal
                break;
            }

            x++;
            y--;

        }

        if(containsBishoporQueen) return true;

        // check bottom left diagonal

        x = loc.x - 1;
        y = loc.y + 1;
        while((x >= 0 && x < board.getBoard()[0].length) && (y >=0 && y < board.getBoard().length)) {

            ChessPiece cur = board.getBoard()[x][y];

            if( cur instanceof Dummy) {//if space is empty, continue to next location

            } else if (cur.getTeam().equals(team)) {// if space contains team member, break and do NOT add to move set
                break;
            } else if ( cur instanceof Queen || cur instanceof Bishop){ // if space contains enemy, add to move set, and then break
                containsBishoporQueen = true;
                break;
            } else { // if enemy piece is not queen or bishop, then we can move on to next diagonal
                break;
            }

            x--;
            y++;

        }

        if(containsBishoporQueen) return true;

        //check bottom right diagonal

        x = loc.x + 1;
        y = loc.y + 1;
        while((x >= 0 && x < board.getBoard()[0].length) && (y >=0 && y < board.getBoard().length)) {

            ChessPiece cur = board.getBoard()[x][y];

            if( cur instanceof Dummy) {//if space is empty, continue to next location

            } else if (cur.getTeam().equals(team)) {// if space contains team member, break and do NOT add to move set
                break;
            } else if ( cur instanceof Queen || cur instanceof Bishop){ // if space contains enemy, add to move set, and then break
                containsBishoporQueen = true;
                break;
            } else { // if enemy piece is not queen or bishop, then we can move on to next diagonal
                break;
            }

            x++;
            y++;

        }

        return containsBishoporQueen;
    }

    private boolean canRookKill(Point loc, ChessBoard board, String team) {

        boolean containsRook = false;

        // left
        int x = loc.x - 1;
        int y = loc.y;

        while(x >= 0 && x < board.getBoard().length) {

            ChessPiece cur = board.getBoard()[x][y];

            if( cur instanceof Dummy) {//if space is empty, continue to next location

            } else if (cur.getTeam().equals(team)) {// if space contains team member, break
                break;
            } else if ( cur instanceof Rook){ // if space contains enemy, add to move set, and then break
                containsRook = true;
                break;
            } else { // if enemy piece is not queen or bishop, then we can move on to next diagonal
                break;
            }

            x--;
        }

        if(containsRook) return true;

        //right
        x = loc.x + 1;
        y = loc.y;

        while(x >= 0 && x < board.getBoard().length) {

            ChessPiece cur = board.getBoard()[x][y];

            if( cur instanceof Dummy) {//if space is empty, continue to next location

            } else if (cur.getTeam().equals(team)) {// if space contains team member, break
                break;
            } else if ( cur instanceof Rook){ // if space contains enemy, add to move set, and then break
                containsRook = true;
                break;
            } else { // if enemy piece is not queen or bishop, then we can move on to next diagonal
                break;
            }

            x++;
        }

        if(containsRook) return true;

        //up

        x = loc.x;
        y = loc.y - 1;

        while(y >= 0 && y < board.getBoard()[0].length) {

            ChessPiece cur = board.getBoard()[x][y];

            if( cur instanceof Dummy) {//if space is empty, continue to next location

            } else if (cur.getTeam().equals(team)) {// if space contains team member, break
                break;
            } else if ( cur instanceof Rook){ // if space contains enemy, add to move set, and then break
                containsRook = true;
                break;
            } else { // if enemy piece is not queen or bishop, then we can move on to next diagonal
                break;
            }

            y--;
        }

        if(containsRook) return true;

        //down

        x = loc.x;
        y = loc.y + 1;

        while(y >= 0 && y < board.getBoard()[0].length) {

            ChessPiece cur = board.getBoard()[x][y];

            if( cur instanceof Dummy) {//if space is empty, continue to next location

            } else if (cur.getTeam().equals(team)) {// if space contains team member, break
                break;
            } else if ( cur instanceof Rook){ // if space contains enemy, add to move set, and then break
                containsRook = true;
                break;
            } else { // if enemy piece is not queen or bishop, then we can move on to next diagonal
                break;
            }

            y++;
        }

        return containsRook;
    }

    private boolean canKnightKill(Point loc, ChessBoard board) {
        boolean containsKnight = false;

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

        for(Point move: possibleMoves){
            if(this.board.isOnBoard(move) && this.board.getPieceAt(move) instanceof Knight){
                containsKnight = true;
                break;
            }
        }

        return containsKnight;
    }

    private boolean canPawnKill(Point loc, ChessBoard board) {
        boolean pawnCanKill = false;

        //check corners of king

        Point[] possibleMoves = {
                new Point(loc.x - 1, loc.y - 1),
                new Point(loc.x + 1, loc.y - 1),
                new Point(loc.x + 1, loc.y + 1),
                new Point(loc.x - 1, loc.y + 1)
        };

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

    private boolean canKingKill(Point loc, ChessBoard board) {
        boolean containsKing = false;

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
            if(board.getPieceAt(move) instanceof King) {
                containsKing = true;
                break;
            }
        }

        return containsKing;
    }



}
