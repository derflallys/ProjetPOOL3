package simpleUIApp;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Point2D;
import java.time.Year;
import java.util.ArrayList;
import java.util.Random;

public class Planet extends Item {
	private int numberSpaceShip;
    private static Color saveLast =null  ;
    private Color player;
    private boolean attak=false;
    public Color getPlayer() {
        return player;
    }


	public Planet(double x, double y, int w,int numberSpaceShip) {
		super(x, y, w);
		this.numberSpaceShip=numberSpaceShip;
        int color = randomColor();
        if(color==0)
        {
            this.player=Color.green;
            saveLast=Color.green;
        }
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
		if(saveLast==Color.green)
        {
            return 1;
        }else
        if(saveLast==Color.BLUE)
            return 0;


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
            while ((x==this.getLocation().getX() &&  y==this.getLocation().getY() ) ||
                    squareDistance(this.getLocation(),new Point2D.Double(x,y))>2500 ||
                    this.contains(new Point2D.Double(x,y)))
            {
                x=random.nextInt(350);
                y=random.nextInt(450);
            }
            item.add(new SpaceShip(x,y, this.getWidth()/4,this.player));
        }

    }

    @Override
    public String toString() {
        return "Planet "+toStringColor(this.player)+" nbSpaceShip: "+this.numberSpaceShip;
    }

    public void attak(ArrayList<Item> items, Item objectiv)
    {
        for (int i = 0; i <items.size() ; i++) {
            System.out.println("attak: "+items.get(i));
            if(items.get(i) instanceof SpaceShip && ((SpaceShip) items.get(i)).getColor().equals(this.player))
            {
                System.out.println(items.get(i));
                items.get(i).setObjective(objectiv);
                items.get(i).move();
            }
        }
    }

    public void setNumberSpaceShip(int numberSpaceShip) {
        this.numberSpaceShip = numberSpaceShip;
    }

    public void afterAttak(ArrayList<Item> item, Planet destination )
    {

        Random random = new Random();
        int x,y;

            if(this.getPlayer().equals(destination.getPlayer()))
            {
                destination.setNumberSpaceShip(numberSpaceShip++);

            }else if(destination.getPlayer().equals(Color.black) || (!destination.getPlayer().equals(Color.black) && destination.numberSpaceShip==0))
            {
                destination.numberSpaceShip = numberSpaceShip;

                    destination.player = this.getPlayer();

                for (int i = 0; i <numberSpaceShip ; i++)
                {
                    x=random.nextInt(350);
                    y=random.nextInt(450);

                        while ((x==this.getLocation().getX() &&  y==this.getLocation().getY() ) || squareDistance(this.getLocation(),new Point2D.Double(x,y))>2500 || this.contains(new Point2D.Double(x,y)))
                        {
                            x=random.nextInt(350);
                            y=random.nextInt(450);
                        }
                        item.add(new SpaceShip(x,y, this.getWidth()/4,this.player));
                }

            }else
            {
                destination.setNumberSpaceShip(numberSpaceShip--);
            }


        }



    }

