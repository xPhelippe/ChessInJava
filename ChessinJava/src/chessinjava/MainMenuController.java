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
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

public class MainMenuController {

    @FXML
    private TextField p1TextField;

    @FXML
    private TextField p2TextField;

    @FXML
    private Button playButton;

    @FXML
    private Label testLabel;
    
    @FXML
    void ClicktoPlayButtonClick(MouseEvent event) {
        String text;
        text = "P1:" + p1TextField.getText() + " P2:" + p2TextField.getText();
        testLabel.setText(text);
    }

}
