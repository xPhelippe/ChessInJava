/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chessinjava;

import java.io.IOException;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

/**
 *
 * @author souza
 */
public class ScreenChanger {
    
    ScreenChanger() {
        
    }
    
    public void changeScreen(String name, MouseEvent event) {
        try {
                //create a parent of the page
                //used in constructor function of Scene object
                // must add an IOException throw to the function signature

                Parent parent = FXMLLoader.load(getClass().getResource(name));

                //creating a scene of the parent object above
                Scene scene = new Scene(parent);
                //makes an object of the stage that is being used by the current node
                // "event" is the current event being invoked

                Stage mainStage = (Stage) ((Node) event.getSource()).getScene().getWindow();

                //hide the current stage
                //saves on resources
                mainStage.hide();

                //setting the scene of the stage
                mainStage.setScene(scene);
                mainStage.show();
            } catch (IOException ex) {
                System.out.printf("Page not Found");
            }
    }
}
