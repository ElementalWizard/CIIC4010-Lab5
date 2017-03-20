import java.awt.Color;
import java.awt.Component;
import java.awt.Insets;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Random;

import javax.swing.JFrame;

public class MyMouseAdapter extends MouseAdapter {
	private Random generator = new Random();
	public void mousePressed(MouseEvent e) {
		switch (e.getButton()) {
			case 1:		//Left mouse button
				Component c = e.getComponent();
				while (!(c instanceof JFrame)) {
					c = c.getParent();
					if (c == null) {
						return;
					}
				}
				JFrame myFrame = (JFrame) c;
				MyPanel myPanel = (MyPanel) myFrame.getContentPane().getComponent(0);
				Insets myInsets = myFrame.getInsets();
				int x1 = myInsets.left;
				int y1 = myInsets.top;
				e.translatePoint(-x1, -y1);
				int x = e.getX();
				int y = e.getY();
				myPanel.x = x;
				myPanel.y = y;
				myPanel.mouseDownGridX = myPanel.getGridX(x, y);
				myPanel.mouseDownGridY = myPanel.getGridY(x, y);
				myPanel.repaint();
				break;
			case 3:		//Right mouse button
				break;
			default:    //Some other button (2 = Middle mouse button, etc.)
				break;
		}
	}

	public void mouseReleased(MouseEvent e) {
		switch (e.getButton()) {
			case 1:		//Left mouse button
				Component c = e.getComponent();
				while (!(c instanceof JFrame)) {
					c = c.getParent();
					if (c == null) {
						return;
					}
				}
				JFrame myFrame = (JFrame)c;
				MyPanel myPanel = (MyPanel) myFrame.getContentPane().getComponent(0);  //Can also loop among components to find MyPanel
				Insets myInsets = myFrame.getInsets();
				int x1 = myInsets.left;
				int y1 = myInsets.top;
				e.translatePoint(-x1, -y1);
				int x = e.getX();
				int y = e.getY();
				myPanel.x = x;
				myPanel.y = y;
				int gridX = myPanel.getGridX(x, y);
				int gridY = myPanel.getGridY(x, y);
				if ((myPanel.mouseDownGridX == -1) || (myPanel.mouseDownGridY == -1)) {
					//Had pressed outside originally and now inside
				} else {
					if ((gridX == -1) || (gridY == -1)) {
						//Is releasing outside the grid
					} else {
						if ((myPanel.mouseDownGridX != gridX) || (myPanel.mouseDownGridY != gridY)) {
							//Released the mouse button on a different cell where it was pressed
						} else {
							//Released the mouse button on the same cell where it was pressed
                            //and on grid

                            if(myPanel.bombLocations[myPanel.mouseDownGridX][myPanel.mouseDownGridY]){
                                System.out.println("Boom");
                                myPanel.colorArray[myPanel.mouseDownGridX][myPanel.mouseDownGridY]=Color.BLACK;
                                myPanel.repaint();
                                myFrame.addMouseListener(null);

                                for (int X = 0; X < myPanel.TOTAL_COLUMNS; X++) {   //The rest of the grid
                                    for (int Y = 0; Y < myPanel.TOTAL_ROWS; Y++) {
                                        if(myPanel.bombLocations[X][Y]){
                                            myPanel.colorArray[X][Y]=Color.BLACK;
                                            myPanel.repaint();
                                        }
                                    }
                                }
                                myPanel.GameOver=true;

                            }



						}
					}
				}
				myPanel.repaint();
				break;
			case 3:		//Right mouse button
				break;
			default:    //Some other button (2 = Middle mouse button, etc.)
				break;
		}
	}
}