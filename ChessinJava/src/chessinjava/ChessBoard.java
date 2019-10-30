/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chessinjava;


/**
 *
 * @author souza
 */
public class ChessBoard {
    
    private ChessPiece[][] chessBoard;

    public ChessPiece[][] getChessBoard() {
        return chessBoard;
    }

    public void setChessBoard(ChessPiece[][] chessBoard) {
        this.chessBoard = chessBoard;
    }
    
    ChessBoard() {
        
        chessBoard = new ChessPiece[8][8];
        
        
    
    }
    
}
