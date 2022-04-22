import bagel.Image;
import bagel.util.Point;
import bagel.util.Rectangle;

public class Food {
    private int weight = 0;
    private int mood = 0;
    private int hair = 0;
    private int acne = 0;

    public Food(int weight, int mood, int hair, int acne) {
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
}
