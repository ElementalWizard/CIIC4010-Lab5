import java.awt.Color;
import java.awt.Graphics;
import java.awt.Insets;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

public class MyPanel extends JPanel {
    private static final long serialVersionUID = 3426940946811133635L;
    private static final int GRID_X = 25;
    private static final int GRID_Y = 25;
    private static final int INNER_CELL_SIZE = 29;
    static final int TOTAL_COLUMNS = 9;
    static final int TOTAL_ROWS = 10;
    int x = -1;
    int y = -1;
    int mouseDownGridX = 0;
    int mouseDownGridY = 0;
    Color[][] colorArray = new Color[TOTAL_COLUMNS][TOTAL_ROWS];


    Boolean[][] bombLocations = new Boolean[TOTAL_COLUMNS][TOTAL_ROWS];
    Boolean[][] flagLocations = new Boolean[TOTAL_COLUMNS][TOTAL_ROWS];


    public BufferedImage[] Images = new BufferedImage[11];

    private int numberOfSquares;
    private int timeLooped =0;

    Boolean GameOver=false;
    Boolean GameWon=false;
    Boolean Displaying=false;


    int[][] bombsAroundXY = new int[TOTAL_COLUMNS][TOTAL_ROWS];

    MyPanel() {   //This is the constructor
        Random randGen = new Random();
        if (INNER_CELL_SIZE + (new Random()).nextInt(1) < 1) {	//Use of "random" to prevent unwanted Eclipse warning
            throw new RuntimeException("INNER_CELL_SIZE must be positive!");
        }
        if (TOTAL_COLUMNS + (new Random()).nextInt(1) < 2) {	//Use of "random" to prevent unwanted Eclipse warning
            throw new RuntimeException("TOTAL_COLUMNS must be at least 2!");
        }
        if (TOTAL_ROWS + (new Random()).nextInt(1) < 3) {	//Use of "random" to prevent unwanted Eclipse warning
            throw new RuntimeException("TOTAL_ROWS must be at least 3!");
        }

        try {
            Images[0]= ImageIO.read(getClass().getResourceAsStream("0.png"));
            Images[1]= ImageIO.read(getClass().getResourceAsStream("1.png"));
            Images[2]= ImageIO.read(getClass().getResourceAsStream("2.png"));
            Images[3]= ImageIO.read(getClass().getResourceAsStream("3.png"));
            Images[4]= ImageIO.read(getClass().getResourceAsStream("4.png"));
            Images[5]= ImageIO.read(getClass().getResourceAsStream("5.png"));
            Images[6]= ImageIO.read(getClass().getResourceAsStream("6.png"));
            Images[7]= ImageIO.read(getClass().getResourceAsStream("7.png"));
            Images[8]= ImageIO.read(getClass().getResourceAsStream("8.png"));
            Images[9]= ImageIO.read(getClass().getResourceAsStream("bomb.png"));
            Images[10]= ImageIO.read(getClass().getResourceAsStream("flag-red.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }


        for (int x = 0; x < TOTAL_COLUMNS; x++) {   //The rest of the grid
            for (int y = 0; y < TOTAL_ROWS; y++) {
                bombLocations[x][y] = false;
                flagLocations[x][y] = false;
                bombsAroundXY[x][y] = -1;
                colorArray[x][y] = Color.LIGHT_GRAY;
            }
        }


        int totalSquares = (TOTAL_COLUMNS * (TOTAL_ROWS - 1));
        int bombAmount = Math.round((totalSquares) / 5);
        int bombsOnMap = 0;
        while((bombsOnMap != bombAmount) && (timeLooped<=25)){
            for (int x = 0; x < TOTAL_COLUMNS; x++) {   //The rest of the grid
                for (int y = 0; y < TOTAL_ROWS -1; y++) {
                    int rando = randGen.nextInt(totalSquares);
                    if(((rando) == 7 && !bombLocations[x][y])){
                        bombLocations[x][y] = true;
                        bombsOnMap++;
                        timeLooped++;
                    }

                }
            }
        }
        numberOfSquares = (totalSquares)- bombAmount;







    }

    int getNumberOfSquares() {
        return numberOfSquares;
    }

    void setNumberOfSquares(int numberOfSquares) {
        this.numberOfSquares = numberOfSquares;
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
        g.setColor(new Color(110,110,110));
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
        for (int x = 0; x < TOTAL_COLUMNS; x++) {
            for (int y = 0; y < TOTAL_ROWS-1; y++) {
                BufferedImage image = null;
                Color c = colorArray[x][y];
                switch(bombsAroundXY[x][y])  {
                    case 0:
                        c=new Color(217,212,220);
                        image=Images[0];
                        break;
                    case 1:
                        c=new Color(217,212,220);
                        image=Images[1];
                        break;
                    case 2:
                        c=new Color(217,212,220);
                        image=Images[2];
                        break;
                    case 3:
                        c=new Color(217,212,220);
                        image=Images[3];
                        break;
                    case 4:
                        c=new Color(217,212,220);
                        image=Images[4];
                        break;
                    case 5:
                        c=new Color(217,212,220);
                        image=Images[5];
                        break;
                    case 6:
                        c=new Color(217,212,220);
                        image=Images[6];
                        break;
                    case 7:
                        c=new Color(217,212,220);
                        image=Images[7];
                        break;
                    case 8:
                        c=new Color(217,212,220);
                        image=Images[8];
                        break;
                }
                g.setColor(c);
                g.fillRect(x1 + GRID_X + (x * (INNER_CELL_SIZE + 1)) + 1, y1 + GRID_Y + (y * (INNER_CELL_SIZE + 1)) + 1, INNER_CELL_SIZE, INNER_CELL_SIZE);
                if(bombsAroundXY[x][y]>0) {
                    g.drawImage(image, x1 + GRID_X + (x * (INNER_CELL_SIZE + 1)) + 1, y1 + GRID_Y + (y * (INNER_CELL_SIZE + 1)) + 1, 29, 29, null);
                }
                if(flagLocations[x][y]){
                    g.drawImage(Images[10],x1 + GRID_X + (x * (INNER_CELL_SIZE + 1)) + 1,y1 + GRID_Y + (y * (INNER_CELL_SIZE + 1)) + 1,29,29,null);

                }

            }
        }

        if(Displaying){
            DisplayMines(g);
        }else if(!Displaying){
            UnDisplayMines();
        }

        if (numberOfSquares==0){
            GameWon = true;
        }
        if(GameOver){
            colorArray[mouseDownGridX][mouseDownGridY] = Color.RED;
            gameOver(g);
            DisplayMines(g);
        }

        if(GameWon){
            gameWon(g);
            DisplayMines(g);
        }


    }

    void DisplayMines(Graphics g){
        for (int x = 0; x < TOTAL_COLUMNS; x++) {   //The rest of the grid
            for (int y = 0; y < TOTAL_ROWS; y++) {
                if(bombLocations[x][y]){
                    Insets myInsets = getInsets();
                    int x1 = myInsets.left;
                    int y1 = myInsets.top;
                    g.drawImage(Images[9],x1 + GRID_X + (x * (INNER_CELL_SIZE + 1)) + 1,y1 + GRID_Y + (y * (INNER_CELL_SIZE + 1)) + 1,29,29,null);
                }
            }
        }
        repaint();
    }

    void UnDisplayMines(){
        for (int x = 0; x < TOTAL_COLUMNS; x++) {   //The rest of the grid
            for (int y = 0; y < TOTAL_ROWS; y++) {
                if(bombLocations[x][y]){
                    colorArray[x][y] = Color.LIGHT_GRAY;
                    if(bombsAroundXY[x][y] >= 0){
                        colorArray[x][y] =new Color(217,212,220);
                    }
                }
            }
        }
        repaint();
    }

    private void gameWon(Graphics g) {
        g.setColor(Color.RED);
        String wtxt ="Congratulations!! YOU HAVE WON.";
        int twwidth = g.getFontMetrics().stringWidth(wtxt);
        g.drawString("Congratulations!! YOU HAVE WON.",(getWidth()/2) -(twwidth/2),20);
    }

    private void gameOver(Graphics g){
        g.setColor(Color.RED);
        String ltxt ="Game Over";
        int tlwidth = g.getFontMetrics().stringWidth(ltxt);
        g.drawString(ltxt,(getWidth()/2-(tlwidth/2)),20);
    }

    @SuppressWarnings("Duplicates")
    int getGridX(int x, int y) {
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
    int getGridY(int x, int y) {
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