
/**
 * Abstract class used to represent a chess man
 *
 * @author Luke Jennings
 * @version Spring 2021
 */
public abstract class Piece
{
    //If a piece's boolean color = false, then it is a white piece, if color = true, it is black.
    protected Boolean color;
    
    //Used to track what square a piece in on
    protected int currentSquare;

    
    /* 
     * I don't know if this is necessary right now, but these arrays are to calculate the offsets of each direction a piece can move. 
     * For example, a bishop if on the a8 square (0), wants to move to b7, it must check if square (9) is a valid square.
     */
    //public static int[] diagonal = {-9,9,-7,7};
    
    //public static int[] horizontal = {-8, 8, -1,1};
    
    /**
     * Returns the color of a piece object as a boolean. If returns false, then it is a white piece, if black it returns true.
     * 
     * @return false if piece is white, true if piece is black.
     */
    public boolean getColor(){
        return color;
    }
    
    /**
     * Returns the color of a piece object as a string. 
     * If returns "white", then it is a white piece, if it returns "black", then it is a black piece.
     * 
     * @return "white" if piece is white, "black" if piece is black.
     */
    public String getColorStr(){
        if(color){
            return "black";
        } else {
            return "white";
        }
    }
    
    abstract public String getPieceStr();
    
    /**
     * Note: This method may be uncessary.
     * Returns the current square as given by the representation of the board class 
     */
    public int getSquare(){
        return currentSquare;
    }
        
    public String getSquareStr(){
        return Board.notation[currentSquare];
    }
    
}
