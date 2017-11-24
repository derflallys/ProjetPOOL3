package simpleUIApp;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Point2D;
import java.time.Year;
import java.util.ArrayList;
import java.util.Random;

public class Planet extends Item {
	private int numberSpaceShip;

    public Color getPlayer() {
        return player;
    }

    private Color player;
	public Planet(double x, double y, int w,int numberSpaceShip) {
		super(x, y, w);
		this.numberSpaceShip=numberSpaceShip;
        int color = randomColor();
        if(color==0)
            this.player=Color.green;
        else
        if(color==1)
            this.player=Color.blue;
        else
        if(color==2)
            this.player=Color.black;
	}
	private int randomColor()
	{
		//Number 0 GREED

		//Number 1 BLUE

		//Number 2 BLACK
		Random rand =new Random();

		return rand.nextInt(3);

	}

	public void draw(Graphics2D g) {
		Point2D pl = this.center;
		int x = (int) pl.getX();
		int y = (int) pl.getY();
		int w = getWidth();
        //System.out.println(randomColor());

        g.setColor(this.player);

		g.fillRect(x - w / 2, y - w / 2, w, w);

	}

	private int squareDistance(Point2D p1, Point2D p2) {
	
		double dx = p1.getX() - p2.getX();
		double dy = p1.getY() - p2.getY();
		return (int) (dx * dx + dy * dy);

	}

	public boolean contains(Point2D p) {
		return squareDistance(this.center, p) <= (getWidth() / 2) * (getWidth() / 2);

	}

	public void createShapeShip(ArrayList<Item> item)
    {
        Random random = new Random();
        int x,y;


        for (int i = 0; i <this.numberSpaceShip ; i++) {
            x=random.nextInt(350);
            y=random.nextInt(450);
            while (x==this.getLocation().getX() &&  y==this.getLocation().getY())
            {
                x=random.nextInt(350);
                y=random.nextInt(450);
            }
            item.add(new SpaceShip(x,y, this.getWidth()/4,this.player));
        }

    }

}
