package simpleUIApp;

import java.awt.Graphics2D;
import java.awt.geom.Point2D;

/**
 * Any graphical element that will be handle by the application.
 *
 */
abstract class Item {

	protected final Point2D center;
	private final int width;

	public Item(double x, double y, int w) {
		center = new Point2D.Double(x, y);
		width = w;
	}

	public Point2D getLocation() {
		return center;
	}

	public int getWidth() {
		return width;
	}

	/*
	 * we remove the abstract look of the move method here so that only the
	 * Spaceship class can benefit from it
	 */

	// public abstract void move();
	public void move() {

	}

	public abstract void draw(Graphics2D arg0);

	/*
	 * Idem
	 */
	// public abstract void setObjective(Item o);
	public void setObjective(Item item) {

	}

	public abstract boolean contains(Point2D p);

}
