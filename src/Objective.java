import java.awt.*;

public class Objective {
    public double xpos, ypos,height, width, hitpoints, maxhitpoints;
    public Rectangle rec;
    public boolean isAlive;
    public Objective(double phitpoints, double pmaxhitpoints){
        xpos = 450;
        ypos = 50;
        height = 50;
        width = 100;
        hitpoints = phitpoints;
        maxhitpoints = pmaxhitpoints;
        isAlive = true;
        rec = new Rectangle((int)xpos,(int)ypos,(int)width,(int)height);


    }
}
