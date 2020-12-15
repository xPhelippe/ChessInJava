package Chess;


public class ChessGame {

    private ChessBoard board;
    private String currentPlayer;

    public void play(){

        board = new ChessBoard();
        currentPlayer = "White";

        System.out.println(board.toString());


    }

    private boolean hasWon(String player){
        return true;
    }



}
