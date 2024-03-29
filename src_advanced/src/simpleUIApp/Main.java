package simpleUIApp;


import java.io.*;
import java.util.ArrayList;
import fr.ubordeaux.simpleUI.*;



public class Main {
	public static void main(String[] args) {

		ArrayList<Item> testItemList = new ArrayList<Item>();

		/*
		 * Check if  safeguard existed
		 */
		File f = new File("collection.ser");
		if (!f.exists()) {
             testItemList= Item.createPlanet(10);
             Item.collection=testItemList;
        }



		Manager manager = new Manager();
		Run r = new Run(800, 900);

		/*
		 * Call the run method of Application providing an initial item Collection, an
		 * item manager and an ApplicationRunnable
		 */
		Application.run(testItemList, manager, r);

	}
}
