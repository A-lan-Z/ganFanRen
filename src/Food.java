import bagel.Image;
import bagel.util.Point;
import bagel.util.Rectangle;

public class Food extends Rectangle{
    private static final int WIDTH = 96;
    private static final int HEIGHT = 96;

    private Image image;
    private double weight = 0;
    private double hunger = 0;
    private double mood = 0;
    private double hair = 0;
    private double acne = 0;
    private double energy = 0;

    public Food(Image image, double weight, double hunger, double mood, double hair, double acne) {
        super(new Point(0, 0), WIDTH, HEIGHT);
        this.image = image;
        this.weight = weight;
        this.hunger = hunger;
        this.mood = mood;
        this.hair = hair;
        this.acne = acne;
        this.energy = energy;
    }

    public double getWeight() {
        return weight;
    }

    public double getMood() {
        return mood;
    }

    public double getHair() {
        return hair;
    }

    public double getAcne() {
        return acne;
    }

    public double getHunger() {
        return hunger;
    }

    public double getEnergy() { return energy; }

    public void setPosition(int x, int y) {
        super.moveTo(new Point(x,y));
    }

    public void displayFood(int x, int y) {
        this.image.draw(x,y);
        this.moveTo(new Point(x-48, y-48));
    }
}
