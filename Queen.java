import java.util.ArrayList;
/**
 * Class used to represent a Queen piece in a game of chess
 *
 * @author Luke Jennings
 * @version Spring 2021
 */
public class Queen extends Piece
{
    public int[] direction = Board.directions;
    
    /**
     * Standard constructor for the Queen object.
     * 
     * @param color boolean value that represents what color the queen is, if false then queen is white, if true then queen is black
     * @param 
     */
    public Queen(boolean color, int initialSquare){
        this.currentSquare = initialSquare;
        this.color = color;
    }
    
    /**
     * This constructor, although likely unnessary, assumes that the queen is starting in its standard board position.
     * Where if black, the Queen's currentSquare is d8, or 3, and if White, the Queen's current square is d1, or 59
     */
    public Queen(boolean color){
        this.color = color;
        if(color){
            this.currentSquare = 3;
        } else {
            this.currentSquare = 59;
        }
        
    }
    public String getPieceStr(){
        return "Q";
    }
    
    @Override 
    public String toString(){
        return getPieceStr() + getSquareStr();
    }
    
    /**
     * Note: This will likely have to be heavily modified to make sure you are not moving into check, however right now this is a prelim
     * implementation. Maybe a potential fix is to generate a new board which checks if a move places yourself into check, but that can be
     * figured out later.
     */
    public ArrayList<Integer> validMoves(Board b){
        
        return null;
    }
    
}
