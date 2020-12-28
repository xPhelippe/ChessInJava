package sample;

import Chess.*;
import javafx.fxml.FXML;
import javafx.scene.shape.Line;

public class Controller {

    private ChessGame game;


    @FXML
    private void initialize() {



        game = new ChessGame();

        //game.play();

    }
}
