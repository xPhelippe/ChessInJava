package sample;

import Chess.*;
import javafx.fxml.FXML;
import javafx.scene.Group;
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

    private double x, y;
    private Point startingPoint;

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

    private void drawBoard(Pane root) {
        root.getChildren().clear();

        double sceneWidth = root.getPrefWidth();
        double sceneHeight = root.getPrefHeight();

        // creating the square grids
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

        //grab from path
        FileInputStream stream = new FileInputStream("./ChessImages/" + team + type + ".png");

        //create image
        javafx.scene.image.Image img = new Image(stream);

        //return image view
        return new ImageView(img);

    }

    private void makeDraggable(ImageView imv, Pane root) {
        double sceneWidth = root.getPrefWidth();
        double sceneHeight = root.getPrefHeight();

        imv.setOnMousePressed((event) -> {

            startingPoint = new Point( (int) imv.getX(), (int) imv.getY());

            x = event.getX();
            y = event.getY();
        });

        imv.setOnMouseDragged((event) -> {

            //System.out.println("From (" + x + "," + y + ") to (" + event.getX() + "," + event.getY() + ")");

            double translateX = event.getX() - x;
            double translateY = event.getY() - y;

            imv.setX(imv.getX() + translateX);
            imv.setY(imv.getY() + translateY);

            x = event.getX();
            y = event.getY();

        });

        imv.setOnMouseReleased((event) -> {

            int properX = (int) ((int)  (event.getX()/(sceneWidth/8)) * sceneWidth/8);
            int properY = (int) ((int) (event.getY()/(sceneHeight/8)) * sceneHeight/8);

            Point from = new Point( startingPoint.x /( (int) sceneWidth/8), startingPoint.y/( (int) sceneHeight/8));
            Point to = new Point(properX/( (int) sceneWidth/8), properY/( (int) sceneHeight/8));


            System.out.println("starting point: " + startingPoint);
            System.out.println("From: " + from +" to: " + to);
            String success = game.attemptMovePiece(from, to);

            if(success.equalsIgnoreCase("success")) {
                imv.setX(properX);
                imv.setY(properY);
            } else {
                imv.setX((double) startingPoint.x);
                imv.setY((double) startingPoint.y);

                messageText.setText(success);
            }

            System.out.println("Success? [" + success + "]");


            drawBoard(root);


        });


    }

}