import java.awt.*;
public class SmallInvader {

    public double xpos, ypos, dx, dy, width, height, speed, hitpoints;
    public boolean isAlive;
    public Rectangle rec;

    public SmallInvader(double pxpos, double pypos, double pdx){
        xpos = pxpos;
        ypos = pypos;
        dx = pdx;
        dy = 0;
        speed = 10;
        width = 20;
        height = 20;
        isAlive = true;
        hitpoints = 1;
        rec = new Rectangle((int)xpos,(int)ypos,(int)width,(int)height);

    }

    public void move(){
        xpos = xpos + dx;
        ypos = ypos + dy;
        rec = new Rectangle((int)xpos,(int)ypos,(int)width,(int)height);

    }

    public void wrap(){
        if (xpos > 1000 || xpos < 0){
           dx = dx*-1;
           ypos = ypos + 2*height;
        }
    }




}
