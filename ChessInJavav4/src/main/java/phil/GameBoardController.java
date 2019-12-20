package phil;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;



public class GameBoardController {

    @FXML
    private GridPane gameBoardGrid;

    @FXML
    private Label Player1Text;

    @FXML
    private Label Player2Text;

    @FXML
    public void initialize() {
        Player2Text.setText("Player2");
        Player1Text.setText("Player1");

    }

    public void setPlayerText(String p1, String p2) {
        Player1Text.setText(p1);
        Player2Text.setText(p2);
    }

}
