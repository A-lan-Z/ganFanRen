import bagel.Image;
import bagel.util.Point;
import bagel.util.Rectangle;

import java.io.*;
import java.util.*;

public class Character extends Rectangle {
    private static int race = 0;
    private static final Image M = new Image("res/character/M.png");
    private static final Image BLUSH = new Image("res/character/Male_Blush.png");
    private static final Image DISGUST = new Image("res/character/Male_Disgust.png");
    private static final int WIDTH = (int) M.getWidth();
    private static final int HEIGHT = (int) M.getHeight();

    /* Attributes */
    Image image;
    private double weight;
    private double hunger;
    private long lastUpdated;
    private int mood = 0;
    private int hair = 0;
    private int acne = 0;
    private Properties properties;
    private Status hungerStatus;
    private Status weightStatus;


    public Character(Point position) {
        super(position, WIDTH, HEIGHT);
        this.image = M;
    }

    public Character(Point position, Properties properties) {
        super(position, WIDTH, HEIGHT);
        this.properties = properties;
        this.image = M;
        this.weight = Double.parseDouble(properties.getProperty("weight"));
        this.hunger = Double.parseDouble(properties.getProperty("hunger"));
        this.lastUpdated = Long.parseLong(properties.getProperty("lastUpdated"));
        if (lastUpdated == 0) lastUpdated = System.currentTimeMillis();
        this.hungerStatus = new Status(new Point(300, 50), "Hunger", true);
        this.weightStatus = new Status(new Point(300, 70), "Weight", false);
    }


    public void displayCharacter() {
        image.draw(this.centre().x, this.centre().y);
    }

    public void eat(Food food) {
        this.weight += food.getWeight();
        this.hunger += food.getHunger();
        this.mood += food.getMood();
        this.hair += food.getHair();
        this.acne += food.getAcne();
    }

    public void updateCharacter() {
        if (this.weight < 40) {
            // Death
        } else if (this.weight < 45) {
            // this.image = XS;
        } else if (this.weight < 50) {
            // this.image = S;
        } else if (this.weight < 55) {
            this.image = M;
        } else if (this.weight < 60) {
            // this.image = L;
        } else if (this.weight < 65) {
            // this.image = XL;
        } else {
            // Death
        }
        updateStatus();
        displayAllStatus();
    }


    public void updateStatus() {
        long currentTime = System.currentTimeMillis();
        double diffInHour = (double) (currentTime - lastUpdated)/(100*60*60);
        hunger = Double.max(0,hunger - diffInHour*10);
        lastUpdated = currentTime;
    }

    public void displayAllStatus() {
        hungerStatus.displayStatus(hunger);
        weightStatus.displayStatus(weight);
    }

    public void save() {
        try(OutputStream out = new FileOutputStream("./res/Properties/person.properties")) {
            properties.setProperty("hunger", Double.toString(hunger));
            properties.setProperty("weight", Double.toString(weight));
            properties.setProperty("lastUpdated", Long.toString(lastUpdated));
            properties.store(out, null);
        } catch (Exception e) {
            e.printStackTrace();
        }

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
