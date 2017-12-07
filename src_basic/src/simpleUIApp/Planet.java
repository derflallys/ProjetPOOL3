package simpleUIApp;

import fr.ubordeaux.simpleUI.KeyPress;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Point2D;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Random;

public class Planet extends Item implements Serializable{
	private int numberSpaceShip;
    private static transient Color saveLast =null  ;
    private Color player;

    //System.identityHashCode(this); give me uniq id of my object

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
            this.numberSpaceShip=20;
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
    public void setObjective(Item item) {};
    public void move() {};

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

	public void createShapeShip(ArrayList<Item> item, Item objectif, int nbescadron)
    {
        Random random = new Random();
        int x,y;
        SpaceShip spaceShip;

        for (int i = 0; i <nbescadron ; i++) {
            x=random.nextInt(700);
            y=random.nextInt(800);
            while ((x==this.getLocation().getX() &&  y==this.getLocation().getY() ) ||
                    squareDistance(this.getLocation(),new Point2D.Double(x,y))>2500 ||
                    this.contains(new Point2D.Double(x,y)))
            {
                x=random.nextInt(700);
                y=random.nextInt(800);
            }
            spaceShip = new SpaceShip(x,y, this.getWidth()/4,this.player,System.identityHashCode(this));
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
        return "Planet Code:"+System.identityHashCode(this)+"  " +toStringColor(this.player)+" nbSpaceShip: "+this.numberSpaceShip;
    }

    public void attak(Item objectiv,KeyPress action)
    {

        ArrayList<Item> spaceShips = new ArrayList<>();
        int nbescadron=0;
        boolean success=false;

        for (Item item:Item.collection) {
            if (item instanceof SpaceShip && !((SpaceShip) item).getObjective().contains(item.center) && ((SpaceShip) item).getIDPlanet()==System.identityHashCode(this))
            {
                item.setObjective(objectiv);
                success=true;
            }


        }

        if(!this.player.equals(Color.black) && !success)
        {

            if(action.equals(KeyPress.CRTL))
            {
                //100 %
                nbescadron=this.numberSpaceShip;
            }
            if (action.equals(KeyPress.ALTGR))
            {
                //75 %
                nbescadron=(this.numberSpaceShip*75) /100;
            }
            if(action.equals(KeyPress.SHIFT))
            {
                //25 %
                nbescadron=(this.numberSpaceShip*25)/100;
            }

            this.createShapeShip(spaceShips,objectiv,nbescadron);


        }



    }

    public void setNumberSpaceShip(int numberSpaceShip) {
        this.numberSpaceShip = numberSpaceShip;
    }

    public ArrayList<Item> mySpaceShips (Planet objectiv)
    {
        ArrayList<Item> spaceShips = new ArrayList<>();
        for (Item item: Item.collection) {
            if (item instanceof SpaceShip && ((SpaceShip) item).getIDPlanet()==System.identityHashCode(this))
                spaceShips.add(item);
        }
        return spaceShips;
    }

    public void afterAttak(Item objectiv )
    {


        Planet destination = (Planet)objectiv;
        ArrayList<Item> myspaceShips = mySpaceShips(destination);
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

    @Override
    public boolean equals(Object o) {
        if(o==null)
            return false;
        if(this==o)
            return true;
        if(o instanceof Planet)
        {
            if(this.player.equals(((Planet) o).player) && this.numberSpaceShip==((Planet) o).numberSpaceShip && super.equals(o))
                return true;
        }
        return false;
    }
}

