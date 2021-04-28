
/**
 * This holds 2 ints inorder to hold moves for LightsOut
 *
 * @author Spencer Moon and Micheal Plekan
 * @version Spring 2021
 */
public class TwoVal
{
    protected int x;
    protected int y;
    /**
     * this constructs TwoValLO
     * 
     * @param x this is used for the first int
     * @param y this is used for the second int
     */
    public TwoVal(int x, int y)
    {
        this.x = x;
        this.y = y;
    }

    /**
     * this gets the x value
     * 
     * @return this returns the x value
     */
    public int getX()
    {
        return x;
    }

    /**
     * this gets the y value
     * 
     * @return this returns the y value
     */
    public int getY()
    {
        return y;
    }

    /**
     * this compares this object to another
     * 
     * @param o takes another TwoValLO object
     * @return int returns 0 if equal, and -1 or 1 if < or >
     */
    public int compareTo(TwoVal o){
        return getX()-o.getX();
    }

    /**
     * this check to see if 2 objects are equal
     * @param o the object this checks
     * @return boolean true if equal otherwise false
     */
    @Override
    public boolean equals(Object o){
        if (!(o instanceof TwoVal)) {
            return false;
        }
        TwoVal v=(TwoVal)o;
        if(x==v.getX()&&y==v.getY())return true;
        return false;
    }

    /**
     * this gives a string of the information stored in this class
     * @return String the string of information
     */
    public String toString()
    {
        return "(" + x + ", " + y + ")";
    }
}
