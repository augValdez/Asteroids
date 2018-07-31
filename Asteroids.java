/*
Augustine Valdez
 */
import java.awt.*;
import java.util.*;
import java.awt.Graphics;

public class Asteroids extends Game {
	public static final int SCREEN_WIDTH = 800;
	public static final int SCREEN_HEIGHT = 600;

	static int counter = 0;

	private java.util.List<Asteroid> randomAsteroids = new ArrayList<Asteroid>();
	
	private Ship ship;
	
	private Star[] stars;
	
	private ArrayList<Bullet> bullets = new ArrayList<Bullet>();
	
	private Bullet b;
	
	public int lives = 3;
	
	boolean boo = false;
	

	public Asteroids() {
		super("Asteroids!",SCREEN_WIDTH,SCREEN_HEIGHT);
		this.setFocusable(true);
		this.requestFocus();

		// create a number of random asteroid objects
		randomAsteroids = createRandomAsteroids(10,60,30);
		stars = createStars(200, 3);
		// create the ship
		ship = createShip();
		this.addKeyListener(ship);

	}
	
	// private helper method to create the Ship
	private Ship createShip() {
        // Look of ship
        Point[] shipShape = {
                new Point(0, 0),
                new Point(Ship.SHIP_WIDTH/3.5, Ship.SHIP_HEIGHT/2),
                new Point(0, Ship.SHIP_HEIGHT),
                new Point(Ship.SHIP_WIDTH, Ship.SHIP_HEIGHT/2)
        };
        // Set ship at the middle of the screen
        Point startingPosition = new Point((width -Ship.SHIP_WIDTH)/2, (height - Ship.SHIP_HEIGHT)/2);
        int startingRotation = 0; // Start facing to the right
        return new Ship(shipShape, startingPosition, startingRotation);
    }

//  Create an array of random asteroids
	private java.util.List<Asteroid> createRandomAsteroids(int numberOfAsteroids, int maxAsteroidWidth,
			int minAsteroidWidth) {
		java.util.List<Asteroid> asteroids = new ArrayList<>(numberOfAsteroids);

		for(int i = 0; i < numberOfAsteroids; ++i) {
			// Create random asteroids by sampling points on a circle
			// Find the radius first.
			int radius = (int) (Math.random() * maxAsteroidWidth);
			if(radius < minAsteroidWidth) {
				radius += minAsteroidWidth;
			}
			// Find the circles angle
			double angle = (Math.random() * Math.PI * 1.0/2.0);
			if(angle < Math.PI * 1.0/5.0) {
				angle += Math.PI * 1.0/5.0;
			}
			// Sample and store points around that circle
			ArrayList<Point> asteroidSides = new ArrayList<Point>();
			double originalAngle = angle;
			while(angle < 2*Math.PI) {
				double x = Math.cos(angle) * radius;
				double y = Math.sin(angle) * radius;
				asteroidSides.add(new Point(x, y));
				angle += originalAngle;
			}
			// Set everything up to create the asteroid
			Point[] inSides = asteroidSides.toArray(new Point[asteroidSides.size()]);
			Point inPosition = new Point(Math.random() * SCREEN_WIDTH, Math.random() * SCREEN_HEIGHT);
			double inRotation = Math.random() * 360;
			asteroids.add(new Asteroid(inSides, inPosition, inRotation));
		}
		return asteroids;
	}





	// Create a certain number of stars with a given max radius
		public Star[] createStars(int numberOfStars, int maxRadius) {
			Star[] stars = new Star[numberOfStars];
			for(int i = 0; i < numberOfStars; ++i) {
				Point center = new Point(Math.random() * SCREEN_WIDTH, Math.random() * SCREEN_HEIGHT);
				int radius = (int) (Math.random() * maxRadius);
				if(radius < 1) {
					radius = 1;
				}
				stars[i] = new Star(center, radius);
			}
			return stars;
		}





	private static final int COLLISION_PERIOD = 100;

		// how we track asteroid collisions
		private boolean collision = false;
		private static int collisionTime = COLLISION_PERIOD;

	public void paint(Graphics brush) {
		brush.setColor(Color.black);
		brush.fillRect(0,0,width,height);
		counter++;
		brush.setColor(Color.white);
		brush.drawString("Counter is " + counter,10,10);
		brush.drawString("Lives Left: "+ lives, 10, 20);


		
		// display the random asteroids
		for (Asteroid asteroid : randomAsteroids) {
			asteroid.paint(brush,Color.white);
			asteroid.move();
			if(!collision) {
              collision = asteroid.collision(ship);
          }
			
			}
		for (int i = 0; i < randomAsteroids.size(); i++) {
			randomAsteroids.get(i).paint(brush, Color.white);
			randomAsteroids.get(i).move();

		}
		
		if(collision) {
          ship.paint(brush, Color.red);
          if (collisionTime == 100)
        	  lives--;
          collisionTime -= 1;
          if(collisionTime <= 0) {
              collision = false;
              collisionTime = COLLISION_PERIOD;
          }
      } else {
          ship.paint(brush, Color.magenta);
      }
		
		// have the ship appear on the screen
//		ship.paint(brush, Color.magenta);
		
		ship.move();
		
		if (counter%25 == 0) {
			boo = !boo;
		}
		for (Star star : stars) {
			star.paint(brush,Color.yellow);
			if (boo)
			star.paint(brush,Color.black);
			
		}
		
		
		
		
		
		
		ArrayList<Bullet> bulletsremove = new ArrayList<Bullet>();
		ArrayList<Asteroid> asteroidremove = new ArrayList<Asteroid>();
		
		bullets = ship.getBullets();
		for(int i = 0; i<bullets.size(); i++) {
			bullets.get(i).paint(brush, Color.white);
			bullets.get(i).move();
			if(bullets.get(i).outOfBound()) {
				bulletsremove.add(bullets.get(i));

			}
			for(int j = 0; j<randomAsteroids.size(); j++) {
				if(randomAsteroids.get(j).contains(bullets.get(i).getCenter())) {
					asteroidremove.add(randomAsteroids.get(j));
					bulletsremove.add(bullets.get(i));

				}
			}
		}
		for(int i = 0; i<asteroidremove.size(); i++) {
			randomAsteroids.remove(asteroidremove.get(i));
		}
		
		for(int i = 0; i<bulletsremove.size(); i++) {
			bullets.remove(bulletsremove.get(i));
		}
		
		
		if(randomAsteroids.isEmpty()) {
			brush.setColor(Color.black);
			brush.fillRect(0,0,width,height);
			brush.setColor(Color.white);
			brush.drawString("You won", 390, 290);
			on = false;
		}
		if(lives == 0) {
			brush.setColor(Color.black);
			brush.fillRect(0,0,width,height);
			brush.setColor(Color.white);
			brush.drawString("You lost", 390, 290);
			on = false;
		}
	}

	public static void main (String[] args) {
		Asteroids a = new Asteroids();
		a.repaint();
	}
}