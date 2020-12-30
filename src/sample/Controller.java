package sample;

import Chess.*;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

import java.awt.*;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class Controller {

    // variables used to implement drag and drop functionality
    private double prevX, prevY;
    private Point startingPoint;

    // ChessGame class provides an interface between the controller and the game
    private ChessGame game;

    @FXML
    private Text playerTwoText;

    @FXML
    private Text messageText;

    @FXML
    private Text playerOneText;

    @FXML
    private Pane boardPane;

    public void initialize() {

        //creating game and setting board
        game = new ChessGame();
        game.init();

        //drawing the board
        drawBoard(boardPane);

        //setting the text
        playerOneText.setText("PlayerOne");
        playerTwoText.setText("PlayerTwo");
        messageText.setText("Let the Game Begin!");
    }

    /*
        Draws the current state of the chess game on the Pane provided
        Requires for the ChessGame object to be initialized before being called.
     */
    private void drawBoard(Pane root) {

        // Start's by clearing the pane of its previous content
        root.getChildren().clear();

        // the width and height of the pane are used throughout to make even squares on the board
        double sceneWidth = root.getPrefWidth();
        double sceneHeight = root.getPrefHeight();

        // creating the square grid
        Rectangle[][] rects = new Rectangle[8][8];

        for(int i = 0; i < 8; i++) {
            for(int j = 0; j < 8; j++) {
                rects[i][j] = new Rectangle();
                rects[i][j].setHeight(sceneHeight/8);
                rects[i][j].setWidth(sceneWidth/8);

                rects[i][j].setX(i*sceneWidth/8);
                rects[i][j].setY(j*sceneHeight/8);

                if((i+j)%2 == 0) {
                    rects[i][j].setFill(Color.web("#BDD5EA"));
                } else {
                    rects[i][j].setFill(Color.web("#577399"));
                }

            }

        }

        //adding square grid to the pane
        for(Rectangle[] rect1 : rects) {
            root.getChildren().addAll(rect1);
        }


        //adding chess pieces from the game board
        for(int i = 0; i < 8; i++) {
            for(int j = 0; j < 8; j++) {
                ChessPiece cur = game.getBoard().getPieceAt(new Point(i,j));

                // TODO refactor this away so that the view does not have to know anything about the chess game
                // possible ideas
                // make the getChessPieceImageView class part of the chess class;
                if(!(cur instanceof Dummy) ) {


                    try {


                        ImageView imView = getChessPieceImageView(cur);

                        //setting image loc
                        imView.setX(i * sceneWidth / 8);
                        imView.setY(j * sceneHeight / 8);

                        //setting image size
                        imView.setFitWidth(sceneWidth / 8);
                        imView.setFitHeight(sceneHeight / 8);

                        imView.setPreserveRatio(true);

                        //make the imageviews draggable
                        makeDraggable(imView, root);

                        //adding image to scene
                        root.getChildren().add(imView);
                    } catch (FileNotFoundException e) {
                        System.out.println("No image here");
                        System.out.println(System.getProperty("user.dir"));
                    } catch (Exception e) {
                        System.out.println("Something Bad Happened");
                    }
                }
            }
        }

    }

    /*
        given a chess piece, the function returns the correct image for that chesspiece
     */
    private ImageView getChessPieceImageView(ChessPiece piece ) throws FileNotFoundException {
        //see what the type of chess piece is
        String team = piece.getTeam();

        //see what the team of the chess piece is
        String pieceInitial = piece.toString().substring(1,2);

        String type = switch (pieceInitial.toLowerCase()) {
            case "k" -> "King";
            case "q" -> "Queen";
            case "r" -> "Rook";
            case "n" -> "Knight";
            case "p" -> "Pawn";
            case "b" -> "Bishop";
            default -> "Dummy";
        };

        // construct path to correct image
        FileInputStream stream = new FileInputStream("./ChessImages/" + team + type + ".png");

        //gram image from path
        Image img = new Image(stream);

        //return image view
        return new ImageView(img);

    }

    /*
        implements and adds all necessary listeners for drag and drop events
     */
    private void makeDraggable(ImageView imv, Pane root) {
        double sceneWidth = root.getPrefWidth();
        double sceneHeight = root.getPrefHeight();


        imv.setOnMousePressed((event) -> {

            // stores point where chess piece starts
            startingPoint = new Point( (int) imv.getX(), (int) imv.getY());

            // saves the current (X,Y) coordinates of the mouse to be used in the future for translation
            prevX = event.getX();
            prevY = event.getY();
        });

        imv.setOnMouseDragged((event) -> {

            // for testing
            //System.out.println("From (" + x + "," + y + ") to (" + event.getX() + "," + event.getY() + ")");

            // calculates how far the mouse has moved in each direction
            double translateX = event.getX() - prevX;
            double translateY = event.getY() - prevY;

            // moves the image by the same amount the mouse has moved
            imv.setX(imv.getX() + translateX);
            imv.setY(imv.getY() + translateY);

            // sets the previous (X,Y) to the current location of the mouse
            prevX = event.getX();
            prevY = event.getY();

        });

        imv.setOnMouseReleased((event) -> {

            // calculating final place for image to "snap" to when mouse is released
            int properX = (int) ((int)  (event.getX()/(sceneWidth/8)) * sceneWidth/8);
            int properY = (int) ((int) (event.getY()/(sceneHeight/8)) * sceneHeight/8);

            // preparing points to be inserted in to ChessGame function
            Point from = new Point( startingPoint.x /( (int) sceneWidth/8), startingPoint.y/( (int) sceneHeight/8));
            Point to = new Point(properX/( (int) sceneWidth/8), properY/( (int) sceneHeight/8));

            // for testing
            //System.out.println("starting point: " + startingPoint);
            //System.out.println("From: " + from +" to: " + to);

            // attemtping to move the piece located at "from" to "to"
            String success = game.attemptMovePiece(from, to);

            // TODO add check for if game is over!
            if(success.equalsIgnoreCase("success")) {
                // if move is successful image is moved
                imv.setX(properX);
                imv.setY(properY);

                // and message says what team goes next
                messageText.setText(game.getCurrentPlayer() + " team may move now.");
            } else {
                // if move is not successful, piece must be moved back to starting location


                imv.setX(startingPoint.x);
                imv.setY(startingPoint.y);

                // and error text must be displayed
                messageText.setText(success);
            }

            // for testing purposes
            System.out.println("Success? [" + success + "]");

            // once move is made, the board gets redrawn
            drawBoard(root);


        });


    }

}