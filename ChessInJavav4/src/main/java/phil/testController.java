package phil;


import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;

import java.io.IOException;

public class testController {

    @FXML
    private Button switchButton;

    @FXML
    void onClick(MouseEvent event) throws IOException {
        App.setRoot("primary");
    }

}
