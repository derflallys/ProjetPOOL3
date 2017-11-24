package simpleUIApp;

import java.awt.*;
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

		for (int i = 0; i < 4; i++) {

			testItemList.add(new Planet(random.nextInt(350),random.nextInt(450),40,10));
		}

		for (int i = 0; i <4 ; i++) {

			if(testItemList.get(i) instanceof Planet)
			{
                if(!Color.black.equals(((Planet) testItemList.get(i)).getPlayer()))
                {
                    ((Planet) testItemList.get(i)).createShapeShip(testItemList);
                    System.out.println(i);
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
