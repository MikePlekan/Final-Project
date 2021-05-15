import java.util.ArrayList;
import java.awt.*;
/**
 * Class used to represent a Rook piece in a game of chess
 *
 * @author Luke Jennings
 * @version Spring 2021
 */
public class Rook extends MovedPiece
{
    public Integer[] direction = {-1,1,-8,8};

    /**
     * Standard constructor for the Rook object.
     * 
     * @param color boolean value that represents what color the rook is, if false then queen is white, if true then queen is black
     * @param initialSquare the integer representation the square the piece is currently resting
     */
    public Rook(boolean color, int initialSquare){
        this.currentSquare = initialSquare;
        this.color = color;
        this.moved = false;
        if(color)file="BlackRook.png";
        else file="WhiteRook.png";
        Toolkit toolkit=Toolkit.getDefaultToolkit();
        pic=toolkit.getImage(file);
    }
    
    /**
     * Constructor for the Rook object, intended to be specifically for rooks that have been
     * promoted or are otherwise unable to castle. Important for FEN codes
     * 
     * @param color boolean value that represents what color the rook is, if false then queen is white, if true then queen is black
     * @param initialSquare the integer representation the square the piece is currently resting
     * @param moved If false, rook is capable of castling, if true, rook is not able to castle.
     */
    public Rook(boolean color, int initialSquare, boolean moved){
        this.currentSquare = initialSquare;
        this.color = color;
        this.moved = moved;
    }

    

    public String getPieceStr(){
        return "R";
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
