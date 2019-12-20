package phil;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * JavaFX App
 */
public class App extends Application {

    private static Scene scene;

    @Override
    public void start(Stage stage) throws IOException {
        scene = new Scene(loadFXML("mainMenu"));
        stage.setScene(scene);
        stage.setTitle("Chess in Java");
        stage.show();
    }

    //used to change the scene to whatever I need
    static void setRoot(String fxml) throws IOException {
        Scene sc = new Scene(loadFXML(fxml));

        Stage st = (Stage) scene.getWindow();

        st.hide();
        st.setScene(sc);
        st.show();
    }

    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }

    public static void main(String[] args) {
        launch();
    }

}