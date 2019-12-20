package phil;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;

public class MainMenuController {

    @FXML
    private TextField Player1TextField;

    @FXML
    private TextField Player2TextField;

    @FXML
    void StartButtonMouseClick(MouseEvent event) {

        //I need to pass data between the controller. This requires a custom approach to opening up the page

        FXMLLoader fx = new FXMLLoader(getClass().getResource("gameBoard.fxml"));

        try {
            Parent parent = (Parent) fx.load();

            //I need to have access to the FXMLLoader
            //this allows me to access the controller and call a function within it externally.
            //Doing this allows me to pass information between the two pages
            //all of this just so I can set the names of the players...
            GameBoardController gbc =  fx.getController();
            gbc.setPlayerText(Player1TextField.getText(),Player2TextField.getText());


            Stage mainStage = (Stage) ((Node)event.getSource()).getScene().getWindow();
            mainStage.hide();
            mainStage.setScene(new Scene(parent));
            mainStage.show();



        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
