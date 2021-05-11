import java.util.ArrayList;
/**
 * Abstract class used to represent a chess man
 *
 * @author Luke Jennings, Spencer Moon, Michael Plekan, and Nathan Poirier
 * @version Spring 2021
 */
public abstract class Piece
{
    //If a piece's boolean color = false, then it is a white piece, if color = true, it is black.
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
    
    public String getPieceStrColor(){
        if(color){return getPieceStr().toLowerCase();
        } else {
            return getPieceStr().toUpperCase();
        }
    }
    

    /**
     * Note: This method may be uncessary.
     * Returns the current square as given by the representation of the board class 
     */
    public int getSquare(){
        return currentSquare;
    }

    /**
     * This method allows us to change the position/square of a piece, should really only be
     * used by the board class
     *
     * @param targetSquare, sets
     */
    public void setCurrentSquare(int targetSquare){
        currentSquare = targetSquare;

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
     * Moves a piece from its currentSquare to targetSquare
     * 
     * @param Board object that the piece is currently on
     * @param targetSquare integer representation of the square where the piece is moving
     */
    public void move(Board b, int targetSquare){
        b.board[targetSquare] = this;
        b.board[currentSquare] = null;
        currentSquare = targetSquare;
        

    }

    
}
