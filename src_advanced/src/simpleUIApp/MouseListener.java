package simpleUIApp;

import java.util.ArrayList;
import java.util.List;

import fr.ubordeaux.simpleUI.KeyPress;
import fr.ubordeaux.simpleUI.MouseHandler;

public class MouseListener implements MouseHandler<Item> {

	ArrayList<Item> dragList = new ArrayList<Item>();

	public MouseListener() {
		super();
	}

	@Override
	public void mouseClicked(List<Item> arg0, KeyPress arg1) {
		System.out.println("Select " + arg0 + " " + arg1);
		for (Item testItem : arg0) {
			System.out.println("Mouse click " + testItem);
		}
	}

	@Override
	public void mouseDrag(List<Item> arg0, KeyPress arg1) {
		dragList = new ArrayList<Item>(arg0);
		System.out.println("Drag :" + dragList);
	}

	@Override
	public void mouseDragging(List<Item> arg0, KeyPress arg1) {
		if (!arg0.isEmpty())
			System.out.println("Dragging :" + arg0);
	}

	@Override
	public void mouseDrop(List<Item> arg0, KeyPress arg1)throws IndexOutOfBoundsException {
		System.out.println("Drag& Drop :" + dragList + " => " + arg0 + " using " + arg1.toString());
		for (Item item : dragList) {
				try {
					if(item instanceof Planet)
                    {

                        if(item instanceof Planet)
                        {
                        	//This is use to play with computer or a game between computer
                        	if(Planet.firstGame==null)
							{
								Planet.firstGame= (Planet) item;
								System.out.println("Initialise fisrtGame");
							}

                            ((Planet) item).attak( arg0.get(0),arg1);
                        }

                    }


				}catch(IndexOutOfBoundsException e) {
					System.out.println("you can't move a ship into an empty area");
				}
			}
			
	}

	@Override
	public void mouseOver(List<Item> arg0, KeyPress arg1) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseWheelMoved(List<Item> arg0, KeyPress arg1, int arg2) {
		// TODO Auto-generated method stub
		System.out.println(arg0 + " using " + arg1.toString() + " wheel rotate " + arg2);
	}

}
