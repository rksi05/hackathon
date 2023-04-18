import java.awt.*;
public class Bullet {

    public double xpos, ypos, dx, dy, width, height, speed, damage;
    public Boolean isAlive;
    public Rectangle rec;

    public Bullet(double pxpos, double pypos){
        xpos = pxpos;
        ypos = pypos;
        dx = 0;
        dy = 0;
        width = 10;
        height = 10;
        speed = 20;
        damage = 10;
        isAlive = false;
        rec = new Rectangle((int)xpos,(int)ypos,(int)width,(int)height);

    }
    public void move(){
        xpos = xpos + dx;
        ypos = ypos + dy;
        rec = new Rectangle((int)xpos,(int)ypos,(int)width,(int)height);

    }

}
