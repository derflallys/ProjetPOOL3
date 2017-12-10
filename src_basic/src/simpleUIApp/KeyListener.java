package simpleUIApp;

import javax.swing.*;

import fr.ubordeaux.simpleUI.KeyHandler;
import fr.ubordeaux.simpleUI.KeyPress;

import java.awt.*;
import java.awt.event.WindowEvent;
import java.util.Random;

public class KeyListener implements KeyHandler {

	private JFrame mFrame;

	public KeyListener(JFrame frame) {
		mFrame = frame;
	}

	@Override
	public JFrame getParentFrame() {
		return mFrame;
	}

	@Override
	public void keyPressed(char arg0) {

	}

	@Override
	public void keyReleased(char arg0) {

	}

	@Override
	public void keyTyped(char arg0) {
		switch (arg0) {
		case '+':
			System.out.println("Add neutral planet");
			Item.createPlanetBlack();
			break;
		case '-':
			System.out.println("- has been typed");
			break;
		case 'a':
				System.out.println("App will play him self");
            JOptionPane.showMessageDialog(mFrame,
                    "App play him self",
                    "Game Notification",
                    JOptionPane.INFORMATION_MESSAGE);
            Color player1 = Color.green;
            Color player2 = Color.BLUE;
            boolean token1 = true;
            boolean token2 = false;

            Random random = new Random();
            while (Item.EndOfGame().equals(Color.RED))
            {


                if (token1)
                {
                    int pos = random.nextInt(Item.collection.size());
                    while (Item.collection.get(pos) instanceof SpaceShip || ((Planet) Item.collection.get(pos)).getPlayer().equals(Color.black) || ((Planet) Item.collection.get(pos)).getPlayer().equals(player2))
                        pos = random.nextInt(Item.collection.size());
                    int posobject=random.nextInt(Item.collection.size());
                    if( Item.collection.get(pos) instanceof Planet) {
                        while (Item.collection.get(posobject) instanceof SpaceShip || Item.collection.get(posobject).equals(Item.collection.get(pos)))
                            posobject = random.nextInt(Item.collection.size());

                        int kpres = random.nextInt(3);
                        KeyPress press = KeyPress.UNKNOWN;
                        if (kpres == 0)
                            press = KeyPress.CRTL;
                        if (kpres == 1)
                            press = KeyPress.ALTGR;
                        if (kpres == 2)
                            press = KeyPress.SHIFT;

                        if (!(Item.collection.get(posobject) instanceof SpaceShip)) {
                            ((Planet) Item.collection.get(pos)).attak(Item.collection.get(posobject), press);
                            JOptionPane.showMessageDialog(mFrame,
                                    "Computer Play "+Item.toStringColor(((Planet) Item.collection.get(pos)).getPlayer())+" :  \n Drag& Drop :" + Item.collection.get(pos) + " => " + Item.collection.get(posobject) + " using " + press.toString(),
                                    " Notification Game",
                                    JOptionPane.INFORMATION_MESSAGE);
                            System.out.println("Drag& Drop :" + Item.collection.get(pos) + " => " + Item.collection.get(posobject) + " using " + press.toString());
                        }
                    }
                    token1=false;
                    token2=true;
                }
                else if (token2)
                {
                    int pos = random.nextInt(Item.collection.size());
                    while (Item.collection.get(pos) instanceof SpaceShip || ((Planet) Item.collection.get(pos)).getPlayer().equals(Color.black) || ((Planet) Item.collection.get(pos)).getPlayer().equals(player1))
                        pos = random.nextInt(Item.collection.size());
                    int posobject=random.nextInt(Item.collection.size());
                    if( Item.collection.get(pos) instanceof Planet) {
                        while (Item.collection.get(posobject) instanceof SpaceShip || Item.collection.get(posobject).equals(Item.collection.get(pos)))
                            posobject = random.nextInt(Item.collection.size());

                        int kpres = random.nextInt(3);
                        KeyPress press = KeyPress.UNKNOWN;
                        if (kpres == 0)
                            press = KeyPress.CRTL;
                        if (kpres == 1)
                            press = KeyPress.ALTGR;
                        if (kpres == 2)
                            press = KeyPress.SHIFT;

                        if (!(Item.collection.get(posobject) instanceof SpaceShip)) {
                            ((Planet) Item.collection.get(pos)).attak(Item.collection.get(posobject), press);
                            JOptionPane.showMessageDialog(mFrame,
                                    "Computer Play "+Item.toStringColor(((Planet) Item.collection.get(pos)).getPlayer())+" :  \n Drag& Drop :" + Item.collection.get(pos) + " => " + Item.collection.get(posobject) + " using " + press.toString(),
                                    " Notification Game",
                                    JOptionPane.INFORMATION_MESSAGE);
                            System.out.println("Drag& Drop :" + Item.collection.get(pos) + " => " + Item.collection.get(posobject) + " using " + press.toString());
                        }
                    }
                    token1=true;
                    token2=false;
                }


            }
            if(!Item.EndOfGame().equals(Color.RED))
            {
                JOptionPane.showMessageDialog(mFrame,
                        "Player "+Item.toStringColor(Item.EndOfGame())+" Win the Game ! ",
                        " End of Game ",
                        JOptionPane.INFORMATION_MESSAGE);
                System.out.println("End of the Game ! ");
                mFrame.dispatchEvent(new WindowEvent(mFrame, WindowEvent.WINDOW_CLOSING));
            }

            break;


		case 'p':

            System.out.println("At the computer to play");
            random = new Random();

            int pos = random.nextInt(Item.collection.size());
            if (Planet.firstGame==null)
            {
                while (Item.collection.get(pos) instanceof SpaceShip || ((Planet) Item.collection.get(pos)).getPlayer().equals(Color.black))
                    pos = random.nextInt(Item.collection.size());
            }
            else
            {
                while( Item.collection.get(pos) instanceof SpaceShip || ((Planet) Item.collection.get(pos)).getPlayer().equals(Planet.firstGame.getPlayer()) || ((Planet) Item.collection.get(pos)).getPlayer().equals(Color.black))
                    pos = random.nextInt(Item.collection.size());
            }


            int posobject=random.nextInt(Item.collection.size());
            if( Item.collection.get(pos) instanceof Planet)
			{
				while(Item.collection.get(posobject) instanceof SpaceShip || Item.collection.get(posobject).equals(Item.collection.get(pos)))
					posobject = random.nextInt(Item.collection.size());

				int kpres = random.nextInt(3);
				KeyPress press = KeyPress.UNKNOWN;
				if (kpres==0)
					press=KeyPress.CRTL;
				if (kpres==1)
					press=KeyPress.ALTGR;
				if (kpres==2)
					press=KeyPress.SHIFT;

                if(!(Item.collection.get(posobject) instanceof SpaceShip))
                {
                    ((Planet) Item.collection.get(pos)).attak(Item.collection.get(posobject),press);
                    JOptionPane.showMessageDialog(mFrame,
                            "Computer Play "+Item.toStringColor(((Planet) Item.collection.get(pos)).getPlayer())+" :  \n Drag& Drop :" + Item.collection.get(pos) + " => " + Item.collection.get(posobject) + " using " + press.toString(),
                            " Notification Game",
                            JOptionPane.INFORMATION_MESSAGE);
                    System.out.println("Drag& Drop :" + Item.collection.get(pos) + " => " + Item.collection.get(posobject) + " using " + press.toString());
                }
                else
                {
                    JOptionPane.showMessageDialog(mFrame,
                            "Give the computer another chance",
                            " Notification Game",
                            JOptionPane.INFORMATION_MESSAGE);
                    System.out.println("Give the computer another chance");
                }

			}

			break;

		default:
			// do nothing
			break;
		}
	}

}
