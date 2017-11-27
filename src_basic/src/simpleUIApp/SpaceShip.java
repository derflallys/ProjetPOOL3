package simpleUIApp;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Point2D;

class SpaceShip extends Item {

	private Item objective;
	private Color color;

	public SpaceShip(double x, double y, int w,Color color) {
		super(x, y, w);
		objective = this;
		this.color=color;
	}

	public void setObjective(Item o) {
		this.objective = o;
	}

	private static double squareDistance(Point2D p1, Point2D p2) {
		double dx = p1.getX() - p2.getX();
		double dy = p1.getY() - p2.getY();
		return dx * dx + dy * dy;
	}

	@Override
	public boolean contains(Point2D p) {
		return squareDistance(this.center, p) <= (getWidth() / 2) * (getWidth() / 2);
	}

	public Color getColor() {
		return color;
	}

	public void move() {

		if (!objective.contains(this.center)) {
			double newx = center.getX();
			double newy = center.getY();
			if (newx > objective.getLocation().getX()) {
				newx--;
			} else {
				newx++;
			}
			if (newy > objective.getLocation().getY()) {
				newy--;
			} else {
				newy++;
			}
			center.setLocation(newx, newy);
		} else {

			objective = this;
		}
	}

	@Override
	public void draw(Graphics2D arg0) {
		Point2D pos = this.center;
		int x = (int) pos.getX(), y = (int) pos.getY(), w = this.getWidth();
		arg0.setColor(this.color);
		arg0.fillRect(x - w / 2, y - w / 2, w, w);
	}

	@Override
	public String toString() {
		return "SpaceShip "+toStringColor(this.color);
	}
}
