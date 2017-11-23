package simpleUIApp;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Point2D;

public class Planet extends Item {

	public Planet(double x, double y, int w) {
		super(x, y, w);

	}

	public void draw(Graphics2D g) {
		Point2D pl = this.center;
		int x = (int) pl.getX();
		int y = (int) pl.getY();
		g.setColor(Color.green);
		// g.fillRoundRect(x, y, width, height, arcWidth, arcHeight);
		g.fillRoundRect(x, y, 70, 70, 1, 1);

	}

	private int squareDistance(Point2D p1, Point2D p2) {
	
		double dx = p1.getX() - p2.getX();
		double dy = p1.getY() - p2.getY();
		return (int) (dx * dx + dy * dy);

	}

	public boolean contains(Point2D p) {
		return squareDistance(this.center, p) <= (getWidth() / 2) * (getWidth() / 2);

	}

}
