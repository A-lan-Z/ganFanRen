import bagel.Image;
import bagel.util.Point;
import bagel.util.Rectangle;

import java.io.*;
import java.util.*;

public class Person extends Rectangle {
    private static int race = 0;
    private static final Image XS_DEATH = new Image("res/character/XS_Death.png");
    private static final Image XS = new Image("res/character/XS.png");
    private static final Image M = new Image("res/character/M.png");
    private static final Image XL = new Image("res/character/XL.png");
    private static final Image XL_DEATH = new Image("res/character/XL_Death.png");
    private static final Image DISGUST = new Image("res/character/Male_Disgust.png");
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


    public Person(Point position) {
        super(position, WIDTH, HEIGHT);
        this.image = M;
    }

    public Person(Point position, Properties properties) {
        super(position, WIDTH, HEIGHT);
        this.properties = properties;
        this.image = M;
        this.weight = Double.parseDouble(properties.getProperty("weight"));
        this.hunger = Double.parseDouble(properties.getProperty("hunger"));
        this.lastUpdated = Long.parseLong(properties.getProperty("lastUpdated"));
        this.energy = Double.parseDouble(properties.getProperty("energy"));
        if (lastUpdated == 0) lastUpdated = System.currentTimeMillis();
        this.hungerStatus = new Status(new Point(300, 50), "Hunger", true);
        this.weightStatus = new Status(new Point(300, 90), "Weight", false);
        this.energyStatus = new Status(new Point(300, 70), "Energy", true);
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

    public boolean updateCharacter() {
        if (this.weight < 45) {
            this.image = XS_DEATH;
            return true;
        } else if (this.weight < 50) {
            this.image = XS;
        } else if (this.weight < 55) {
            this.image = M;
        } else if (this.weight < 60) {
            // this.image = L;
        } else if (this.weight < 65) {
            this.image = XL;
        } else {
            this.image = XL_DEATH;
            return true;
        }
        updateStatus();
        displayAllStatus();
        return  false;
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
