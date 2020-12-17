package Chess;


import java.awt.*;
import java.util.Scanner;

public class ChessGame {

    private ChessBoard board;
    private String currentPlayer;

    public void play(){

        board = new ChessBoard();
        currentPlayer = "White";

        while(true) {

            System.out.println(board.toString());

            //ask user for points to move
            System.out.print("Please enter piece you want to move: ");
            Scanner sc = new Scanner(System.in);

            Point start = makePoint(sc.next());

            System.out.print("Please enter where you want to move the piece: ");

            Point end = makePoint(sc.next());

            //try to move user to new location
            String moveSuccess = board.movePiece(start, end, currentPlayer);

            if (!(moveSuccess.equalsIgnoreCase("success"))) {
                System.out.println(moveSuccess);
            }

            System.out.println("");
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

    private boolean hasWon(String player){
        //find king of opposite player
        ChessPiece enemyKing = new Dummy();
        Point enemyKingLoc = new Point(0,0);

        for(int x = 0; x < board.getBoard().length; x++){
            for(int y = 0; y < board.getBoard()[0].length;y++ ){
                ChessPiece piece = board.getBoard()[x][y];

                if(piece instanceof King && !(piece.getTeam().equals(player))){
                    enemyKing = piece;
                    enemyKingLoc = new Point(x,y);
                }
            }
        }

        //if there is not enemy king, return true
        if(enemyKing instanceof Dummy) {
            return true;
        }


        // see if everything around king is of the same team or not

        Point kingMoves[] = {
                new Point(enemyKingLoc.x - 1, enemyKingLoc.y - 1), // top left
                new Point( enemyKingLoc.x, enemyKingLoc.y -1), // top middle
                new Point(enemyKingLoc.x + 1, enemyKingLoc.y - 1), // top right
                new Point(enemyKingLoc.x - 1, enemyKingLoc.y), // middle left
                new Point(enemyKingLoc.x + 1, enemyKingLoc.y), // middle right
                new Point( enemyKingLoc.x - 1, enemyKingLoc.y + 1),// bottom left
                new Point( enemyKingLoc.x, enemyKingLoc.y + 1), // bottom middle
                new Point( enemyKingLoc.x + 1, enemyKingLoc.y + 1) //bottom right
        };

        boolean isEmptySpace = false;
        boolean isAllWhite = true;
        for(Point move: kingMoves) {
            if(board.isOnBoard(move)) {
                if(board.getBoard()[move.x][move.y] instanceof Dummy) {
                    isEmptySpace = true;
                    isAllWhite = false;
                    break;
                } else if (!board.getBoard()[move.x][move.y].getTeam().equals(enemyKing.getTeam())) {
                    isAllWhite = false;
                }
            }
        }

        // if king is surrounded by white pieces, then the game can continue
        if(!isEmptySpace && isAllWhite) {
            return false;
        }

        // assume the king cannot move to a location
        boolean canSafelyMove = false;

        for(Point move: kingMoves) {


            if(board.isOnBoard(move)) {
                // need to store piece at that spot
                ChessPiece temp = board.getBoard()[move.x][move.y];

                // move king to that spot
                board.getBoard()[move.x][move.y] = board.getBoard()[enemyKingLoc.x][enemyKingLoc.y];
                board.getBoard()[enemyKingLoc.x][enemyKingLoc.y] = new Dummy();

                // see if king is in danger if he moves there...
                canSafelyMove =
                        !canBishoporQueenkill(move, board) &&
                                !canKingKill(move, board) &&
                                !canKnightKill(move, board) &&
                                !canPawnKill(move, board) &&
                                !canRookKill(move, board);



                // see if bishop or queen are in diagonals

                // see if knight is in weird spots
                // see if pawn is in special spots
                // see if rook or queen are in diagonals
                // see if king is in any of the closest spots


                //if not...

                // move king back to that spot
                board.getBoard()[enemyKingLoc.x][enemyKingLoc.y] = board.getBoard()[move.x][move.y];
                board.getBoard()[move.x][move.y] = temp;


            }

            if(canSafelyMove) break;
        }

        return !canSafelyMove;

        //see if the king can move somewhere without being killed
            //move the king to each spot
                //see if bishop or queen are in diagonals
                //see if knight is in weird spots
                //see if pawn is in special spots
                //see if rook or queen are in diagonals
                //see if king is in any of the closest spots
            //if king can move to a spot, break and return false

    }

    private boolean canBishoporQueenkill(Point loc, ChessBoard board){
        return false;
    }

    private boolean canRookKill(Point loc, ChessBoard board) {
        return false;
    }

    private boolean canKnightKill(Point loc, ChessBoard board) {
        return false;
    }

    private boolean canPawnKill(Point loc, ChessBoard board) {
        return false;
    }

    private boolean canKingKill(Point loc, ChessBoard board) {
        return false;
    }



}
