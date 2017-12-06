package simpleUIApp;

import java.awt.*;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.Collection;
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
		for (int i = 0; i <25 ; i++) {
		    x=random.nextInt(700);
		    y=random.nextInt(800);
            if(!testItemList.isEmpty())
            {

                for (int j = 0;j <testItemList.size() ; j++) {

					while ( x==testItemList.get(j).getLocation().getX() &&  y==testItemList.get(j).getLocation().getY() || testItemList.get(j).contains(new Point2D.Double(x,y)))
                    {
                        x=random.nextInt(700);
                        y=random.nextInt(800);
                    }

                }
            }

			testItemList.add(new Planet(x,y,40));
            Item.collection=testItemList;
		}
        /*
        for (Item item:testItemList) {
           if(item instanceof Planet)
           {
               for (Item item1:testItemList) {
                   if(item1 instanceof Planet && Math.abs(item.getLocation().getX()-item1.getLocation().getX())<=100.0 || Math.abs(item.getLocation().getY()-item1.getLocation().getY())<=100.0)
                   {
                       System.out.println("Fou");
                       System.out.println("dist x "+Math.abs(item.getLocation().getX()-item1.getLocation().getX()));
                       System.out.println("dist y "+Math.abs(item.getLocation().getY()-item1.getLocation().getY()));
                   }

               }

           }
        }
         */


		Manager manager = new Manager();
		Run r = new Run(800, 900);

		/*
		 * Call the run method of Application providing an initial item Collection, an
		 * item manager and an ApplicationRunnable
		 */
		Application.run(testItemList, manager, r);
	}
}
