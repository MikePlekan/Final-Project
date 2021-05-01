import java.util.ArrayList;
/**
 * Abstract class used to represent a chess man
 *
 * @author Luke Jennings, Spencer Moon, Michael Plekan, and Nathan Poirier
 * @version Spring 2021
 */
public abstract class Piece
{
    //If a piece's boolean color = true, then it is a white piece, if color = false, it is black.
    protected Boolean color;
    
    //Used to track what square a piece in on
    protected int currentSquare;
    
    abstract public ArrayList<Integer> validMoves(Board b);
    
    /**
     * Returns the color of a piece object as a boolean. If returns false, then it is a black piece, if white it returns true.
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
        
    /**
     * Gives the algebraic notation of the current square a piece is resting upon
     * 
     * @return a string of the algebraic notation of the current square
     */
    public String getSquareStr(){
        return Board.notation[currentSquare];
    }
    
    /**
     * Gives the number of squares from the edge of the board from a given square in all directions.
     * This is used to help calculate valid moves so pieces dont go off the board.
     * Diagonals can be calculated by taking the minumum between the left-right, top-bottom pair combinations
     * 
     * @return an Integer[] that contains the number of square from the edge of the board in this sequence 
     * [left,right, top, bottom,topright,bottomleft,topleft,bottomright]
     */
    public static Integer[] squaresToEdge(int currentSquare){
        Integer[] squaresToEdge = new Integer[8];
        
        squaresToEdge[0] = currentSquare % 8;
        squaresToEdge[1] = 7 - currentSquare % 8;
        squaresToEdge[2] = currentSquare / 8;
        squaresToEdge[3] = 7 - currentSquare / 8;
        
        squaresToEdge[4] = Math.min(squaresToEdge[1],squaresToEdge[2]);
        squaresToEdge[5] = Math.min(squaresToEdge[0],squaresToEdge[3]);
        squaresToEdge[6] = Math.min(squaresToEdge[0],squaresToEdge[2]);
        squaresToEdge[7] = Math.min(squaresToEdge[1],squaresToEdge[1]);
        
        return squaresToEdge;
    }
    
    
}
