package simpleUIApp;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Point2D;
import java.io.Serializable;
import java.util.Random;

class SpaceShip extends Item  implements Serializable{

	private Item objective;
	private Color color;
	private int speed;
	private int timeProduction;
	private int power;
	private int IDPlanet ;


	public SpaceShip(double x, double y, int w, Color color,int IDPlanet) {
		super(x, y, w);
		objective = this;
		this.color=color;
		this.speed = 8;
		this.timeProduction = 5;
		setRandPower();
		this.IDPlanet= IDPlanet;
	}

	public void setRandPower() {
		Random random = new Random();
		int power = random.nextInt(3);
		while(power==0)
		{
			power=random.nextInt(3);
		}
		this.power = power;
	}

	public int getPower() {
		return power;
	}

	public void setObjective(Item o) {
		this.objective = o;
	}

	private static double squareDistance1(Point2D p1, Point2D p2) {
		double dx = p1.getX() - p2.getX();
		double dy = p1.getY() - p2.getY();
		return dx * dx + dy * dy;
	}

	@Override
	public boolean contains(Point2D p) {
        double w = getWidth() / 2;
        return (this.center.getX() - w <= p.getX() && p.getX() <= this.center.getX() + w)
                && (this.center.getY() - w <= p.getY() && p.getY() <= this.center.getY() + w);
	}

	public Color getColor() {
		return color;
	}

	public void setColor(Color color) {
		this.color = color;
	}

	public Item getObjective() {
		return objective;
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
			Point2D.Double newposition = checkOverPlanet(newx,newy);
			center.setLocation(newposition.x, newposition.y);
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

    public int getIDPlanet() {
        return IDPlanet;
    }

    @Override
	public String toString() {
		return "SpaceShip "+toStringColor(this.color)+" From "+this.IDPlanet;
	}

	public   Point2D.Double checkOverPlanet (double x,double y)
	{
		for (Item item:collection) {
			if(item instanceof Planet &&  !item.equals(objective) )
			{

                if( item.contains(new Point2D.Double(x+getWidth(),y)) || item.contains(new Point2D.Double(x-getWidth(),y)) )
                {

                    if( !item.contains(new Point2D.Double(x,y+getWidth())) && y+getWidth()<850 )
                    {
                        //System.out.println("Dans x +y");
                        y+=getWidth()+5;
                    }

                    if (!item.contains(new Point2D.Double(x,y-getWidth())) && y-getWidth()<850)
                    {
                        //System.out.println("Dans x -y");
                        y-=getWidth()+5;
                    }


                }
                else
                if( item.contains(new Point2D.Double(x,y+getWidth()))  ||item.contains(new Point2D.Double(x,y-getWidth())) )
                {

                    if( !item.contains(new Point2D.Double(x+getWidth(),y)) && x+getWidth()<750 )
                    {
                        x+=getWidth()+5;
                        //System.out.println("dans y: +x");
                    }

                    if (!item.contains(new Point2D.Double(x-getWidth(),y)) || x-getWidth()<750)
                    {
                        //System.out.println("Dans y : -x");
                        x-=getWidth()+5;
                    }


                }



			}
		}
		return new Point2D.Double(x,y);
	}



	@Override
	public boolean equals(Object o) {
		if(o==null)
			return false;
		if(this==o)
			return true;
		if(o instanceof SpaceShip)
		{
			if(this.objective.equals(((SpaceShip) o).objective) && this.color.equals(((SpaceShip) o).color) && super.equals(o))
				return true;
		}
		return false;
	}
}
