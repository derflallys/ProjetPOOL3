package simpleUIApp;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Random;

public class Planet extends Item {
	private int numberSpaceShip;
    private static Color saveLast =null  ;
    private Color player;



    public Color getPlayer() {
        return player;
    }



    public Planet(double x, double y, int w) {
		super(x, y, w);

        int color = randomColor();
        if(color==0)
        {
            this.player=Color.green;
            saveLast=Color.green;
        }
        else
        if(color==1)
        {
            this.player=Color.blue;
            saveLast=Color.blue;
        }

        else
        if(color==2)
        {
            this.player=Color.black;
            saveLast=Color.black;
        }


        if(!this.player.equals(Color.black))
            this.numberSpaceShip=10;
        else
            this.numberSpaceShip=0;
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
        g.setColor(Color.WHITE);

        g.drawString(numberSpaceShip+"",(int)this.getLocation().getX(),(int)this.getLocation().getY());

	}

	private int squareDistance(Point2D p1, Point2D p2) {
	
		double dx = p1.getX() - p2.getX();
		double dy = p1.getY() - p2.getY();
		return (int) (dx * dx + dy * dy);

	}

	public boolean contains(Point2D p) {
		return squareDistance(this.center, p) <= (getWidth() / 2) * (getWidth() / 2);

	}

	public void createShapeShip(ArrayList<Item> item,Item objectif)
    {
        Random random = new Random();
        int x,y;
        SpaceShip spaceShip;

        for (int i = 0; i <this.numberSpaceShip ; i++) {
            x=random.nextInt(750);
            y=random.nextInt(850);
            while ((x==this.getLocation().getX() &&  y==this.getLocation().getY() ) ||
                    squareDistance(this.getLocation(),new Point2D.Double(x,y))>2500 ||
                    this.contains(new Point2D.Double(x,y)))
            {
                x=random.nextInt(750);
                y=random.nextInt(850);
            }
            spaceShip = new SpaceShip(x,y, this.getWidth()/4,this.player);
            spaceShip.setObjective(objectif);
            Item.collection.add(spaceShip);
            item.add(spaceShip);
        }

    }

    public int getNumberSpaceShip() {
        return numberSpaceShip;
    }

    @Override
    public String toString() {
        return "Planet "+toStringColor(this.player)+" nbSpaceShip: "+this.numberSpaceShip;
    }

    public void attak(Item objectiv)
    {

        ArrayList<Item> spaceShips = new ArrayList<>();
        ArrayList<Item> items = new ArrayList<>();
        //je cree les vaisseaux lors d'un attak et je les mets dans un item puis je fais l'attak avec ceux la  ne pas faire le setobjectiv dans planet

        if(!this.player.equals(Color.black))
        {
            for (int i = 0; i <this.numberSpaceShip ; i++) {
                this.createShapeShip(spaceShips,objectiv);
            }
        }
        this.afterAttak(items, (Planet) objectiv,spaceShips);
    }

    public void setNumberSpaceShip(int numberSpaceShip) {
        this.numberSpaceShip = numberSpaceShip;
    }

    public void afterAttak(ArrayList<Item> item, Planet destination,ArrayList<Item> myspaceShips )
    {

        Random random = new Random();
        int x,y;

            if(!this.player.equals(Color.black) && this.getPlayer().equals(destination.getPlayer()))
            {
                destination.setNumberSpaceShip(destination.getNumberSpaceShip()+1);

            }
            else if(destination.getPlayer().equals(Color.black) || (!destination.getPlayer().equals(Color.black) && destination.numberSpaceShip==0))
            {
                destination.numberSpaceShip = numberSpaceShip;

                destination.player = this.getPlayer();

                if(!myspaceShips.isEmpty())
                {
                    for (int i = 0; i <myspaceShips.size() ; i++) {
                        SpaceShip sship = (SpaceShip) myspaceShips.get(i);
                        sship.setColor(this.player);
                    }
                }

            }
            else
            {
                if(!Color.black.equals(player))

                    destination.setNumberSpaceShip(destination.getNumberSpaceShip()-1);
            }
        }


    }

