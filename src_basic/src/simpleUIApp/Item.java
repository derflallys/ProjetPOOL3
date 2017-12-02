package simpleUIApp;

import java.awt.*;
import java.awt.geom.Point2D;
import java.util.ArrayList;

/**
 * Any graphical element that will be handle by the application.
 *
 */
abstract class Item {

	protected final Point2D center;
	private final int width;
	public static ArrayList<Item> collection ;

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


	// public abstract void move();
	public void move() {};



	public abstract void draw(Graphics2D arg0);

	/*
	 * Idem
	 */
	// public abstract void setObjective(Item o);
	public void setObjective(Item item) {}


	public abstract boolean contains(Point2D p);

	public static String toStringColor(Color color)
	{

		if (Color.black.equals(color))
			return "BLACK";
		if (Color.green.equals(color))
			return "GREEN";
		if(Color.blue.equals(color))
			return "BLUE";
		return String.valueOf(color);
	}

}
