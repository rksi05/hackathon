import java.awt.*;
public class Fighter {
    public double xpos, ypos, dx, dy, height, width, hitpoints;
    public double speed;
    public boolean isAlive;
    public Rectangle rec;
    public Fighter(double pxpos, double pypos, double pspeed){
        xpos = pxpos;
        ypos = pypos;
        dx = 0;
        dy = 0;
        height = 50;
        width = 20;
        hitpoints = 1;
        isAlive = true;
        rec = new Rectangle((int)xpos,(int)ypos,(int)width,(int)height);
        speed = pspeed;

    }

    public void teleport(double x, double y){
        xpos = x;
        ypos = y;

        rec = new Rectangle((int)xpos,(int)ypos,(int)width,(int)height);
    }

    public void move(){
        xpos = xpos + dx;
        ypos = ypos + dy;
        rec = new Rectangle((int)xpos,(int)ypos,(int)width,(int)height);

    }





}





