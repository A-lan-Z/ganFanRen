import bagel.Image;
import bagel.util.Point;
import bagel.util.Rectangle;

public class Character extends Rectangle {
    private static int race = 0;
    private static final Image ANGRY = new Image("res/character/Male_Angry.png");
    private static final Image BLUSH = new Image("res/character/Male_Blush.png");
    private static final Image DISGUST = new Image("res/character/Male_Disgust.png");
    private static final int WIDTH = (int) ANGRY.getWidth();
    private static final int HEIGHT = (int) ANGRY.getHeight();

    /* Attributes */
    Image image;
    private int weight = 0;
    private int mood = 0;
    private int hair = 0;
    private int acne = 0;


    public Character(Point position) {
        super(position, WIDTH, HEIGHT);
        this.image = ANGRY;
    }

    public void displayCharacter() {
        image.draw(this.centre().x, this.centre().y);
    }

    public void eat(Food food) {
        this.weight += food.getWeight();
        this.mood += food.getMood();
        this.hair += food.getHair();
        this.acne += food.getAcne();
    }

    public void updateCharacter() {
        if ((this.weight >= 3) && (this.weight < 8)) {
            this.image = BLUSH;
        } else if(this.weight >= 8) {
            this.image = DISGUST;
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
