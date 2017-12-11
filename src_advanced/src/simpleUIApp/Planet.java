package simpleUIApp;

import fr.ubordeaux.simpleUI.KeyPress;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Point2D;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Random;

public class Planet extends Item implements Serializable{
	private int numberSpaceShip;
    private static transient Color saveLast =null  ;
    private Color player;
    private static int timeGenerate = 0;
    public static transient Planet  firstGame = null;

    //System.identityHashCode(this); give me uniq id of my object

    /**
     * allows to have the color of the player (of the planet)
     * @return color of planet
     */
    public Color getPlayer() {
        return player;
    }

    /**
     * allows to have time for the generation of new planet
     * @return int  time
     */
    public static int getTimeGenerate() {
        return timeGenerate;
    }

    public static void setTimeGenerate(int timeGenerate) {
        Planet.timeGenerate = timeGenerate;
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

    public Planet(double x, double y, int w, Color player) {
        super(x, y, w);
        this.player = player;
    }

    /**
     * intelligently generate colors for planet or ship creation
     * @return int corresponding of color
     */
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
    public void setObjective(Item item) {}

    public void move() {}

    public void draw(Graphics2D g) {
		Point2D pl = this.center;
		int x = (int) pl.getX();
		int y = (int) pl.getY();
		int w = getWidth();

        g.setColor(this.player);

		g.fillRect(x - w / 2, y - w / 2, w, w);
        g.setColor(Color.WHITE);

        g.drawString(numberSpaceShip+"",(int)this.getLocation().getX(),(int)this.getLocation().getY());

	}



	public boolean contains(Point2D p) {

        return squareDistance(this.center, p) <= (getWidth() / 2) * (getWidth() / 2);

	}

    /**
     * allows to increment the number of ships when timeGenerate would have a value greater than or equal to 10000 ms
     */
	public static void UpdateUniteAfterTime()
    {
        if (Planet.getTimeGenerate()>=10000) {
            for (Item item : Item.collection) {

                if (item instanceof Planet && !((Planet) item).getPlayer().equals(Color.black)) {
                    ((Planet) item).numberSpaceShip += 1;

                }
            }
            Planet.setTimeGenerate(0);
        }

    }

    /**
     *allows you to create ships
     * @param item the collection containing all the items (planets and ships)
     * @param objectif of SpaceShip
     * @param nbescadron is the number of ships to send to the target planet
     */
	public void createSpaceShip(ArrayList<Item> item, Item objectif, int nbescadron)
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
        this.numberSpaceShip= this.numberSpaceShip-nbescadron;

    }

    public int getNumberSpaceShip() {
        return numberSpaceShip;
    }

    @Override
    public String toString() {
        return "Planet Code:"+System.identityHashCode(this)+"  " +toStringColor(this.player)+" nbSpaceShip: "+this.numberSpaceShip;
    }

    /**
     *can attack a planet if it does not have an attack in court or it just changes the goal
     * @param objectiv cible of planet
     * @param action allows to determine according to CRTL - ALT -SHIF the number of ships to send
     */
    public void attak(Item objectiv,KeyPress action)
    {

        ArrayList<Item> spaceShips = new ArrayList<>();
        int nbescadron=0;
        boolean success=false;
        if (!objectiv.equals(this)) {
            for (Item item : Item.collection) {
                if (item instanceof SpaceShip && !((SpaceShip) item).getObjective().contains(item.center) && ((SpaceShip) item).getIDPlanet() == System.identityHashCode(this)) {
                    item.setObjective(objectiv);
                    success = true;
                }

            }

            if (!this.player.equals(Color.black) && !success) {

                if (action.equals(KeyPress.CRTL)) {
                    //100 %
                    nbescadron = this.numberSpaceShip;
                }
                if (action.equals(KeyPress.ALTGR)) {
                    //75 %
                    nbescadron = (this.numberSpaceShip * 75) / 100;
                }
                if (action.equals(KeyPress.SHIFT)) {
                    //25 %
                    nbescadron = (this.numberSpaceShip * 25) / 100;
                }

                this.createSpaceShip(spaceShips, objectiv, nbescadron);


            }
        }



    }

    public void setNumberSpaceShip(int numberSpaceShip) {
        this.numberSpaceShip = numberSpaceShip;
    }

    /**
     * allows you to return a list of ships belonging to a given planet in parameter using System.identityHashCode of the planet
     * @param who la planete concernée
     * @return list of SpaceShip
     */
    public ArrayList<Item> mySpaceShips(Item who)
    {
        ArrayList<Item> spaceShips = new ArrayList<>();
        for (Item item: Item.collection) {
            if (item instanceof SpaceShip && ((SpaceShip) item).getIDPlanet()==System.identityHashCode(who))
                spaceShips.add(item);
        }
        return spaceShips;
    }


    /**
     * allows to evaluate the damage or the troop movements to carry out by increasing or decreasing the number of ships of the planets concerned
     * @param objectiv of SpaceShip
     * @param power of SpaceShip
     * @return true or false after evaluate
     */
    public   boolean afterAttak(Item objectiv, int power)
    {


        Planet destination = (Planet)objectiv;
        ArrayList<Item> myspaceShips = mySpaceShips(destination);
        if(!this.player.equals(Color.black) && this.getPlayer().equals(destination.getPlayer()))
        {
            destination.setNumberSpaceShip(destination.getNumberSpaceShip()+1);
            return true;

        }
        else if(destination.getPlayer().equals(Color.black) || (!destination.getPlayer().equals(Color.black) && destination.numberSpaceShip<=0))
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
            return true;

        }
        else
        {
            if(!Color.black.equals(player))
            {
                destination.setNumberSpaceShip(destination.getNumberSpaceShip()-power);
                if(destination.numberSpaceShip<=0)
                {
                    destination.player = this.getPlayer();

                    if(!myspaceShips.isEmpty())
                    {
                        for (int i = 0; i <myspaceShips.size() ; i++) {
                            SpaceShip sship = (SpaceShip) myspaceShips.get(i);
                            sship.setColor(this.player);
                        }
                    }
                }
                return true;
            }


        }
        return false;


    }

    /**
     *allows to compare two planets based on the method of the parent class
     * @param o
     * @return true if it's the same else false
     */
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

