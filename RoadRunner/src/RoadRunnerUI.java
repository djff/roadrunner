import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.*;
import java.util.Stack;

public class RoadRunnerUI extends Application {

    UICell[] startPoint = new UICell[1];
    UICell[][] imageholder;
    boolean eightD = false;
    int totalPoints = 0;
    Label scoreKeeper;
    Stack<int []> undoStack = new Stack<>();
    Stack<int []> redoStack = new Stack<>();
    int N, M;

    public static void main(String[] args) {
        launch(args);
    }

    public void alertDialog(Alert.AlertType type, String title, String message){
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);

        alert.showAndWait();
    }

    public void undoRedoHelper(int[] coord, Stack<int []> stack, Image image, boolean visited){
        UICell imageCell = imageholder[coord[0]][coord[1]];
        stack.push(new int[] {startPoint[0].getX(), startPoint[0].getY()});
        imageCell.setImageView(startPoint[0].temp);
        startPoint[0].setImageView(image);
        startPoint[0].temp = null;
        startPoint[0].setVisited(visited);
        startPoint[0] = imageCell;
    }

    public void updateStack(int x, int y){
        if(undoStack.size() == 3){
            for(int i=0; i<2; i++){
                redoStack.push(undoStack.pop());
            }
            undoStack.pop();
            for(int i=0; i<2; i++){
                undoStack.push(redoStack.pop());
            }

            undoStack.push(new int[]{x, y});
        }
        else{
            while(redoStack.size() > 0) redoStack.pop();
            undoStack.push(new int[]{x, y});
        }
    }

    public void swapImages(UICell imageCell, int x, int y){
        if(!imageCell.visited) {
            startPoint[0].setVisited(true);
            if (startPoint[0].temp != null) {
                imageCell.setImageView(startPoint[0].temp);
                startPoint[0].temp = null;
            } else {
                imageCell.setImageView(startPoint[0].imageObject.getImage());
            }

            if (startPoint[0].imageObject.getAltImage() != null) {
                startPoint[0].imageView.setImage(startPoint[0].imageObject.getAltImage());
            } else {
                startPoint[0].imageView.setImage(startPoint[0].imageObject.getImage());
            }
            startPoint[0] = imageholder[x][y];
            totalPoints += startPoint[0].imageObject.getWeight();
            scoreKeeper.setText("Score: " + totalPoints);
        }
        else{
            String message = "Sorry, That cell has been visited already!!";
            alertDialog(Alert.AlertType.ERROR, "Cell Visited Error", message);
        }

        if(startPoint[0].imageObject.getImageName().equals("goal")){
            String message = "Congrats, be happy, you've reached the Goal with "+totalPoints+" points!!";
            alertDialog(Alert.AlertType.INFORMATION, "Goal Reached", message);
        }

    }

    private void moveUp(int x, int y){
        updateStack(x, y);
        UICell imageCell = imageholder[x-=1][y];
        swapImages(imageCell, x, y);
    }

    private void moveDown(int x, int y){
        UICell imageCell = imageholder[x+=1][y];
        swapImages(imageCell, x, y);
        updateStack(x, y);
    }

    private void moveLeft(int x, int y){
        updateStack(x, y);
        UICell imageCell = imageholder[x][y-=1];
        swapImages(imageCell, x, y);
    }

    private void moveRight(int x, int y){
        updateStack(x, y);
        UICell imageCell = imageholder[x][y+=1];
        swapImages(imageCell, x, y);
    }

    private void moveNE(int x, int y){
        updateStack(x, y);
        UICell imageCell = imageholder[x-=1][y+=1];
        swapImages(imageCell, x, y);
    }

    private void moveSE(int x, int y){
        updateStack(x, y);
        UICell imageCell = imageholder[x+=1][y+=1];
        swapImages(imageCell, x, y);
    }

    private void moveSW(int x, int y){
        updateStack(x, y);
        UICell imageCell = imageholder[x+=1][y-=1];
        swapImages(imageCell, x, y);
    }

    private void moveNW(int x, int y){
        updateStack(x, y);
        UICell imageCell = imageholder[x-=1][y-=1];
        swapImages(imageCell, x, y);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        VBox root = new VBox();
        Scene scene = new Scene(root, 370, 400);

        scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                System.out.println("it is here");
                int x = startPoint[0].getX();
                int y = startPoint[0].getY();
                if(event.getCode() == KeyCode.UP) moveUp(x, y);
                else if(event.getCode() == KeyCode.DOWN) moveDown(x, y);
                else if(event.getCode() == KeyCode.RIGHT) moveRight(x, y);
                else if(event.getCode() == KeyCode.LEFT) moveLeft(x, y);
                else if(event.getCode() == KeyCode.W && eightD) moveNE(x, y);
                else if(event.getCode() == KeyCode.S && eightD) moveSE(x, y);
                else if(event.getCode() == KeyCode.A && eightD) moveSW(x, y);
                else if(event.getCode() == KeyCode.Q && eightD) moveNW(x, y);
                else {
                    String message = "Either Wrong key press or 8D has not been Enabled";
                    alertDialog(Alert.AlertType.WARNING, "Key Not Regocnised", message);
                }
            }
        });


        GridPane grid = new GridPane();
        grid.setVgap(3);
        grid.setHgap(3);
        grid.setBackground(new Background(new BackgroundFill(Color.gray(0.6), CornerRadii.EMPTY, Insets.EMPTY)));
        grid.setPadding(new Insets(5, 0, 5, 0));
        grid.setAlignment(Pos.CENTER);
        root.getChildren().add(grid);

        // Reading the test File
        char[] row;
        BufferedReader reader = new BufferedReader(new FileReader("./TestInputs/sample_test_input_1.txt"));
        String[] line = reader.readLine().split(" ");
        N = Integer.parseInt(line[0]);
        M = Integer.parseInt(line[1]);

        ImagesGrid gridImage = new ImagesGrid(16);
        imageholder = new UICell[N][M];

        for(int i=0; i<N; i++){
            row = reader.readLine().toCharArray();
            for(int j=0; j<M; j++){
                int col = Character.getNumericValue(row[j]);
                Image image = gridImage.getImagesGrid()[col].getImage();
                UICell imageCell = new UICell(i, j, image, gridImage.getImagesGrid()[col]);

                if (gridImage.getImagesGrid()[col].getImageName().equals("road_runner")){
                    startPoint[0] = imageCell;
                    totalPoints += startPoint[0].imageObject.getWeight();
                }
                imageholder[i][j] = imageCell;
                grid.add(imageCell.imageView, j, i);
            }
        }

        Button eightDirection = new Button("Enable 8D");
        eightDirection.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if(!eightD){
                    eightD = true;
                    eightDirection.setText("Disable 8D");
                }
                else{
                    eightD = false;
                    eightDirection.setText("Enable 8D");
                }
            }
        });

        Button undoFeature = new Button("Undo");
        undoFeature.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if(undoStack.size() > 0) {
                    int[] coord = undoStack.pop();
                    undoRedoHelper(coord, redoStack, startPoint[0].imageObject.getImage(), false);
                }
                else{
                    String message = "No Undo operation found !!";
                    alertDialog(Alert.AlertType.ERROR, "Error with Undo Operation", message);
                }
            }
        });

        Button redoFeature = new Button("Redo");
        redoFeature.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if(redoStack.size() > 0){
                    int[] coord = redoStack.pop();
                    undoRedoHelper(coord, undoStack, startPoint[0].imageObject.getAltImage(), true);
                }
                else{
                    String message = "No Redo operation found !!";
                    alertDialog(Alert.AlertType.ERROR, "Error with Redo Operation", message);
                }
            }
        });

        Button reset = new Button("Reset");
        reset.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                for(int i=0; i<N; i++){
                    for(int j=0; j<M; j++){
                        imageholder[i][j].setVisited(false);
                        imageholder[i][j].imageView.setImage(imageholder[i][j].imageObject.getImage());
                        if (imageholder[i][j].imageObject.getImageName().equals("road_runner")){
                            System.out.println("it is here reset");
                            startPoint[0] = imageholder[i][j];
                        }
                    }
                }
                scoreKeeper.setText("Score: 0");
                totalPoints = 0;
            }
        });

        scoreKeeper = new Label("Score: 0");
        scoreKeeper.setPadding(new Insets(5, 0, 0, 0));

        HBox btnHb = new HBox(10);
        btnHb.setPadding(new Insets(15, 0, 0, 20));
        btnHb.getChildren().addAll(eightDirection, undoFeature, redoFeature, reset, scoreKeeper);

        root.getChildren().addAll(btnHb);

        primaryStage.setTitle("RoadRunner");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
