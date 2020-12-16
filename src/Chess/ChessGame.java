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

            String moveSuccess = board.movePiece(start, end, currentPlayer);

            if (!(moveSuccess.toLowerCase() == "success")) {
                System.out.println(moveSuccess);
            }

            System.out.println("");
        }


    }

    private Point makePoint (String  text) {

        return new Point(Integer.valueOf(text.substring(0,1)), Integer.valueOf(text.substring(2,3)));
    }

    private boolean hasWon(String player){
        return true;
    }



}
