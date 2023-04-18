import java.awt.*;

public class Bomb {
    public double xpos, ypos, dx, dy, width, height, speed, damage;
    public Boolean isAlive;
    public Rectangle rec;
    public int count = 0;

    public Bomb(double pxpos, double pypos, double pspeed){
        xpos = pxpos;
        ypos = pypos;
        dx = 0;
        dy = 0;
        width = 12;
        height = 20;
        speed = pspeed;
        damage = 50;
        isAlive = false;
        rec = new Rectangle((int)xpos,(int)ypos,(int)width,(int)height);

    }
    public void move(){
        xpos = xpos + dx;
        ypos = ypos + dy;
        rec = new Rectangle((int)xpos,(int)ypos,(int)width,(int)height);

    }

}
