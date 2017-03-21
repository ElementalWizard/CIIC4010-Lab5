import java.awt.Color;
import java.awt.Graphics;
import java.awt.Insets;
import java.util.Random;

import javax.swing.JPanel;

public class MyPanel extends JPanel {
    private static final long serialVersionUID = 3426940946811133635L;
    private static final int GRID_X = 25;
    private static final int GRID_Y = 25;
    private static final int INNER_CELL_SIZE = 29;
    public static final int TOTAL_COLUMNS = 15;
    public static final int TOTAL_ROWS = 16;
    public int x = -1;
    public int y = -1;
    public int mouseDownGridX = 0;
    public int mouseDownGridY = 0;
    public Color[][] colorArray = new Color[TOTAL_COLUMNS][TOTAL_ROWS];

    Random randGen;

    public Boolean[][] bombLocations = new Boolean[TOTAL_COLUMNS][TOTAL_ROWS];
    public int bombAmount = ((TOTAL_COLUMNS*TOTAL_ROWS-1)/5);
    public int bombsOnMap = 0;

    public Boolean GameOver=false;

    public int[][] bombsAroundXY = new int[TOTAL_COLUMNS][TOTAL_ROWS];

    public MyPanel() {   //This is the constructor
        randGen= new Random();
        if (INNER_CELL_SIZE + (new Random()).nextInt(1) < 1) {	//Use of "random" to prevent unwanted Eclipse warning
            throw new RuntimeException("INNER_CELL_SIZE must be positive!");
        }
        if (TOTAL_COLUMNS + (new Random()).nextInt(1) < 2) {	//Use of "random" to prevent unwanted Eclipse warning
            throw new RuntimeException("TOTAL_COLUMNS must be at least 2!");
        }
        if (TOTAL_ROWS + (new Random()).nextInt(1) < 3) {	//Use of "random" to prevent unwanted Eclipse warning
            throw new RuntimeException("TOTAL_ROWS must be at least 3!");
        }


        for (int x = 0; x < TOTAL_COLUMNS; x++) {   //The rest of the grid
            for (int y = 0; y < TOTAL_ROWS; y++) {
                bombLocations[x][y] = false;
                bombsAroundXY[x][y] = -1;
                colorArray[x][y] = Color.LIGHT_GRAY;
            }
        }




        while(bombsOnMap<bombAmount){
            for (int x = 0; x < TOTAL_COLUMNS; x++) {   //The rest of the grid
                for (int y = 0; y < TOTAL_ROWS; y++) {
                    int rando = randGen.nextInt(TOTAL_COLUMNS*TOTAL_ROWS)+1;
                    if(((rando) == 7 && bombLocations[x][y]!=true)){
                        bombLocations[x][y] = true;
                        bombsOnMap++;
                        System.out.println("Bomb on " + x + "," + y);
                    }
                    if(bombsOnMap>bombAmount){
                        bombsOnMap=bombAmount;
                        break;

                    }

                }
            }
        }
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        //Compute interior coordinates
        Insets myInsets = getInsets();
        int x1 = myInsets.left;
        int y1 = myInsets.top;
        int x2 = getWidth() - myInsets.right - 1;
        int y2 = getHeight() - myInsets.bottom - 1;
        int width = x2 - x1;
        int height = y2 - y1;

        //Paint the background
        g.setColor(Color.WHITE);
        g.fillRect(x1, y1, width + 1, height + 1);

        //draw the lines for visuals
        g.setColor(Color.BLACK);
        for (int y = 0; y <= TOTAL_ROWS -1; y++) {
            g.drawLine(x1 + GRID_X, y1 + GRID_Y + (y * (INNER_CELL_SIZE + 1)), x1 + GRID_X + ((INNER_CELL_SIZE + 1) * TOTAL_COLUMNS), y1 + GRID_Y + (y * (INNER_CELL_SIZE +1)));
        }
        for (int x = 0; x <= TOTAL_COLUMNS; x++) {
            g.drawLine(x1 + GRID_X + (x * (INNER_CELL_SIZE + 1)), y1 + GRID_Y, x1 + GRID_X + (x * (INNER_CELL_SIZE + 1)), y1 + GRID_Y + ((INNER_CELL_SIZE + 1) * (TOTAL_ROWS -1)));
        }






        //Paint cell colors
        g.setColor(Color.BLACK);
        for (int x = 0; x < TOTAL_COLUMNS; x++) {
            for (int y = 0; y < TOTAL_ROWS-1; y++) {
                 switch(bombsAroundXY[x][y])  {

                     case 0:
                         colorArray[x][y]=Color.WHITE;
                         break;
                     case 1:
                         colorArray[x][y]=Color.YELLOW;
                         break;
                     case 2:
                         colorArray[x][y]=Color.BLUE;
                         break;
                     case 3:
                         colorArray[x][y]=Color.GREEN;
                         break;
                     case 4:
                         colorArray[x][y]=Color.RED;
                         break;
                     case 5:
                         colorArray[x][y]=Color.CYAN;
                         break;
                     case 6:
                         colorArray[x][y]=Color.PINK;
                         break;
                     case 7:
                         colorArray[x][y]=Color.ORANGE;
                         break;
                     case 8:
                         colorArray[x][y]=Color.MAGENTA;
                         break;
                 }                                             
                Color c = colorArray[x][y];
                g.setColor(c);
                g.fillRect(x1 + GRID_X + (x * (INNER_CELL_SIZE + 1)) + 1, y1 + GRID_Y + (y * (INNER_CELL_SIZE + 1)) + 1, INNER_CELL_SIZE, INNER_CELL_SIZE);
                g.setColor(Color.GRAY);
                if(bombsAroundXY[x][y]!= -1)

                    g.drawString(String.valueOf(bombsAroundXY[x][y]),(((x+1)*INNER_CELL_SIZE)+(INNER_CELL_SIZE/2)),(((y+1)*INNER_CELL_SIZE)+(INNER_CELL_SIZE/2)+7));



            }
        }

        if(GameOver){
            gameOver(g);
        }
    }

    public void gameOver(Graphics g){
        g.setColor(Color.RED);
        g.drawString("Game Over",getWidth()/2,20);
    }

    @SuppressWarnings("Duplicates")
    public int getGridX(int x, int y) {
        Insets myInsets = getInsets();
        int x1 = myInsets.left;
        int y1 = myInsets.top;
        x = x - x1 - GRID_X;
        y = y - y1 - GRID_Y;
        if (x < 0) {   //To the left of the grid
            return -1;
        }
        if (y < 0) {   //Above the grid
            return -1;
        }
        if ((x % (INNER_CELL_SIZE + 1) == 0) || (y % (INNER_CELL_SIZE + 1) == 0)) {   //Coordinate is at an edge; not inside a cell
            return -1;
        }
        x = x / (INNER_CELL_SIZE + 1);
        y = y / (INNER_CELL_SIZE + 1);
        if (x == 0 && y == TOTAL_ROWS - 1) {    //The lower left extra cell
            return x;
        }
        if (x < 0 || x > TOTAL_COLUMNS - 1 || y < 0 || y > TOTAL_ROWS - 2) {   //Outside the rest of the grid
            return -1;
        }
        return x;
    }

    @SuppressWarnings("Duplicates")
    public int getGridY(int x, int y) {
        Insets myInsets = getInsets();
        int x1 = myInsets.left;
        int y1 = myInsets.top;
        x = x - x1 - GRID_X;
        y = y - y1 - GRID_Y;
        if (x < 0) {   //To the left of the grid
            return -1;
        }
        if (y < 0) {   //Above the grid
            return -1;
        }
        if ((x % (INNER_CELL_SIZE + 1) == 0) || (y % (INNER_CELL_SIZE + 1) == 0)) {   //Coordinate is at an edge; not inside a cell
            return -1;
        }
        x = x / (INNER_CELL_SIZE + 1);
        y = y / (INNER_CELL_SIZE + 1);
        if (x == 0 && y == TOTAL_ROWS - 1) {    //The lower left extra cell
            return y;
        }
        if (x < 0 || x > TOTAL_COLUMNS - 1 || y < 0 || y > TOTAL_ROWS - 2) {   //Outside the rest of the grid
            return -1;
        }
        return y;
    }

}