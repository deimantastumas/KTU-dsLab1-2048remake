import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;

public class Tile {
    private int power = 0;
    public int posX, posY;
    public int realPower = 0;
    private boolean usedTile = false;
    private Label number = new Label("");

    private Rectangle rec;
    private StackPane bgBlock;

    public Tile(int posX, int posY) {
        bgBlock = new StackPane();
        this.posX = posX;
        this.posY = posY;
        bgBlock.setLayoutX(posX);
        bgBlock.setLayoutY(posY);
    }

    public void setUsed(boolean check) {
        usedTile = check;
    }

    // Returns a number of the block
    public int getNumber() {
        return realPower;
    }

    // Returns StackPane object
    public StackPane getTile() {
        return bgBlock;
    }

    // Tells whether block is used or not
    public boolean isUsed() {
        return usedTile;
    }

    public void increaseNumber() {
        power = realPower;
        number.setText(String.valueOf(power));
        changeColour();
    }

    private void changeColour() {
        switch (power) {
            case 2:
                rec.setFill(Color.WHITE);
                break;
            case 4:
                rec.setFill(Color.web("ffb142", 1));
                break;
            case 8:
                rec.setFill(Color.web("f3a683", 1));
                break;
            case 16:
                rec.setFill(Color.web("786fa6", 1));
                break;
            case 32:
                rec.setFill(Color.web("f7d794", 1));
                break;
            case 64:
                rec.setFill(Color.web("f8a5c2", 1));
                break;
            case 128:
                rec.setFill(Color.web("778beb", 1));
                break;
            case 256:
                rec.setFill(Color.web("63cdda", 1));
                break;
            case 512:
                rec.setFill(Color.web("ea8685", 1));
                break;
            case 1024:
                rec.setFill(Color.web("cf6a87", 1));
                break;
            case 2048:
                rec.setFill(Color.web("596275", 1));
                break;
        }
    }

    private Rectangle generateRectangle() {
        rec = new Rectangle(65, 65);
        rec.setArcWidth(10);
        rec.setArcHeight(10);
        rec.setFill(Color.WHITE);

        return rec;
    }

    public void insertNumber() {
        rec = generateRectangle();
        number.setText("2");
        number.setFont(Font.font("Dubai Medium", 35));
        power = 2;
        realPower = 2;
        usedTile = true;
        bgBlock.getChildren().addAll(rec, number);
    }
}
