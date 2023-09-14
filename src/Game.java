import java.lang.Math;
public class Game
{
    private GridDisplay display;
    private Block block;
    private static int score;
    public static Boolean gameOver;

    public Game()
    {
        display = new GridDisplay(20, 10);
        display.setTitle("Tetris");
        display.setLineColor(new Color(0, 0, 0));
        block = new Block(display);
        score = 0;
    }

    public void play()
    {
        while (true)
        {
            //loop to process 10 key presses in half a second
            for (int i = 0; i < 10; i++)
            {
                display.pause((int)(50.0*Math.pow(0.999, Math.pow(score,1.3))));//gradually increases game speed with score
                int key = display.checkLastKeyPressed();
                if (key == 37)//left arrow pressed
                {
                    block.shift(0,-1);

                }
                else if (key == 38)//up arrow pressed
                {
                   block.rotate();
                }
                else if (key == 39)//right arrow pressed
                {
                    block.shift(0, 1);

                }
                else if (key == 40)//down arrow pressed
                {
                    block.shift(1, 0);

                }
                else if (key == 32)//space bar pressed
                {
                	while(block.shift(1,0)) {}
                }
            } //end of for-loop
            if(!block.shift(1,0)) {
            	removeCompletedRows();
            	block = new Block(display);
            	updateScore();
            }
        }
    }

    public boolean isCompletedRow(int row)
    {
        for(int i=0; i<display.getNumCols(); i++) {//traverses each column
        	if(display.getColor(new Location(row, i)).equals(new Color(0,0,0))) {//checks if square at each column is black
        		return false;
        	}
        }
    	return true;
    }

    public void removeSquare(Location loc)
    {
    	for(int i=loc.getRow(); i>0; i--) {//traverses each row upwards
    		display.setColor(new Location(i, loc.getCol()), display.getColor(new Location(i-1, loc.getCol())));//sets the current pixel to the color of the pixel above
    	}
    	display.setColor(new Location(0, loc.getCol()), new Color(0,0,0));//sets top pixel to black
    }

    public void removeRow(int row)
    {
    	for(int i=0; i<display.getNumCols(); i++) {
    		removeSquare(new Location(row, i));
    	}
    }

    public void removeCompletedRows()
    {
    	int comboCounter = 1;
    	for(int i=0; i < display.getNumRows(); i++) {
    		if(isCompletedRow(i)) {
    			removeRow(i);
    			score+=(int)(10*comboCounter*(1/Math.pow(0.9985, score)));
    			comboCounter++;
    		}
    	}
    }
    
    public static int getScore() {
    	return score;
    }
    
    public void updateScore() {
    	display.setTitle("Tetris" + "            Score:" + score);
    }
}