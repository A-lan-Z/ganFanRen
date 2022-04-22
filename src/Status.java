import bagel.util.*;
import bagel.*;
import java.util.*;

public class Status {
    private Point position;
    private Font font;
    private boolean isPercentage;
    private String name;

    public Status(Point position, String name, boolean isPercentage) {
        this.position = position;
        this.font = new Font("./res/wheaton.otf", 20);
        this.isPercentage = isPercentage;
        this.name = name;
    }

    public void displayStatus(double curStatus) {
        String message = name + ": " + Math.round(curStatus);
        if (isPercentage) message += "%";
        font.drawString(message, position.x, position.y);
    }

}