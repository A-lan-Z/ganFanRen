import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;

import bagel.*;
import bagel.util.*;

public class Navigation {
    private final Image MENU_ICON = new Image("res/menu_icon.png");
    private final Image CLOTH_ICON = new Image("res/Clothes_icon.png");
    private final Image EXERCISE_ICON = new Image("res/Exercise_icon.png");
    private final Image SOCIAL_ICON = new Image("res/Social_icon.png");
    private final Image RANK_ICON = new Image("res/Rank_icon.png");

    private List<Food> foods = new ArrayList<>();
    private List<String> foodNames = Arrays.asList("apple", "burger", "icecream", "carrot", "salad");
    private Foodie foodie;
    private Person person;


    public Navigation(Foodie foodie, Person person) {
        this.foodie = foodie;
        this.person = person;
        createFoods();
    }

    public void createFoods() {
        Properties prop;
        for (String name: foodNames) {
            try (InputStream file = new FileInputStream("./res/Properties/" + name + ".properties")) {
                prop = new Properties();
                prop.load(file);
                foods.add(new Food(prop));
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    public void updateNg(Input input) {
        if (foodie.gameState == Foodie.MAIN_WINDOW) {
            MENU_ICON.draw(40, 860);
            CLOTH_ICON.draw(126, 860);
            EXERCISE_ICON.draw(212, 860);
            SOCIAL_ICON.draw(298, 860);
            RANK_ICON.draw(384, 860);
            if (hasClikcedMenuButton(input)) foodie.setGameState(Foodie.MENU_WINDOW);
        } else if (foodie.gameState == Foodie.MENU_WINDOW) {
            renderFood(input);
            if (input.wasPressed(Keys.BACKSPACE)) {
                foodie.setGameState(Foodie.MAIN_WINDOW);
            }
        }
    }

    public boolean hasClikcedMenuButton(Input input) {
        Rectangle menuHitBox = MENU_ICON.getBoundingBoxAt(new Point(70, 860));
        return detectBottomPress(input, menuHitBox);
    }

    private boolean detectBottomPress(Input input, Rectangle button) {
        Point mousePos = input.getMousePosition();
        /* Check if mouse hovers on button */
        if ((button.intersects(mousePos)) && (input.wasPressed(MouseButtons.LEFT))) {
            return true;
        }
        return false;
    }

    private void renderFood(Input input) {
        // Render all food.
        int x = 40;
        int y = 850;
        for (Food food : foods) {
            // Draw food at desired position.
            food.displayFood(x, y);
            x += 85;
            if (detectBottomPress(input, food)) {
                person.eat(food);
                System.out.println(person.toString());
                foodie.setGameState(Foodie.MAIN_WINDOW);
                break;
            }
        }
    }
}
