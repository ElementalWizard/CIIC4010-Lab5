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
		if(!myPanel.GameOver) {
			switch (e.getButton()) {
			case 1:        //Left mouse button

			break;
			case 3:        //Right mouse button
			break;
			default:    //Some other button (2 = Middle mouse button, etc.)
				break;
			}
		}
	}

	public void mouseReleased(MouseEvent e) {
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
		if(!myPanel.GameOver) {
			switch (e.getButton()) {
			case 1:        //Left mouse button


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

							///////////////////////////////////////////////////////////////////////////
							///////////////////////////////////////////////////////////////////////////
							//Bomb Touch
							if (myPanel.bombLocations[myPanel.mouseDownGridX][myPanel.mouseDownGridY]) {
								myPanel.colorArray[myPanel.mouseDownGridX][myPanel.mouseDownGridY] = Color.BLACK;
								myPanel.repaint();


								for (int X = 0; X < myPanel.TOTAL_COLUMNS; X++) {   //The rest of the grid
									for (int Y = 0; Y < myPanel.TOTAL_ROWS; Y++) {
										if (myPanel.bombLocations[X][Y]) {
											myPanel.colorArray[X][Y] = Color.BLACK;
											myPanel.repaint();
										}
									}
								}
								myPanel.GameOver = true;
							}
							///////////////////////////////////////////////////////////////////////////
							///////////////////////////////////////////////////////////////////////////
							else {

								checkSurroundings(myPanel.mouseDownGridX, myPanel.mouseDownGridY, myPanel);
								myPanel.repaint();

							}
						}
					}
				}
				myPanel.repaint();
				break;
			case 3:        //Right mouse button
				if ((myPanel.mouseDownGridX == -1) || (myPanel.mouseDownGridY == -1)) {
					//Had pressed outside originally and now inside
				} else {
					if ((gridX == -1) || (gridY == -1)) {
						//Is releasing outside the grid
					}
					else{
						myPanel.colorArray[myPanel.mouseDownGridX][myPanel.mouseDownGridY] = Color.RED;
						myPanel.repaint();
					}
				}
				break;
			default:    //Some other button (2 = Middle mouse button, etc.)
				break;
			}
		}
	}

	private void checkSurroundings(int mouseDownGridX, int mouseDownGridY, MyPanel myPanel) {


		if(myPanel.bombsAroundXY[mouseDownGridX][mouseDownGridY]==-1) {
			myPanel.bombsAroundXY[mouseDownGridX][mouseDownGridY]=0;

			for (int x = mouseDownGridX - 1; x <= mouseDownGridX + 1; x++) {
				if((x<0 ||x>myPanel.TOTAL_COLUMNS-1)){
					continue;
				}
				for (int y = mouseDownGridY - 1; y <= mouseDownGridY + 1; y++) {
					if((y<0 || y>myPanel.TOTAL_ROWS-2)){
						continue;
					}
					if(x == mouseDownGridX && y == mouseDownGridY){
						myPanel.setNumberOfSquares(myPanel.getNumberOfSquares()-1);
						System.out.println(myPanel.getNumberOfSquares());

						continue;
					}
					if (myPanel.bombLocations[x][y]) {
						myPanel.bombsAroundXY[mouseDownGridX][mouseDownGridY]++;
						
					}
				}
			}
			if(myPanel.bombsAroundXY[mouseDownGridX][mouseDownGridY]==0){
				for (int x = mouseDownGridX - 1; x <= mouseDownGridX + 1; x++) {
					if ((x < 0 || x > myPanel.TOTAL_COLUMNS - 1)) {
						continue;
					}
					for (int y = mouseDownGridY - 1; y <= mouseDownGridY + 1; y++) {
						if ((y < 0 || y > myPanel.TOTAL_ROWS - 2)) {
							continue;
						}
						checkSurroundings(x,y,myPanel);

					}
				}
			}
		}
	}


}