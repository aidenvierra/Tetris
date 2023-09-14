public class Block
{
    private GridDisplay display;
    private Color color;
    private Location[] locs;

    public Block(GridDisplay disp)
    {
        int shapeNum = (int)(Math.random() * 3);
        if (shapeNum == 0)
        {
        	locs = new Location[3];
        	locs[0] = new Location(0,4);
        	locs[1] = new Location(0,3);
        	locs[2] = new Location(0,5);
            color = new Color(255, 0, 255);
        }
        else if (shapeNum == 1)
        {
        	locs = new Location[4];
        	locs[0] = new Location(0,4);
        	locs[1] = new Location(0,5);
        	locs[2] = new Location(1,4);
        	locs[3] = new Location(1,5);
        	color = new Color(255, 255, 0);
        }
        else
        {
        	locs = new Location[4];
        	locs[0] = new Location(0,4);
        	locs[1] = new Location(0,3);
        	locs[2] = new Location(0,5);
        	locs[3] = new Location(1,4);
        	color = new Color(0, 255, 255);
        }
        display = disp;
        if(!areValidAndEmpty(locs)) {
            display.endGame();
            display.showMessageDialog("Game Over \n Score: " + Game.getScore() + "\nPress OK to play again");
        }
        drawSelf(color);
    }

    public void drawSelf(Color color)
    {
    	for(int i = 0; i < locs.length; i++) {
    		display.setColor(locs[i], color);
    	}
    }

    public boolean areValidAndEmpty(Location[] locs)
    {
        for(int i = 0; i < locs.length; i++) {
        	if(!display.isValid(locs[i])) {return false;} //checks if valid
        	if(!display.getColor(locs[i]).equals(new Color(0,0,0))) {return false;}//checks if empty
        }    	
    	return true; 
    }

    public boolean shift(int deltaRow, int deltaCol)
    {
        for(Location loc: locs) {
        	display.setColor(loc, new Color(0,0,0));
        }
        Location[] newLocs = new Location[locs.length];
        for(int i = 0; i<locs.length; i++) {
        	newLocs[i] = new Location(locs[i].getRow()+deltaRow, locs[i].getCol()+deltaCol);
        }
        if(areValidAndEmpty(newLocs)) {
        	locs = newLocs;
            drawSelf(color);
        }
        else {
            drawSelf(color);
        	return false;
        }
        return true;
    }

    public void rotate()
    {
    	for(Location loc: locs) {
        	display.setColor(loc, new Color(0,0,0));
        }
        Location[] newLocs = new Location[locs.length];
        Location loc0 = locs[0];
        newLocs[0] = locs[0];
        for(int i = 1; i<locs.length; i++) {
        	newLocs[i] = new Location(loc0.getRow()+locs[i].getCol()-loc0.getCol(), loc0.getCol()+loc0.getRow()-locs[i].getRow());
        }
        if(areValidAndEmpty(newLocs)) {
        	locs = newLocs;
        }
        drawSelf(color);
    }
}