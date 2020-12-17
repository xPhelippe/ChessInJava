package Chess;

import java.awt.Point;
import java.util.Locale;

public class ChessBoard {
    private int width;

    private int length;

    private ChessPiece [][] board;

    public ChessPiece[][] getBoard() {
        return board;
    }

    ChessBoard() {
        this.width = 8;
        this.length = 8;
        init();
    }

    public void init() {
        board = new ChessPiece[8][8];
        board[0][0] = new Rook("white");
        board[1][0] = new Knight("White");
        board[2][0] = new Bishop("White");
        board[3][0] = new King("White");
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
        board[3][3] = new Bishop("White");
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
        board[4][7] = new Queen("Black");
        board[5][7] = new Bishop("Black");
        board[6][7] = new Knight("Black");
        board[7][7] = new Rook("Black");

    }

    public String canMovePiece(Point start, Point end, String team) {

        boolean canItMove = this.getBoard()[start.x][start.y].canMove(start, end, this);

        this.getBoard()[start.x][start.y].printMoveSet(start, this);

        if(canItMove) {
            return "yes";
        } else {
            return "no";
        }

    }

    public String movePiece(Point start, Point end, String team) {

        String canMoveResult = canMovePiece(start, end, team);

        if(canMoveResult.toLowerCase() == "yes") {

            //if it is a pawn, set isFirstMove to false
            if(board[start.x][start.y] instanceof Pawn) {
                ((Pawn) board[start.x][start.y]).setFirstMove(false);
            }

            //if there is a piece at the new location. remove it
            if(!(board[end.x][end.y] instanceof Dummy)) {
                board[end.x][end.y] = new Dummy();
            }

            //swap the two pieces
            board[end.x][end.y] = board[start.x][start.y];
            board[start.x][start.y] = new Dummy();

            return "Success";

        } else {
            return canMoveResult;
        }


    }

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
