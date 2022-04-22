import bagel.*;
import bagel.Font;
import bagel.Image;
import bagel.Window;
import bagel.util.Point;

import java.io.FileReader;
import java.io.BufferedReader;

public class ganFanRen extends AbstractGame{
    private final static int WINDOW_WIDTH = 390;
    private final static int WINDOW_HEIGHT = 844;
    private final static String GAME_TITLE = "Gan Fan Ren";
    private final Image BACKGROUND_IMAGE = new Image("res/room.png");
    private final Image ANGRY = new Image("res/character/Male_Angry.png");
    private final Image BAR = new Image("res/bar.png");

    private Character character;
    private int gameState = 0;

    public ganFanRen() {super(WINDOW_WIDTH, WINDOW_HEIGHT, GAME_TITLE);}


    /**
     * Method used to find x, y coordinates for the top left of an image
     * given its center.
     */
    private Point findTopLeft(Image image, Point middle) {
        double x = middle.x - (image.getWidth() / 2);
        double y = middle.y - (image.getHeight() / 2);
        return new Point(x, y);
    }

    /**
     * The entry point for the program.
     */
    public static void main(String[] args) {
        ganFanRen game = new ganFanRen();
        game.run();
    }

    /**
     * Performs a state update.
     * allows the game to exit when the escape key is pressed.
     */
    @Override
    public void update(Input input) {
        BACKGROUND_IMAGE.draw(Window.getWidth()/2.0, Window.getHeight()/2.0);
        Food burger = new Food(2, 0, 0, 0);


        if (gameState == 0) {
            Point centre = new Point(Window.getWidth()/2.0,Window.getHeight()/2.0);
            Point topLeft = findTopLeft(ANGRY, centre);
            character = new Character(topLeft);
            character.displayCharacter();
            BAR.draw(Window.getWidth()/2.0, Window.getHeight()/1.1);
            gameState = 1;

        } else {
            if (input.wasPressed(Keys.SPACE)) {
                character.eat(burger);
                System.out.println(character.toString());
            }
            character.updateCharacter();
            character.displayCharacter();
            BAR.draw(Window.getWidth()/2.0, Window.getHeight()/1.1);

        }

    }
}
