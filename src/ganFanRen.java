import bagel.*;
import bagel.Font;
import bagel.Image;
import bagel.Window;
import bagel.util.Point;
import bagel.util.Rectangle;
import java.io.*;
import java.util.*;

public class ganFanRen extends AbstractGame{
    private final static int WINDOW_WIDTH = 428;
    private final static int WINDOW_HEIGHT = 926;
    private final static int NOT_STARTED = -1;
    private final static int CHOOSING_WINDOW = 0;
    private final static int MAIN_WINDOW = 1;
    private final static int MENU_WINDOW = 2;
    private final static String GAME_TITLE = "Gan Fan Ren";
    private final Image BACKGROUND_IMAGE = new Image("res/room.png");
    private final Image M = new Image("res/character/M.png");
    private final Image BAR = new Image("res/bar.png");
    private final Image MENU_ICON = new Image("res/menu_icon_2.png");
    private final Image APPLE = new Image("res/Food/apple.png");
    private final Image BURGER = new Image("res/Food/burger.png");
    private final Image ICE_CREAM = new Image("res/Food/ice_cream.png");
    private final Font font = new Font("res/wheaton.otf", 55);
    private final Font chooseFont = new Font("res/wheaton.otf", 45);

    private Person person;
    private int gameState = NOT_STARTED;
    private Food[] foods;

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
        Food apple = new Food(APPLE, 0.09, 15, 0, 0, 0);
        Food burger = new Food(BURGER,0.55, 40, 0, 0, 0);
        Food iceCream = new Food(ICE_CREAM,0.15, 20, 0, 0, 0);
        foods = new Food[3];
        foods[0] = apple;
        foods[1] = burger;
        foods[2] = iceCream;


        if (gameState == NOT_STARTED) {
            Properties prop = null;
            try (InputStream file = new FileInputStream("./res/Properties/person.properties")) {
                prop = new Properties();
                prop.load(file);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            BAR.draw(Window.getWidth() / 2.0, Window.getHeight() / 2.0);
            printMessage(GAME_TITLE, font, 0);
            /* Start the game after space. */
            if (input.wasPressed(Keys.SPACE)) {
                Point centre = new Point(Window.getWidth() / 2.0, Window.getHeight() / 2.0);
                Point topLeft = findTopLeft(M, centre);
                person = new Person(topLeft, prop);
                gameState = MAIN_WINDOW;
            }
        } else if (gameState == CHOOSING_WINDOW) {
            BAR.draw(Window.getWidth() / 2.0, Window.getHeight() / 2.0);
            printMessage("Choose your Age", chooseFont, -200);
            printMessage("0-15   15-45   45-100", chooseFont, -50);
            printMessage("Choose your Weight", chooseFont, 50);

        } else if (gameState == MAIN_WINDOW) {
            person.updateCharacter();
            person.displayCharacter();
            MENU_ICON.draw(70, 860);
            Rectangle menuHitBox = MENU_ICON.getBoundingBoxAt(new Point(70, 860));
            if (detectBottomPress(input, menuHitBox)) {
                gameState = 2;
            }

        } else if (gameState == MENU_WINDOW) {
            // System.out.println("in menu");
            // character.updateCharacter();
            person.displayCharacter();
            BAR.draw(Window.getWidth() / 2.0, Window.getHeight() / 0.7);
            // Render all food.
            int x = 60;
            int y = 850;
            for (Food food : foods) {
                // Draw food at desired position.
                food.displayFood(x, y);
                x += 90;
                if (x >= 428) {
                    x = 60;
                    y += 200;
                }
                if (detectBottomPress(input, food)) {
                    person.eat(food);
                    System.out.println(person.toString());
                    gameState = MAIN_WINDOW;
                }
                if (input.wasPressed(Keys.BACKSPACE)) {
                    gameState = MAIN_WINDOW;
                }
            }
        }

        /* Exit the game at any state through ESC. */
        if (input.wasPressed(Keys.ESCAPE)){
            person.save();
            Window.close();
        }

    }
}
