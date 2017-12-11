package simpleUIApp;

import fr.ubordeaux.simpleUI.KeyPress;

import java.awt.*;
import java.awt.geom.Point2D;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Random;

/**
 * Any graphical element that will be handle by the application.
 *
 */
abstract class Item implements Serializable  {

	protected final Point2D center;
	private final int width;
    /**
     * a collection containing all the planets and ships of the game
     */
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

    /**
     *this synchronized method allows the user to play himself with his opponent as parameter
     * @param player
     */
	public synchronized static void IAPlayer(Color player)
    {
        System.out.println("Begin "+toStringColor(player));
        Random random = new Random();
        int pos = random.nextInt(Item.collection.size());
        while (Item.collection.get(pos) instanceof SpaceShip || ((Planet) Item.collection.get(pos)).getPlayer().equals(Color.black) || ((Planet) Item.collection.get(pos)).getPlayer().equals(player))
            pos = random.nextInt(Item.collection.size());
        int posobject = random.nextInt(Item.collection.size());
        if (Item.collection.get(pos) instanceof Planet) {
            while (Item.collection.get(posobject) instanceof SpaceShip || Item.collection.get(posobject).equals(Item.collection.get(pos)))
                posobject = random.nextInt(Item.collection.size());

            int kpres = random.nextInt(3);
            KeyPress press = KeyPress.UNKNOWN;
            if (kpres == 0)
                press = KeyPress.CRTL;
            if (kpres == 1)
                press = KeyPress.ALTGR;
            if (kpres == 2)
                press = KeyPress.SHIFT;

            if (!(Item.collection.get(posobject) instanceof SpaceShip)) {
                ((Planet) Item.collection.get(pos)).attak(Item.collection.get(posobject), press);

                System.out.println("Drag& Drop :" + Item.collection.get(pos) + " => " + Item.collection.get(posobject) + " using " + press.toString());
                try {
                    Thread.sleep(10000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

        }
    }



    public abstract void draw(Graphics2D arg0);

	/*
	 * Idem
	 */
	public abstract void setObjective(Item o);



	public abstract boolean contains(Point2D p);

    /**
     *converts our colors used into strings of characters
     * @param color
     * @return the string corresponding to the color (BLUE - GREEN - BLACK)
     */
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

    /**
     *Calculate the radius of cirlce
     * @return
     */
	public double getRayon()
	{
		return Math.sqrt(Math.pow(getWidth(),2) + Math.pow(getWidth(),2)  ) /2;
	}

    /**
     * Used to compare two Items based on these coordinates and its width
     * @param o
     * @return true ou false
     */
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

    protected static int squareDistance(Point2D p1, Point2D p2) {

        double dx = p1.getX() - p2.getX();
        double dy = p1.getY() - p2.getY();
        return (int) (dx * dx + dy * dy);

    }

    /**
     * allows you to remove ships that have reached their target
     */
    public static void eraseAfterAttak()
    {
        for (int i = 0; i <Item.collection.size() ; i++) {
            if(Item.collection.get(i) instanceof SpaceShip && ((SpaceShip) Item.collection.get(i)).getObjective().contains(Item.collection.get(i).center))
                Item.collection.remove(i);
        }
    }

    /**
     * allows to determine the end of the game
     * @return Color.RED if one is at the end otherwise return the color of the winning player
     */
    public static Color EndOfGame() {
        Color playerWon = null;
        for (int j = 0; j < 2; j++) {
            boolean onecolor = true;
            for (int i = 0; i < Item.collection.size(); i++) {
                if (Item.collection.get(i) instanceof Planet) {
                    if (j == 0) {
                        if (!((Planet) Item.collection.get(i)).getPlayer().equals(Color.BLUE)) {
                            onecolor = false;
                            break;

                        }

                    }

                    if (j == 1) {
                        if (!((Planet) Item.collection.get(i)).getPlayer().equals(Color.green)) {
                            onecolor = false;
                        }

                    }


                }

            }
            if (onecolor == true) {
                if (j == 0)
                    return Color.BLUE;
                else
                    return  Color.green;
            }
        }
        return Color.RED;

    }

    /**
     * Allows you to create a list of planets of all colors
     * @param number of planet to create
     * @return list of planet
     */
	public static ArrayList<Item>  createPlanet(int number )
    {
        if (number<=5)
            number=10;
        int x, y;
        ArrayList<Item> testItemList = new ArrayList<>();
        Random random = new Random();
        for (int i = 0; i < number; i++) {
            x = random.nextInt(750);
            y = random.nextInt(850);
            if (!testItemList.isEmpty()) {

                for (int j = 0; j < testItemList.size(); j++) {

                    while ((x == testItemList.get(j).getLocation().getX() && y== testItemList.get(j).getLocation().getY()) ||
                            testItemList.get(j).contains(new Point2D.Double(x-30, y-30)) ||
                            testItemList.get(j).contains(new Point2D.Double(x+30, y+30)) ||
                            testItemList.get(j).contains(new Point2D.Double(x-30, y+30)) ||
                            testItemList.get(j).contains(new Point2D.Double(x+30, y-30)) ||
                            testItemList.get(j).contains(new Point2D.Double(x, y)) ||
                            x<=30 || y<=20 ) {
                        x = random.nextInt(750);
                        y = random.nextInt(850);

                    }

                }
            }

            testItemList.add(new Planet(x, y, 60));

        }
        return testItemList;
    }

    /**
     *create a neutral planet list and add them to Item.collection
     */
    public  static  void createPlanetBlack()
    {
        int x, y;

        Random random = new Random();
        for (int i = 0; i < 4; i++) {
            x = random.nextInt(750);
            y = random.nextInt(850);
            if (!Item.collection.isEmpty()) {

                for (int j = 0; j < Item.collection.size(); j++) {

                    while ((x == Item.collection.get(j).getLocation().getX() && y== Item.collection.get(j).getLocation().getY()) ||
                            Item.collection.get(j).contains(new Point2D.Double(x-30, y-30)) ||
                            Item.collection.get(j).contains(new Point2D.Double(x+30, y+30)) ||
                            Item.collection.get(j).contains(new Point2D.Double(x-30, y+30)) ||
                            Item.collection.get(j).contains(new Point2D.Double(x+30, y-30)) ||
                            Item.collection.get(j).contains(new Point2D.Double(x, y)) ||
                            x<=30 || y<=20 ) {
                        x = random.nextInt(750);
                        y = random.nextInt(850);

                    }

                }
            }

            Item.collection.add(new Planet(x, y, 60,Color.black));

        }
    }
}
