/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chessinjava;

import java.awt.Point;
/**
 *
 * @author souza
 */
public class ChessCell {
    public Point position;
    public ChessPiece chessPiece;

    @Override
    public String toString() {
        return "ChessCell{" + "position=" + position + ", chessPiece=" + chessPiece + '}';
    }
    
    
}
