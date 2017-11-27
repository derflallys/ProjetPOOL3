package simpleUIApp;

import java.awt.*;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.Random;

import fr.ubordeaux.simpleUI.*;

public class Main {
	public static void main(String[] args) {
		Random random = new Random();
		ArrayList<Item> testItemList = new ArrayList<Item>();
		/*
		 * Randomly position 25 Ships in the Arena zone (defined afterwards)
		 */

		int x,y;
		for (int i = 0; i < 4; i++) {
		    x=random.nextInt(350);
		    y=random.nextInt(450);
            if(!testItemList.isEmpty())
            {

                for (int j = 0;j <testItemList.size() ; j++) {
                    while ((x==testItemList.get(j).getLocation().getX() &&  y==testItemList.get(j).getLocation().getY() ) || testItemList.get(j).contains(new Point2D.Double(x,y)) || testItemList.get(j).contains(new Point2D.Double(x,y)))
                    {
                        x=random.nextInt(350);
                        y=random.nextInt(450);
                    }
                }
            }

			testItemList.add(new Planet(x,y,40,10));
		}

		for (int i = 0; i <testItemList.size() ; i++) {

			if(testItemList.get(i) instanceof Planet)
			{
                if(!Color.black.equals(((Planet) testItemList.get(i)).getPlayer()))
                {
                    ((Planet) testItemList.get(i)).createShapeShip(testItemList);

                }

			}
		}
		Manager manager = new Manager();
		Run r = new Run(400, 500);

		/*
		 * Call the run method of Application providing an initial item Collection, an
		 * item manager and an ApplicationRunnable
		 */
		Application.run(testItemList, manager, r);
	}
}
