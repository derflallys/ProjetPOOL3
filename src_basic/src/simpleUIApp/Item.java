package simpleUIApp;

import java.awt.*;
import java.awt.geom.Point2D;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Random;

/**
 * Any graphical element that will be handle by the application.
 *
 */
abstract class Item implements Serializable {

	protected final Point2D center;
	private final int width;
	public static transient ArrayList<Item> collection ;

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


	public abstract void move();


	public static  int numberPlanet()
	{
		int number=0;
        for (Item item:Item.collection) {
            if(item instanceof Planet)
                number++;
        }
        return number;
    }

    public static  int numberSpaceShip()
    {
        int number=0;
        for (Item item:Item.collection) {
            if(item instanceof SpaceShip)
                number++;
        }
        return number;
    }

	public abstract void draw(Graphics2D arg0);

	/*
	 * Idem
	 */
	public abstract void setObjective(Item o);



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

	@Override
	public boolean equals(Object o) {
		if (o==null)
			return false;
		if(o==this)
			return true;
		if(o instanceof Item)
		{
			if (this.center.equals(((Item) o).center) && this.width==((Item) o).width)
				return true;
		}
		return false;
	}


	public static ArrayList<Item>  createPlanet()
    {
        int x, y;
        ArrayList<Item> testItemList = new ArrayList<>();
        Random random = new Random();
        for (int i = 0; i < 25; i++) {
            x = random.nextInt(700);
            y = random.nextInt(800);
            if (!testItemList.isEmpty()) {

                for (int j = 0; j < testItemList.size(); j++) {

                    while (x == testItemList.get(j).getLocation().getX() && y == testItemList.get(j).getLocation().getY() || testItemList.get(j).contains(new Point2D.Double(x, y))) {
                        x = random.nextInt(700);
                        y = random.nextInt(800);
                    }

                }
            }

            testItemList.add(new Planet(x, y, 40));

        }
        return testItemList;
    }
}
