import bagel.*;
import bagel.util.*;

import java.io.*;
import java.util.*;

public class Person extends Rectangle {
    private static int race = 0;
    private static final Image XS_DEATH = new Image("res/character/XS_Death.png");
    private static final Image XS = new Image("res/character/XS.png");
    private static final Image M = new Image("res/character/M.png");
    private static final Image L = new Image("res/character/L.png");
    private static final Image XL = new Image("res/character/XL.png");
    private static final Image XL_DEATH = new Image("res/character/XL_Death.png");
    private static final int WIDTH = (int) M.getWidth();
    private static final int HEIGHT = (int) M.getHeight();

    /* Attributes */
    Image image;
    private double weight;
    private double hunger;
    private double energy;
    private long lastUpdated;
    private double mood = 0;
    private double hair = 0;
    private double acne = 0;
    private Properties properties;
    private Status hungerStatus;
    private Status weightStatus;
    private Status energyStatus;
    private Image aura;
    private Foodie foodie;

    public Person(Foodie foodie) {
        super(new Point(Window.getWidth() / 2.0 - M.getWidth() / 2,
                Window.getHeight() / 2.0 + 90 - M.getHeight() / 2), WIDTH, HEIGHT);

        this.foodie = foodie;
        readInProperties();
        this.hungerStatus = new Status(new Point(300, 50), "Hunger", true);
        this.weightStatus = new Status(new Point(300, 90), "Weight", false);
        this.energyStatus = new Status(new Point(300, 70), "Energy", true);
        this.aura = null;
    }

    public void readInProperties() {
        Properties prop = null;
        try (InputStream file = new FileInputStream("./res/Properties/person.properties")) {
            prop = new Properties();
            prop.load(file);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        this.properties = prop;
        this.image = M;
        this.weight = Double.parseDouble(properties.getProperty("weight"));
        this.hunger = Double.parseDouble(properties.getProperty("hunger"));
        this.lastUpdated = Long.parseLong(properties.getProperty("lastUpdated"));
        this.energy = Double.parseDouble(properties.getProperty("energy"));
        if (lastUpdated == 0) lastUpdated = System.currentTimeMillis();
    }


    public void displayCharacter() {
        image.draw(this.centre().x, this.centre().y);
    }

    public void eat(Food food) {
        weight = Math.min(100, weight + food.getWeight());
        hunger = Math.min(100, hunger + food.getHunger());
        mood = Math.min(100, mood + food.getMood());
        hair = Math.min(100, hair + food.getHair());
        acne = Math.min(100, acne + food.getAcne());
        energy = Math.min(100, energy + food.getEnergy());
    }

    public void updateCharacter(Input input) {
        if (foodie.gameState == Foodie.CHOOSING_WINDOW) return;
        if (foodie.gameState == Foodie.NOT_STARTED) {
            resetPlayer();
            return;
        }
        if (foodie.gameState == Foodie.GAME_OVER) {
            displayCharacter();
            return;
        }
        changeImage();
        updateStatus();
        updateAura();
        displayAllStatus();
        displayCharacter();
        displayAura();
        if (hasDied()) {
            foodie.setGameState(Foodie.GAME_OVER);
        }

    }
    public boolean hasDied() {
        return image == XL_DEATH;
    }

    public void changeImage() {
        if (this.weight < 45) {
            this.image = XS_DEATH;
        } else if (this.weight < 50) {
            this.image = XS;
        } else if (this.weight < 55) {
            this.image = M;
        } else if (this.weight < 60) {
            this.image = L;
        } else if (this.weight < 65) {
            this.image = XL;
        } else {
            this.image = XL_DEATH;
        }
    }

    public void updateStatus() {
        long currentTime = System.currentTimeMillis();
        double diffInHour = (double) (currentTime - lastUpdated)/(1000*60*60);
        hunger = Double.max(0,hunger - diffInHour*10);
        energy = Double.max(0, energy - diffInHour*10);
        lastUpdated = currentTime;
    }

    public void displayAllStatus() {
        hungerStatus.displayStatus(hunger);
        weightStatus.displayStatus(weight);
        energyStatus.displayStatus(energy);
    }

    public void updateAura() {
        if (energy >= 80) aura = new Image("./res/Aura/energetic.png");
        else if (energy <= 20) aura = new Image("./res/Aura/lowenergy.png");
        else aura = null;
    }

    public void displayAura() {
        if (aura == null) return;
        else aura.draw(220, 400);
    }

    public void save() {
        try(OutputStream out = new FileOutputStream("./res/Properties/person.properties")) {
            properties.setProperty("hunger", Double.toString(hunger));
            properties.setProperty("weight", Double.toString(weight));
            properties.setProperty("lastUpdated", Long.toString(lastUpdated));
            properties.setProperty("energy", Double.toString(energy));
            properties.store(out, null);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void resetPlayer() {
        try(OutputStream out = new FileOutputStream("./res/Properties/person.properties")) {
            properties.setProperty("hunger", Double.toString(50));
            properties.setProperty("weight", Double.toString(50));
            properties.setProperty("lastUpdated", Long.toString(0));
            properties.setProperty("energy", Double.toString(50));
            properties.store(out, null);
        } catch (Exception e) {
            e.printStackTrace();
        }
        readInProperties();
    }


    @Override
    public String toString() {
        return "Character{" +
                "weight=" + weight +
                ", mood=" + mood +
                ", hair=" + hair +
                ", acne=" + acne +
                '}';
    }
}
