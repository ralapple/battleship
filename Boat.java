//lyon0210
//yurev003


public class Boat {
    private int size; //size of boat
    private boolean orien; //orientation of boat
    private Cell[] b1; //cell array of coordinates of the boat
    public Boat(int s) //sets the boat size and makes the cell array null.
    {
        this.size = s;
        b1 = null;
    }
    public boolean getOrientation()
    {
        return orien;
    }
    public void setOrientation(boolean o)
    {
        orien = o; //true is vertical, false is horizontal
    }
    public int getSize()
    {
        return size;
    }
    public Cell[] getB1()
    {
        return b1;
    }
    public void setB1(Cell[] cell1)
    {
        b1 = cell1;
    }
}