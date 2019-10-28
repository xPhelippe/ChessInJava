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
public abstract class ChessPiece {
    
    
    abstract void move(Point from, Point to);
    abstract void attack(Point point);
    
}
