import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;

import bagel.*;
import bagel.util.*;

public class Navigation {
    private final Image MENU_ICON = new Image("res/menu_icon_2.png");

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
            MENU_ICON.draw(70, 860);
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
