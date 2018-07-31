import java.awt.Color;
import java.awt.Graphics;


public class Bullet extends Circle {

	private int rotation;
	public Bullet(Point center, int rotation) {
		super(center, 5);
		this.rotation = rotation;
	}

	@Override
	public void paint(Graphics brush, Color color) {
		brush.setColor(color);
		brush.fillOval((int) center.x, (int) center.y, radius, radius);
	}

	@Override
	public void move() {
		center.x += 4 * Math.cos(Math.toRadians(rotation));
		center.y += 4 * Math.sin(Math.toRadians(rotation));
	}

	public boolean outOfBound() {
		if (center.x < 0 || center.x > Asteroids.SCREEN_WIDTH ||
				center.y < 0 || center.y > Asteroids.SCREEN_HEIGHT) {
			return true;
		}
		return false;
	}
	public Point getCenter() {
		Point p = new Point(center.x, center.y);
		return p;
	}
}
