import bagel.Image;
import bagel.util.Point;
import bagel.util.Rectangle;

public class Food extends Rectangle{
    private static final int WIDTH = 32;
    private static final int HEIGHT = 32;

    private Image image;
    private int weight = 0;
    private int mood = 0;
    private int hair = 0;
    private int acne = 0;

    public Food(Image image, int weight, int mood, int hair, int acne) {
        super(new Point(0, 0), WIDTH, HEIGHT);
        this.image = image;
        this.weight = weight;
        this.mood = mood;
        this.hair = hair;
        this.acne = acne;
    }

    public int getWeight() {
        return weight;
    }

    public int getMood() {
        return mood;
    }

    public int getHair() {
        return hair;
    }

    public int getAcne() {
        return acne;
    }

    public void setPosition(int x, int y) {
        super.moveTo(new Point(x,y));
    }

    public void displayFood(int x, int y) {
        this.image.draw(x,y);
    }
}
