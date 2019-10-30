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
public class Bishop extends ChessPiece {
    
    Bishop(Point startPos) {
        super(startPos);
    }
    
    @Override
    public void findMovable(ChessBoard board) {
        
        int i = pos.x;
        int j = pos.y;
        
        //adding possible points up and to the right
        while(i < 8 && j <8) {
            
            i++;
            j++;
            
            if(board.getChessBoard()[i][j] == null) {
                    movablePoints.add(new Point(i, j));
            } else {
                break;
            }
        }
        
        i = pos.x;
        j = pos.y;
        
        //adding possible points down and to the right
        while(i < 8 && j <8) {
            
            i++;
            j--;
            
            if(board.getChessBoard()[i][j] == null) {
                    movablePoints.add(new Point(i, j));
            } else {
                break;
            }
        }
        
        //adding possible points up and to the left
        while(i < 8 && j <8) {
            
            i--;
            j++;
            
            if(board.getChessBoard()[i][j] == null) {
                    movablePoints.add(new Point(i, j));
            } else {
                break;
            }
        }
        
        //adding possible points down and to the left
        while(i < 8 && j <8) {
            
            i--;
            j--;
            
            if(board.getChessBoard()[i][j] == null) {
                    movablePoints.add(new Point(i, j));
            } else {
                break;
            }
        }
        
    }
    
}
