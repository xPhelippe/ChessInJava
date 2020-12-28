package sample;

import Chess.*;
import Chess.ChessPiece;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.scene.shape.*;

import java.awt.*;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FilenameFilter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Main extends Application {

    private // used for dragging images. should be replaced at some point
    double x, y;
    private boolean firstTimemoved = true;
    private Point startingPoint;
    public int sceneWidth = 600;
    public int sceneHeight = 600;

    private ChessGame game;

    @Override
    public void start(Stage primaryStage) throws Exception{



        Group root = new Group();
        // Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));



        // creating the square grids
        Rectangle rects[][] = new Rectangle[8][8];

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



        for(Rectangle rect1 []: rects) {
            root.getChildren().addAll(rect1);
        }

        //creating game and setting board
        game = new ChessGame();
        game.init();

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
                        makeDragable(imView);

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

        primaryStage.setTitle("Chess in Java!");
        primaryStage.setScene(new Scene(root, sceneWidth, sceneHeight));
        primaryStage.show();
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
        Image img = new Image(stream);

        //return image view
        return new ImageView(img);

    }

    private void makeDragable(ImageView imv) {
        imv.setOnMousePressed((event) -> {
            if(firstTimemoved) {
                startingPoint = new Point( (int) imv.getX(), (int) imv.getY());
            }
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


            int properX = (int) (event.getX()/(sceneWidth/8)) * sceneWidth/8;
            int properY =  (int) (event.getY()/(sceneHeight/8)) * sceneHeight/8;

            Point from = new Point(startingPoint.x /(sceneWidth/8), startingPoint.y/(sceneHeight/8));
            Point to = new Point((int)properX/(sceneWidth/8), (int) properY/(sceneHeight/8));

            System.out.println("starting point: " + startingPoint);
            System.out.println("From: " + from +" to: " + to);
            String success = game.attemptMovePiece(from, to);

            if(success.equalsIgnoreCase("success")) {
                imv.setX(properX);
                imv.setY(properY);
            } else {
                imv.setX((double) startingPoint.x);
                imv.setY((double) startingPoint.y);
            }


            System.out.println("Success? [" + success + "]");

        });


    }
    public static void main(String[] args) {
        launch(args);
    }
}
