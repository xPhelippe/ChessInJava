package phil;

import chessPieces.*;
import chessPieces.ChessPiece;

import java.awt.*;

public class ChessBoard {

    private ChessPiece[][] board;

    private int width;
    private int height;

    ChessBoard() {
        board = new ChessPiece[8][8];
    }

    public void insertPiece(String type, Point location){
        switch(type.toLowerCase()){
            case "pawn":
                board[location.x][location.y] = new Pawn(location);
                break;
            case "rook":
                board[location.x][location.y] = new Rook();
                break;
            case "knight":
                board[location.x][location.y] = new Knight();
                break;
            case "King":
                board[location.x][location.y] = new King();
                break;
            case "Queen":
                board[location.x][location.y] = new Queen();
                break;
            case "bishop":
                board[location.x][location.y] = new Bishop();
                break;
        }
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public ChessPiece[][] getBoard() {
        return board;
    }

    public void setBoard(ChessPiece[][] board) {
        this.board = board;
    }

}
