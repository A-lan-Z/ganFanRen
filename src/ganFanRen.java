import bagel.*;
import bagel.Font;
import bagel.Image;
import bagel.Window;
import bagel.util.Point;
import bagel.util.Rectangle;

import java.io.FileReader;
import java.io.BufferedReader;

public class ganFanRen extends AbstractGame{
    private final static int WINDOW_WIDTH = 428;
    private final static int WINDOW_HEIGHT = 926;
    private final static int NOT_STARTED = -1;
    private final static int CHOOSING_WINDOW = 0;
    private final static int MAIN_WINDOW = 1;
    private final static int MENU_WINDOW = 2;
    private final static int GAME_ENDED = 3;
    private final static String GAME_TITLE = "Gan Fan Ren";
    private final Image BACKGROUND_IMAGE = new Image("res/room.png");
    private final Image ANGRY = new Image("res/character/Male_Angry.png");
    private final Image BAR = new Image("res/bar.png");
    private final Image MENU_ICON = new Image("res/menu_icon_2.png");
    private final Font font = new Font("res/wheaton.otf", 55);
    private final Font chooseFont = new Font("res/wheaton.otf", 45);

    private Character character;
    private int gameState = NOT_STARTED;

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
     * Method used to print formatted messages in game.
     */
    private void printMessage(String message, Font font, int difference) {
        int x, y;
        /* Calculate the x-coordinate for message to be centered */
        x = (int) ((Window.getWidth() - font.getWidth(message))/2);
        y = (WINDOW_HEIGHT/2);
        font.drawString(message, x, y + difference);
    }

    /**
     * Method used to detect bottom pressed.
     */
    private boolean detectBottomPress(Input input, Rectangle button) {
        Point mousePos = input.getMousePosition();
        /* Check if mouse hovers on button */
        if ((button.intersects(mousePos)) && (input.wasPressed(MouseButtons.LEFT))) {
            return true;
        }
        return false;
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


        if (gameState == NOT_STARTED) {
            printMessage(GAME_TITLE, font, 0);
            /* Start the game after space. */
            if (input.wasPressed(Keys.SPACE)) {
                Point centre = new Point(Window.getWidth() / 2.0, Window.getHeight() / 2.0);
                Point topLeft = findTopLeft(ANGRY, centre);
                character = new Character(topLeft);
                gameState = CHOOSING_WINDOW;
            }
        } else if (gameState == CHOOSING_WINDOW) {
            BAR.draw(Window.getWidth() / 2.0, Window.getHeight() / 2.0);
            printMessage("Choose your Age", chooseFont, -200);
            printMessage("0-15   15-45   45-100", chooseFont, -50);
            printMessage("Choose your Weight", chooseFont, 50);
        } else if (gameState == MAIN_WINDOW) {
            character.updateCharacter();
            character.displayCharacter();
            MENU_ICON.draw(70, 860);
            Rectangle menuHitBox = MENU_ICON.getBoundingBoxAt(new Point(70, 860));
            if (detectBottomPress(input, menuHitBox)) {
                gameState = 2;
            }

        } else if (gameState == MENU_WINDOW) {
            System.out.println("in menu");
            character.updateCharacter();
            character.displayCharacter();
            BAR.draw(Window.getWidth()/2.0, Window.getHeight()/1);
        }

        /* Exit the game at any state through ESC. */
        if (input.wasPressed(Keys.ESCAPE)){
            Window.close();
        }

    }
}