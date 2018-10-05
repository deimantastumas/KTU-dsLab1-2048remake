import java.util.LinkedList;
import java.util.concurrent.ThreadLocalRandom;
import javafx.animation.TranslateTransition;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.scene.image.ImageView;
import javafx.util.Duration;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import java.io.File;
import javafx.scene.control.Label;

public class Main extends Application {

    LinkedList<Tile> Tiles = new LinkedList<>();
    final int[][] sequenceInstructions = {
            {4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15},
            {11, 10, 9, 8, 7, 6, 5, 4, 3, 2, 1, 0},
            {1, 5, 9, 13, 2, 6, 10, 14, 3, 7, 11, 15},
            {2, 6, 10, 14, 1, 5, 9, 13, 0, 4, 8, 12},
    };

    @Override
    public void start(Stage primaryStage) throws Exception{
        StackPane mainBg = new StackPane();
        mainBg.getStyleClass().add("mainBg");

        Pane gameBoard = new Pane();
        StackPane scorePane = new StackPane();

        generateDesign(gameBoard, scorePane);

        mainBg.getChildren().add(gameBoard);

        setGrid(gameBoard, Tiles);
        generateNumber(Tiles);

        Scene scene = new Scene(mainBg, 500, 500);
        scene.getStylesheets().add("style.css");

        scene.setOnKeyPressed(e -> {
            if (e.getCode() == KeyCode.UP) {
                findDestination(Tiles, gameBoard, 0);
            }
            else if (e.getCode() == KeyCode.DOWN) {
                findDestination(Tiles, gameBoard, 1);
            }
            else if (e.getCode() == KeyCode.RIGHT) {
                findDestination(Tiles, gameBoard, 3);
            }
            else if (e.getCode() == KeyCode.LEFT) {
                findDestination(Tiles, gameBoard, 2);
            }
        });

        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public void moveBlocks(boolean merge, boolean moved, int startIndex, int endIndex, LinkedList<Tile> tiles, Pane grid, int[] generator) {
        if (merge || moved) {
            Tile start = tiles.get(startIndex);
            Tile finish = tiles.get(endIndex);

            Tile blank = new Tile(start.posX, start.posY);
            grid.getChildren().add(blank.getTile());

            tiles.set(endIndex, start);
            tiles.set(startIndex, blank);

            int distanceX = finish.posX - start.posX;
            int distanceY = finish.posY - start.posY;
            boolean test = merge;
            int skaitliukas = generator[0];

            start.posX = finish.posX;
            start.posY = finish.posY;

            TranslateTransition trans = new TranslateTransition();
            trans.setDuration(Duration.millis(150));
            trans.setByX(distanceX);
            trans.setByY(distanceY);
            trans.setNode(start.getTile());

            trans.setOnFinished(event -> {
                grid.getChildren().remove(finish.getTile());
                if (test) {
                    for (Tile c : tiles) {
                        c.increaseNumber();
                    }
                }
                if (skaitliukas == 1)
                    generateNumber(tiles);
            });
            trans.play();
            generator[0]--;
        }
    }

    public int getNextIndex(int currentIndex, int movementDirection) {
        switch (movementDirection) {
            case 0: return currentIndex - 4; // Higher block
            case 1: return currentIndex + 4; // Lower block
            case 2: return currentIndex - 1; // Left block
            case 3: return currentIndex + 1; // Right block
            default: return 0;
        }
    }

    public boolean isAvailable(int movementDirection, int currentIndex) {
        switch (movementDirection) {
            case 0: return currentIndex >= 4;           // Higher block
            case 1: return currentIndex <= 11;          // Lower block
            case 2: return currentIndex % 4 != 0;       // Left block
            case 3: return (currentIndex + 1) % 4 != 0; // Right block
            default: return false;
        }
    }

    private void playSound(int realPower) {
        boolean levelUp = false;

        String musicMergeSound = "mergeSound.wav";
        String musicLevelUp = "levelUp.wav";

        if (realPower == 512 || realPower == 1024 || realPower == 2048)
            levelUp = true;

        Media sound = new Media(new File(musicMergeSound).toURI().toString());
        MediaPlayer mediaPlayer = new MediaPlayer(sound);

        if (!levelUp) {
            mediaPlayer.play();
        }
        else {
            Media soundLevelUp = new Media(new File(musicLevelUp).toURI().toString());
            MediaPlayer mediaPlayerLevelUp = new MediaPlayer(soundLevelUp);
            mediaPlayerLevelUp.play();
        }
    }

    public void findDestination(LinkedList<Tile> tiles, Pane grid, int movementDirection) {
        boolean moved, merge;
        int currentIndex, end;
        int[] generator = {1};

        for (int i : sequenceInstructions[movementDirection]) {
            currentIndex = i;
            Tile nextTile;
            Tile startingTile = tiles.get(i);
            merge = false;
            moved = false;

            if (startingTile.isUsed()) {
                while (true) {
                    if (isAvailable(movementDirection, currentIndex)) {
                        nextTile = tiles.get(getNextIndex(currentIndex, movementDirection));
                        if (nextTile.isUsed()) {
                            if (startingTile.getNumber() == nextTile.getNumber()) {
                                merge = true;
                                startingTile.realPower *= 2;
                                currentIndex = getNextIndex(currentIndex, movementDirection);
                                break;
                            } else break;
                        }
                        else {
                            currentIndex = getNextIndex(currentIndex, movementDirection);
                            moved = true;
                        }
                    }
                    else break;
                }
            }

            end = currentIndex;
            moveBlocks(merge, moved, i, end, tiles, grid, generator);

            if (merge) {
                playSound(startingTile.realPower);
                //updateScore(startingTile.realPower);
            }
        }
    }

//    public void updateScore(int points) {
//        switch (points) {
//            case 4: score += 100; break;
//            case 8: score += 200; break;
//            case 16: score += 300; break;
//            case 32: score += 500; break;
//            case 64: score += 1000; break;
//            case 128: score += 2000; break;
//            case 256: score += 5000; break;
//            case 512: score += 8000; break;
//            case 1024: score += 10000; break;
//            case 2048: score += 50000; break;
//        }
//
//        labelScore.setText(score + "");
//    }

    public void generateDesign(Pane gameBoard, StackPane scorePane) {
        Rectangle rectGameBoard = new Rectangle(340, 340);
        rectGameBoard.getStyleClass().add("rectGameBoard");
        Image img = new Image("background.png");
        ImageView obj = new ImageView();
        obj.setImage(img);
        obj.setFitWidth(336);
        obj.setFitHeight(336);
        obj.setX(82);
        obj.setY(102);

        //DESIGN
        rectGameBoard.setY(100);
        rectGameBoard.setX(80);
        rectGameBoard.setArcWidth(20);
        rectGameBoard.setArcHeight(20);
        rectGameBoard.setStroke(Color.WHITE);
        rectGameBoard.setStrokeWidth(4);

        //SCORE
//        labelScore.setText(score + "");
//        scorePane.getChildren().add(labelScore);

        gameBoard.getChildren().addAll(rectGameBoard, obj);
    }

    public void setGrid(Pane grid, LinkedList<Tile> tiles) {
        Tile tile;
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                tile = new Tile(100 + 78 * j, 121 + 78 * i);
                grid.getChildren().add(tile.getTile());
                tiles.add(tile);
            }
        }
    }

    public void generateNumber(LinkedList<Tile> tiles) {
        boolean generated = false;
        Tile tile;
        while (!generated) {
            int randomNum = ThreadLocalRandom.current().nextInt(0, 16);
            tile = tiles.get(randomNum);
            if (!tile.isUsed()) {
                tile.insertNumber();
                generated = true;
            }
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}