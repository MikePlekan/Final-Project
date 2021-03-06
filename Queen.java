import java.util.ArrayList;
import java.awt.*;
/**
 * Class used to represent a Queen piece in a game of chess
 *
 * @author Luke Jennings
 * @version Spring 2021
 */
public class Queen extends Piece
{
    public Integer[] direction = {-1,1,-8,8,-7,7,-9,9};
    /**
     * Standard constructor for the Queen object.
     * 
     * @param color boolean value that represents what color the queen is, if false then queen is white, if true then queen is black
     * @param initialSquare the integer representation the square the piece is currently resting
     */
    public Queen(boolean color, int initialSquare){
        this.currentSquare = initialSquare;
        this.color = color;
        if(color)file="BlackQueen.png";
        else file="WhiteQueen.png";
        Toolkit toolkit=Toolkit.getDefaultToolkit();
        pic=toolkit.getImage(file);
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
        ArrayList<Integer> validMoves = new ArrayList<Integer>();

        // the int value of the square that is currently being checked for whether or not a piece occupies it. If no square occupies it
        // then it is added to the arraylist of valid moves
        int checkingSquare;

        // this is just to make things more efficient by escaping the inner loop once a piece has collided with another.
        boolean breakout = false;
        Integer[] n = b.numSquaresToEdge.get(currentSquare);
        int j;
        for(int i = 0; i < direction.length; i++){
            checkingSquare = currentSquare;
            breakout = false;
            j = 0;

            while(j < n[i]){
                // okay I know this looks crazy, however this is how this loop baiscally works
                // basically, we start with a piece's position, then for each direction that piece can move in, it will loop as many times
                // as there are squares to the edge of the board until it intercepts a piece, from which it will check if there
                checkingSquare += direction[i]; 

                if(b.board[checkingSquare] == null){
                    //empty square
                    validMoves.add(checkingSquare);
                } else if (b.board[checkingSquare].color != color){
                    validMoves.add(checkingSquare);
                    breakout = true;
                } else if (b.board[checkingSquare].color == color){

                    breakout = true;
                }
                
                if(!breakout){
                    // this is to make us leave the inner for loop
                    j++;
                } else {
                    j = Integer.MAX_VALUE;
                }

            }
        }
        // later we need to create a method that removes methods that result in putting yourself in check
        //removeIllegalMoves();
        return validMoves;
    }

}
