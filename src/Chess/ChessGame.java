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
        return true;
    }



}
