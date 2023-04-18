//Basic Game Application
//Version 2
// Basic Object, Image, Movement
// Astronaut moves to the right.
// Threaded

//K. Chun 8/2018

//*******************************************************************************
//Import Section
//Add Java libraries needed for the game
//import java.awt.Canvas;

//Graphics Libraries
import java.awt.Graphics2D;
import java.awt.event.*;
import java.awt.image.BufferStrategy;
import java.awt.*;
import java.io.File;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.sound.sampled.*;

import java.awt.event.KeyListener;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import java.awt.Graphics2D;
import java.awt.event.*;
import java.awt.image.BufferStrategy;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.sound.sampled.*;

public class BasicGameApp implements Runnable, KeyListener, MouseMotionListener, MouseListener {
	public int LEVEL = 1;
	final int WIDTH = 1000;
	final int HEIGHT = 700;
	public JFrame frame;
	public Canvas canvas;
   public JPanel panel;
   public Fighter jet;
   public Bullet[] mag;
   public SmallInvader[] swarm;
   public Bomb[] bombs;
   public Objective headquarters;
   public Image jet_picture;
   public Image small_invader_picture;
   public Image HQ_picture;
   public Image bomb_picture;
   public Image space_picture;
	public int bullet_count = 0;
	public int bomb_count = 0;
	public int bombtime=0;

   public BufferStrategy bufferStrategy;
   public boolean hasdropped=false;
   public boolean small_invader_alive = false;
   public boolean hasStarted=false;

   public boolean hasLost = false;

   public static void main(String[] args) {
		BasicGameApp ex = new BasicGameApp();   //creates a new instance of the game
		new Thread(ex).start();                 //creates a threads & starts up the code in the run( ) method
	}

	public void playSound(String name) {

		try {

			// Open an audio input stream.
			File soundFile = new File(name); //you could also get the sound file with an URL
			AudioInputStream audioIn = AudioSystem.getAudioInputStream(soundFile);
			// Get a sound clip resource.
			Clip clip = AudioSystem.getClip();
			// Open audio clip and load samples from the audio input stream.
			clip.open(audioIn);
			clip.start();   //start playing the sound
		}
		catch (UnsupportedAudioFileException e) {
			e.printStackTrace();
		}
		catch (IOException e) {
			e.printStackTrace();
		}
		catch (LineUnavailableException e) {
			e.printStackTrace();
		}

	}

	public BasicGameApp() {
      
      setUpGraphics();

      //variable and objects
      //create (construct) the objects needed for the game and load up
		jet_picture = Toolkit.getDefaultToolkit().getImage("jet.png");
		small_invader_picture = Toolkit.getDefaultToolkit().getImage("sinvader.png");
		HQ_picture = Toolkit.getDefaultToolkit().getImage("HQ.jpeg");
		bomb_picture = Toolkit.getDefaultToolkit().getImage("bomb.jpeg");
		space_picture = Toolkit.getDefaultToolkit().getImage("space.jpeg");
		playSound("pop_song.wav");

		jet = new Fighter(500,350, 3);

		mag = new Bullet[100];
		for (int i=0;i<mag.length;i++){
			mag[i] = new Bullet(-100,-100);
		}

		swarm = new SmallInvader[20];
		for (int i = 0;i<swarm.length;i++){
			swarm[i] = new SmallInvader(50*i , 100, 2);
		}

		bombs = new Bomb[100];
		for (int i = 0;i<bombs.length;i++){
			bombs[i] = new Bomb(-200,-200, 5);
		}

		headquarters = new Objective(250, 250);

	}

	public void run() {
		//USE ARRAY STEP 4

      //for the moment we will loop things forever.
		while (true) {
			gameOver();

			if (hasStarted){
			moveThings();
			checkIntersections();
		}
         render();  // paint the graphics
         pause(10);// sleep for 10 ms=

         if (hasStarted){
			 bombDrops();
			 Levels();
		 }
		}
	}
	public void moveThings() {
	for (int i =0;i<mag.length;i++) {
		mag[i].move();
	}
	for (int i =0;i<swarm.length;i++) {
		swarm[i].move();
		swarm[i].wrap();
	}
	for (int i =0;i<bombs.length;i++) {
		bombs[i].move();
	}
	jet.move();

}

public void CheckSmallInvaderAlive(){
	 small_invader_alive = false;
	 for (int i=0;i<swarm.length;i++){
		 if (swarm[i].isAlive == true){
			 small_invader_alive = true;
		 }
	 }


}
public void bombDrops(){
	   CheckSmallInvaderAlive();
	   if (small_invader_alive == true){
		   bombtime++;
		   if(bombtime==30-2*LEVEL){
			   bombtime=0;
			   while(hasdropped==false){
				   int x=(int)(Math.random()*swarm.length);
				   if(swarm[x].isAlive==true){
					   bombs[bomb_count].xpos=swarm[x].xpos;
					   bombs[bomb_count].ypos=swarm[x].ypos;
					   bombs[bomb_count].dy= bombs[bomb_count].speed;
					   hasdropped=true;
					   bomb_count++;
					   if(bomb_count == bombs.length - 1){
						   bomb_count = 0;
					   }
				   }

			   }
			   hasdropped = false;
		   }
	   }
}

public void Levels(){
	   if (headquarters.hitpoints <= 0){
		   System.out.println("checking");
		   pause(50);
		   hasStarted = false;
		   LEVEL++;
		   jet = new Fighter(500,350, 3);

		   mag = new Bullet[1000];
		   for (int i=0;i<mag.length;i++){
			   mag[i] = new Bullet(-100,-100);
		   }

		   swarm = new SmallInvader[20 + 10*(LEVEL-1)];
		   for (int i = 0;i<swarm.length;i++){
			   swarm[i] = new SmallInvader((1000/(20 + 10*(LEVEL-1))+10)*i , 100, 2+(1*LEVEL));
		   }

		   bombs = new Bomb[1000];
		   for (int i = 0;i<bombs.length;i++){
			   bombs[i] = new Bomb(-200,-200, 5+ (2.5*LEVEL));
		   }

		   headquarters = new Objective(250, 250);

	   }
}


public void gameOver(){
	   for (int i =0;i< swarm.length;i++){
		   if (jet.isAlive == false){
			   hasLost = true;
		   }
	   }
}



	public void checkIntersections(){

	   for (int a = 0;a< mag.length;a++){
		   for (int b = 0;b< swarm.length;b++){
			   if(mag[a].rec.intersects(swarm[b].rec) && mag[a].isAlive == true && swarm[b].isAlive == true){
				   mag[a].isAlive = false;
				   swarm[b].isAlive = false;
			   }
		   }
	   }
	   for (int a=0;a<bombs.length;a++){
		   if(bombs[a].rec.intersects(jet.rec) && jet.isAlive == true){
			   jet.isAlive = false;
		   }
	   }
	   for (int a = 0;a< mag.length;a++){
			if(mag[a].rec.intersects(headquarters.rec) && mag[a].isAlive == true && headquarters.hitpoints > 0){
				headquarters.hitpoints = headquarters.hitpoints - mag[a].damage;
				mag[a].isAlive = false;
			}
		}
	   for(int a = 0;a<swarm.length;a++){
		   if(swarm[a].rec.intersects(jet.rec)&&swarm[a].isAlive==true){
			   jet.isAlive = false;
		   }

		}
	}
	public void pause(int time ){
   		//sleep
			try {
				Thread.sleep(time);
			} catch (InterruptedException e) {

			}
   }
   private void setUpGraphics() {
      frame = new JFrame("Application Template");   //Create the program window or frame.  Names it.
   
      panel = (JPanel) frame.getContentPane();  //sets up a JPanel which is what goes in the frame
      panel.setPreferredSize(new Dimension(WIDTH, HEIGHT));  //sizes the JPanel
      panel.setLayout(null);   //set the layout
   
      // creates a canvas which is a blank rectangular area of the screen onto which the application can draw
      // and trap input events (Mouse and Keyboard events)
      canvas = new Canvas();  
      canvas.setBounds(0, 0, WIDTH, HEIGHT);
      canvas.setIgnoreRepaint(true);
	  canvas.addKeyListener(this);
	  canvas.addMouseListener(this);
	  canvas.addMouseMotionListener(this);
   
      panel.add(canvas);  // adds the canvas to the panel.
   
      // frame operations
      frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  //makes the frame close and exit nicely
      frame.pack();  //adjusts the frame and its contents so the sizes are at their default or larger
      frame.setResizable(false);   //makes it so the frame cannot be resized
      frame.setVisible(true);      //IMPORTANT!!!  if the frame is not set to visible it will not appear on the screen!
      
      // sets up things so the screen displays images nicely.
      canvas.createBufferStrategy(2);
      bufferStrategy = canvas.getBufferStrategy();
      canvas.requestFocus();
      System.out.println("DONE graphic setup");
   
   }
   private void render() {
		Graphics2D g = (Graphics2D) bufferStrategy.getDrawGraphics();
		g.clearRect(0, 0, WIDTH, HEIGHT);
		//g.drawImage(space_picture, 0,0,1000,700,null);


if (hasStarted == true && hasLost == false){
	g.drawImage(space_picture,0,0,1000,700,null);
	if (jet.isAlive == true){
		g.drawImage(jet_picture, (int)jet.xpos, (int)jet.ypos, (int)jet.width, (int)jet.height, null);
	}
	for (int i=0;i<mag.length;i++){
		g.setColor(new Color(255,50,100));
		if (mag[i].isAlive == true){
			g.fillRect((int)mag[i].xpos,(int)mag[i].ypos,10,10);
		}
	}
	for (int i=0;i<swarm.length;i++){
		if (swarm[i].isAlive == true){
			g.drawImage(small_invader_picture,(int)swarm[i].xpos,(int)swarm[i].ypos,(int)swarm[i].width, (int)swarm[i].height,null);
		}
	}
	if (headquarters.hitpoints > 0){
		g.drawImage(HQ_picture,(int)headquarters.xpos,(int)headquarters.ypos,(int)headquarters.width, (int)headquarters.height,null);
		g.setColor(Color.red);
		g.fillRect((int)headquarters.xpos, (int)headquarters.ypos - 10,(int)headquarters.width, 5 );
		g.setColor(Color.green);
		g.fillRect((int)headquarters.xpos, (int)headquarters.ypos - 10,(int)(headquarters.width*(headquarters.hitpoints/headquarters.maxhitpoints)), 5);
	}

	for (int i=0;i<bombs.length;i++){
		g.drawImage(bomb_picture,(int)bombs[i].xpos,(int)bombs[i].ypos,(int)bombs[i].width, (int)bombs[i].height,null);
	}
}if (hasStarted == false && hasLost == false){
	g.setColor(Color.PINK);
	g.fillRect(350,200,300,100);
	g.setColor(Color.BLACK);
	if (LEVEL>1){

		g.drawString("Conratulations on beating level "+(LEVEL-1)+"!", 450, 600);
	}
	g.drawString("Click to start level "+LEVEL, 450, 250);
	if (LEVEL == 1) {

		g.drawString("Win by shooting down the headquarters!", 450, 100);
	}



}
if (hasLost){
	g.drawString("GAME OVER", 450, 250);

}


		g.dispose();

		bufferStrategy.show();
	}


	@Override
	public void keyTyped(KeyEvent e) {

	}

	@Override
	public void keyPressed(KeyEvent e) {

		if (hasStarted){
			int keycode = e.getKeyCode();
			if (keycode == 37 || keycode == 65){
				jet.dx = - jet.speed;
			}
			if (keycode == 38 || keycode == 87){
				jet.dy = - jet.speed;
			}
			if (keycode == 39 || keycode == 68){
				jet.dx = jet.speed;
			}
			if (keycode == 40 || keycode == 83){
				jet.dy = jet.speed;
			}
		}

	}

	@Override
	public void keyReleased(KeyEvent e) {
		if (hasStarted){
			int keycode = e.getKeyCode();
			if (keycode == 37 || keycode == 65){
				jet.dx = 0;
			}
			if (keycode == 38 || keycode == 87){
				jet.dy = 0;
			}
			if (keycode == 39 || keycode == 68){
				jet.dx = 0;
			}
			if (keycode == 40 || keycode == 83){
				jet.dy = 0;
			}
		}

	}

	@Override
	public void mouseClicked(MouseEvent e) {

			int x = e.getX();
			int y = e.getY();
			if (x > 350 && x < 650 && y > 200 && y < 300 && hasStarted == false){
				pause(50);
				hasStarted = true;

			}




	}

	@Override
	public void mousePressed(MouseEvent e) {
		if (hasStarted){
			if (jet.isAlive == true){
				playSound("laser_shot.wav");
				bullet_count++;
				mag[bullet_count].isAlive = true;
				mag[bullet_count].xpos = jet.xpos;
				mag[bullet_count].ypos = jet.ypos;
				double x = e.getX();
				double y = e.getY();
				double xdif = x - jet.xpos;
				double ydif = y - jet.ypos;
				double dist = Math.sqrt(xdif * xdif + ydif * ydif);
				mag[bullet_count].dx = xdif / (dist / mag[bullet_count].speed);
				mag[bullet_count].dy = ydif / (dist / mag[bullet_count].speed);
				mag[bullet_count].isAlive = true;

				if (bullet_count == bullet_count - 1){
					bullet_count = 0;
				}
			}
		}

	}

	@Override
	public void mouseReleased(MouseEvent e) {

	}
	@Override
	public void mouseEntered(MouseEvent e) {

	}

	@Override
	public void mouseExited(MouseEvent e) {

	}

	@Override
	public void mouseDragged(MouseEvent e) {

	}

	@Override
	public void mouseMoved(MouseEvent e) {
	}
}