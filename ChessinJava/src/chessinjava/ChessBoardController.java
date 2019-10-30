/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chessinjava;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;

public class ChessBoardController {

    @FXML
    private GridPane boardGidPane;

    @FXML
    private ImageView Image00;

    @FXML
    private Label player1Label;

    @FXML
    private Label player2Label;
    
    public void initialize() {
        Image pawn = new Image("/src/Chess_plt60.png");
        
        Image00.setImage(pawn);
    }

}
