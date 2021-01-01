package Chess;


import java.awt.*;
import java.util.HashSet;
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

    public ChessGame() {
        board = new ChessBoard();
    }

    /*
        initializes the chess board and some key values
     */
    public void init() {
        //initialize chess board
        board.init();

        //set current player to white
        currentPlayer = "White";

    }

    /*
        tries to move a piece from the starting location to the ending location
     */
    public SuccessMessage attemptMovePiece(Point start, Point end) {

        SuccessMessage moveSuccess = new SuccessMessage();

        //try to move piece chosen by user to new location
        moveSuccess = board.movePiece(start, end, currentPlayer);

        //check and see if move was successful
        if (moveSuccess.getResult() == MoveResult.SUCCESS || moveSuccess.getResult() == MoveResult.SUCCESS_W_MESSAGE) {

            ChessPiece pieceMoved = this.board.getPieceAt(end);

            // if the piece was a pawn, then check and see if the pawn can be promoted
            if(pieceMoved instanceof Pawn) {
                //see if it is at its opposite end
                if(end.y == 0 || end.y == 7) {
                    moveSuccess.setResult(MoveResult.SUCCESS_W_MESSAGE);
                    moveSuccess.setMessage("Change " + pieceMoved.getTeam() + " pawn");
                }
            }


            // Check and see if the current player won
            if(hasWon(currentPlayer)) {
                moveSuccess.setResult(MoveResult.SUCCESS_W_MESSAGE);
                moveSuccess.setMessage("Congratulatiosn! " + currentPlayer + " has won!");
            }

            // TODO add stalemate condition

            // switch teams
            if(currentPlayer.equals("White")) {
                currentPlayer = "Black";
            } else {
                currentPlayer = "White";
            }
        }

        System.out.println(board.toString());

        return moveSuccess;



    }


    /*
        check to see if the given player has won the game
     */
    private boolean hasWon(String team) {

        // grab the opposite team
        String teamtoCheck;
        if(team.equalsIgnoreCase("white")) {
            teamtoCheck = "Black";
        } else {
            teamtoCheck = "White";
        }

        // see if the king is in check
        if(board.isKinginCheck(teamtoCheck)){
            // if king is in check, see if he is in check mate
            return board.isKingInCheckmate(teamtoCheck);
        } else {
            return false;
        }



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

        boolean gameisWon = false;

        //let the game begin!
        while(!gameisWon) {

            // print board to user
            System.out.println(board.toString());

            // ask user for points to move
            System.out.print("Please enter piece you want to move: ");
            Scanner sc = new Scanner(System.in);

            Point start = makePoint(sc.next());

            System.out.print("Please enter where you want to move the piece: ");

            Point end = makePoint(sc.next());

            SuccessMessage moveSuccess = attemptMovePiece(start, end);

            switch (moveSuccess.getResult()){
                case SUCCESS:
                    break;
                case SUCCESS_W_MESSAGE:
                    if(moveSuccess.getMessage().toLowerCase().contains("pawn")) {
                        replacePawn(end);
                    }

                    if(moveSuccess.getMessage().toLowerCase().contains("won")) {
                        System.out.println(moveSuccess.getMessage());
                        gameisWon = true;
                    }


                case FAILED:
                    System.out.println(moveSuccess.getMessage());
            }


            System.out.println();
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
