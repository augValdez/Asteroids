/**
 * A representation of a ship.
 */
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;

public class Ship extends Polygon implements KeyListener {
	public static final int SHIP_WIDTH = 40;
	public static final int SHIP_HEIGHT = 25;
	
	private boolean forward;
	private boolean right;
	private boolean left;
	private boolean shoot = false;
	private ArrayList<Bullet> bulletsShot = new ArrayList<Bullet>();

	public Ship(Point[] inShape, Point inPosition, double inRotation) {
		super(inShape, inPosition, inRotation);
	}

	// Create paint method to paint a ship
	public void paint(Graphics brush, Color color) {
		Point[] points = getPoints();
		int[] xPoints = new int[points.length];
		int[] yPoints = new int[points.length];
		int nPoints = points.length;
		for(int i = 0; i < nPoints; ++i) {
			xPoints[i] = (int) points[i].x;
			yPoints[i] = (int) points[i].y;
		}
		brush.setColor(color);
		brush.fillPolygon(xPoints, yPoints, nPoints);
	
	}

	public void move() {

		// Check forward movement
        if(forward) {
            position.x += 3 * Math.cos(Math.toRadians(rotation));
            position.y += 3 * Math.sin(Math.toRadians(rotation));
        }
       
        
        // Check rotation to right
        if(right) {
            rotate(3);
        }
        // Check rotation to left
        if(left) {
            rotate(-3);
        }
        if(position.x > Asteroids.SCREEN_WIDTH) {
            position.x -= Asteroids.SCREEN_WIDTH;
        } else if(position.x < 0) {
            position.x += Asteroids.SCREEN_WIDTH;
        }
        if(position.y > Asteroids.SCREEN_HEIGHT) {
            position.y -= Asteroids.SCREEN_HEIGHT;
        } else if(position.y < 0) {
            position.y += Asteroids.SCREEN_HEIGHT;
        }
	}

	
	public ArrayList<Bullet> getBullets() {
		return bulletsShot;
	}
	
	@Override
	public void keyPressed(KeyEvent e) {
		
		if(e.getKeyCode() == KeyEvent.VK_SPACE) {
			System.out.println("pwew");
			if (!shoot) {
				Point p = new Point(position.x, position.y);
				Bullet b = new Bullet(p, (int)rotation);
				bulletsShot.add(b);
				
			}
			
			shoot = true;
		}
		if(e.getKeyCode() == KeyEvent.VK_UP) {
			forward = true;
		}
		if(e.getKeyCode() == KeyEvent.VK_RIGHT) {
			right = true;
		}
		if(e.getKeyCode() == KeyEvent.VK_LEFT) {
			left = true;
		}
		
		
		
		
		
	}

	@Override
	public void keyReleased(KeyEvent e) {
		if(e.getKeyCode() == KeyEvent.VK_UP) {
			forward = false;
		}
		if(e.getKeyCode() == KeyEvent.VK_RIGHT) {
			right = false;
		}
		if(e.getKeyCode() == KeyEvent.VK_LEFT) {
			left = false;
		}
		if(e.getKeyCode() == KeyEvent.VK_SPACE) {
			shoot = false;
		}

	}

	@Override
	public void keyTyped(KeyEvent e) { 
		return;
	}
}
