package sample;

import Chess.ChessGame;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class PawnChangeController {

    // a reference to the current game
    ChessGame game;

    // a reference to the original window
    Controller mainWindow;

    @FXML
    private ImageView BishopImageView;

    @FXML
    private ImageView RookImageView;

    @FXML
    private ImageView QueenImageView;

    @FXML
    private ImageView KnightImageView;


    @FXML
    private HBox imageHbox;

    // the team that gets to change their piece
    private String team;

    public void initialize() {


    }

    public void setTeam(String team) {
        this.team = team;
    }

    /*
        sets the images for the player's options based on the team field
     */
    public void loadImages() throws FileNotFoundException {

        //System.out.println(System.getProperty("user.dir"));


        String [] pieces = {"Bishop", "Rook","Queen","Knight"};
        ImageView [] views = {
                BishopImageView,
                RookImageView,
                QueenImageView,
                KnightImageView
        };


        for(int i = 0; i < pieces.length; i++) {
            String piece = pieces[i];
                // creating the url for the image to be loaded
                Image img = new Image(new FileInputStream("./ChessImages/" + team + piece + ".png"));

                // setting the Image View
                views[i].setImage(img);

                // sizing the image
                views[i].setFitHeight(50);
                views[i].setFitWidth(50);
                views[i].setPreserveRatio(true);
        }

        // adding a click listener to the image
        for(ImageView view: views) {
            view.setOnMouseClicked((event) -> {

                // looks at the URL of the image and replaces the
                // pawn with the piece found in the url
                if(view.getId().toLowerCase().contains("bishop")) {
                    game.replacePawn("Bishop", team);
                } else if (view.getId().toLowerCase().contains("queen")){
                    game.replacePawn("Queen", team);
                } else if (view.getId().toLowerCase().contains("knight")) {
                    game.replacePawn("Knight",team);
                } else if(view.getId().toLowerCase().contains("rook")){
                    game.replacePawn("Rook", team);
                }

                // redraw the board before closing the current window
                mainWindow.drawBoard();

                // close the window
                ((Stage)view.getScene().getWindow()).close();

            });
        }


    }
}
