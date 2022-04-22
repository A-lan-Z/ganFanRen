import bagel.*;
import bagel.Window;

public class Foodie extends AbstractGame{
    protected final static int WINDOW_WIDTH = 428;
    protected final static int WINDOW_HEIGHT = 926;
    protected final static int NOT_STARTED = -1;
    protected final static int CHOOSING_WINDOW = 0;
    protected final static int MAIN_WINDOW = 1;
    protected final static int MENU_WINDOW = 2;
    protected final static int GAME_OVER = 3;
    protected final static String GAME_TITLE = "Foodie";

    private Person person;
    private Navigation ng;
    private DisplayHandler dh;
    protected int gameState = NOT_STARTED;

    public Foodie() {
        super(WINDOW_WIDTH, WINDOW_HEIGHT, GAME_TITLE);
        dh = new DisplayHandler();
        person = new Person(this);
        ng = new Navigation(this, person);
    }


    /**
     * The entry point for the program.
     */
    public static void main(String[] args) {
        Foodie game = new Foodie();
        game.run();
    }

    /**
     * Performs a state update.
     * allows the game to exit when the escape key is pressed.
     */
    @Override
    public void update(Input input) {
        dh.drawBackground();
        person.updateCharacter(input);
        ng.updateNg(input);

        switch(gameState) {
            case NOT_STARTED:
                dh.drawStartingScreen();
                /* Start the game after space. */
                if (input.wasPressed(Keys.SPACE)) setGameState(MAIN_WINDOW);
                break;
            case CHOOSING_WINDOW:
                dh.drawChoosingWindow();
                break;
            case MAIN_WINDOW:
                break;
            case MENU_WINDOW:
                break;
            case GAME_OVER:
                dh.drawGameOverWindow();
                if (input.wasPressed(Keys.SPACE)) {
                    person.resetPlayer();
                    setGameState(NOT_STARTED);
                }
                break;
        }

        /* Exit the game at any state through ESC. */
        if (input.wasPressed(Keys.ESCAPE)){
            person.save();
            Window.close();
        }

    }

    public void setGameState(int newState) {
        gameState = newState;
    }

}
