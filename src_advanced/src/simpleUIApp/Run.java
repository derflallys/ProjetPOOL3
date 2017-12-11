package simpleUIApp;

import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.concurrent.TimeUnit;

import javax.swing.*;

import fr.ubordeaux.simpleUI.Application;
import fr.ubordeaux.simpleUI.ApplicationRunnable;
import fr.ubordeaux.simpleUI.Arena;
import fr.ubordeaux.simpleUI.TimerRunnable;
import fr.ubordeaux.simpleUI.TimerTask;

public class Run implements ApplicationRunnable<Item> {

	private int width;
	private int height;

	public Run(int width, int height) {
		this.width = width;
		this.height = height;
	}

	@Override
	public void run(final Arena<Item> arg0, Collection<Item> arg1) {
		MouseListener mouseHandler = new MouseListener();

		/*
		 * We build the graphical interface by adding the graphical component
		 * corresponding to the Arena - by calling createComponent - to a JFrame.
		 */
		final JFrame frame = new JFrame("Arena");

		/*
		 * This is our KeyHandler that will be called by the Arena in case of key events
		 */
		KeyListener keyListener = new KeyListener(frame);

		/**
		 * if the saveguard  exists it will be load else if you want to begin a new game a new part of game will be begin
		 */
		if (arg1.isEmpty())
		{

			 int option =JOptionPane.showConfirmDialog(frame,
					 "Do you want to load the last part of the game ? ",
					"Load Game ",
					JOptionPane.YES_NO_OPTION);


            if (option==0)
			 {
				 ObjectInputStream ois = null;
				 try {
					 final FileInputStream fileInputStream = new FileInputStream("collection.ser");
					 ois = new ObjectInputStream(fileInputStream);
					 Item item;
					 int sizeofCollection = ois.readInt();
					 for (int i = 0; i <sizeofCollection ; i++) {
						 item = (Item) ois.readObject();
						 arg1.add(item);
					 }
					 Item.collection = (ArrayList<Item>) arg1;
                     System.out.println("Game Load!");

				 } catch (ClassNotFoundException | IOException e) {
					 e.printStackTrace();
				 } finally {
					 try {
						 if (ois!=null)
						 {
							 ois.close();
						 }
					 } catch (IOException e) {
						 e.printStackTrace();
					 }
				 }
			 }
			 else if(option==1 || option==-1)
			 {

                 ArrayList<Item> items=Item.createPlanet(10);
                 if (items.isEmpty())
                     System.out.println("empty");
                 for (Item item:
                      items) {
                     arg1.add(item);
                     Item.collection= (ArrayList<Item>) arg1;
                 }

			 }



		}




		frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		frame.add(arg0.createComponent(this.width, this.height, mouseHandler, keyListener));
		frame.pack();
		frame.setVisible(true);
        frame.setLocationRelativeTo(null);
		/**
		 * save game when you exit the game by click of close window
		 */
		frame.addWindowListener(new WindowAdapter()
		{

			public void windowClosing(WindowEvent e)
			{
				ObjectOutputStream oos = null;
				try {
					final FileOutputStream fileOutputStream = new FileOutputStream("collection.ser");
					oos = new ObjectOutputStream(fileOutputStream);
					oos.writeInt(Item.collection.size());
					for (Item item:Item.collection) {
						oos.writeObject(item);
					}
					oos.flush();

				} catch (FileNotFoundException e1) {
					e1.printStackTrace();
				}
				 catch (IOException e1) {
					e1.printStackTrace();
				}
				finally {
					try {
						if (oos!=null)
						{
							oos.flush();
							oos.close();
						}
					} catch (IOException e1) {
						e1.printStackTrace();
					}
				}

				System.out.println("Closed and Save ");

			}
		});

		/*
		 * We initially draw the component
		 */
		arg0.refresh();

		/*
		 * We ask the Application to call the following run function every seconds. This
		 * method just refresh the component.
		 */


        Collection<Item> finalArg = arg1;


        Application.timer(100, new TimerRunnable() {

			boolean finish;
			public void run(TimerTask timerTask) {
				arg0.refresh();

				for (Item item : finalArg) {
					item.move();


				}

				for (int i = 0; i <Item.collection.size() ; i++) {
					if(Item.collection.get(i) instanceof Planet )
					{
						for (int j = 0; j <Item.collection.size() ; j++)
						 {
							if(Item.collection.get(j) instanceof SpaceShip && ((SpaceShip) Item.collection.get(j)).getIDPlanet()==System.identityHashCode(Item.collection.get(i)))
							{

								if(((SpaceShip) Item.collection.get(j)).getObjective().contains(Item.collection.get(j).center))
								{

									Item objectiv = ((SpaceShip) Item.collection.get(j)).getObjective();
									if(objectiv instanceof Planet)
									{
										finish=((Planet)Item.collection.get(i)).afterAttak(objectiv,((SpaceShip) Item.collection.get(j)).getPower());
										if(finish)
										{
											Item.eraseAfterAttak();
										}
										break;
									}


								}

							}
						}
					}
				}


               Planet.setTimeGenerate(Planet.getTimeGenerate()+100);
				Planet.UpdateUniteAfterTime();

                if(!Item.EndOfGame().equals(Color.RED))
                {
                    JOptionPane.showMessageDialog(frame,
                            "Player "+Item.toStringColor(Item.EndOfGame())+" Win the Game ! ",
                            " End of Game ",
                            JOptionPane.INFORMATION_MESSAGE);
                    System.out.println("End of the Game ! ");
                    frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
                }



			}


		});

	}

}
