import bagel.*;
import bagel.util.*;
import java.util.*;

public class DisplayHandler {
    private final static int WINDOW_WIDTH = 428;
    private final static int WINDOW_HEIGHT = 926;
    private final static String GAME_TITLE = "Foodie";
    private final Image BACKGROUND_IMAGE = new Image("res/background.jpg");
    private final Image BAR = new Image("res/bar.png");

    private final Font font = new Font("res/wheaton.otf", 55);
    private final Font chooseFont = new Font("res/wheaton.otf", 45);

    public DisplayHandler() {}

    public void drawStartingScreen() {
        BAR.draw(Window.getWidth() / 2.0, Window.getHeight() / 2.0);
        printMessage(GAME_TITLE, font, 0);
    }

    public void drawChoosingWindow() {
        BAR.draw(Window.getWidth() / 2.0, Window.getHeight() / 2.0);
        printMessage("Choose your Age", chooseFont, -200);
        printMessage("0-15   15-45   45-100", chooseFont, -50);
        printMessage("Choose your Weight", chooseFont, 50);
    }

    public void drawBackground() {
        BACKGROUND_IMAGE.draw(Window.getWidth()/2.0, Window.getHeight()/2.0);
    }

    public void drawGameOverWindow() {
        printMessage("Died from\n Heart attack", font, 0);
    }

    private void printMessage(String message, Font font, int difference) {
        int x, y;
        /* Calculate the x-coordinate for message to be centered */
        x = (int) ((Window.getWidth() - font.getWidth(message))/2);
        y = (WINDOW_HEIGHT/2);
        font.drawString(message, x, y + difference);
    }


}
