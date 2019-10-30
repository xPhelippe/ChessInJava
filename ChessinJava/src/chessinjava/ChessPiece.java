/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chessinjava;

import java.awt.Point;
import java.util.HashSet;
import java.util.Set;

/**
 *
 * @author souza
 */
public abstract class ChessPiece {
    
    ChessPiece (Point startPos) {
        pos = startPos;
        movablePoints = new HashSet<>();
    }
    
    Point pos;
    Set<Point> movablePoints;
    
    abstract void findMovable(ChessBoard chessBoard);
    
    
}
