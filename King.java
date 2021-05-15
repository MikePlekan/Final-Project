import java.util.ArrayList;
import java.awt.*;
/**
 * Class used to represent a King piece in a game of chess
 *
 * @author Luke Jennings
 * @version Spring 2021
 */
public class King extends MovedPiece
{
    public Integer[] direction = {-1,1,-8,8,-7,7,-9,9};

    protected boolean checked = false;

    /**
     * Standard constructor for the King object.
     * 
     * @param color boolean value that represents what color the rook is, if false then queen is white, if true then queen is black
     * @param initialSquare the integer representation the square the piece is currently resting
     */
    public King(boolean color, int initialSquare){
        this.currentSquare = initialSquare;
        this.color = color;
        this.moved = false;
        if(color)file="BlackKing.png";
        else file="WhiteKing.png";
        Toolkit toolkit=Toolkit.getDefaultToolkit();
        pic=toolkit.getImage(file);
    }

    /**
     * Constructor for the King object, intended to be specifically for kings that have been
     * already moved. Important for FEN codes
     * 
     * @param color boolean value that represents what color the king is, if false then queen is white, if true then queen is black
     * @param initialSquare the integer representation the square the piece is currently resting
     * @param moved If false, rook is capable of castling, if true, rook is not able to castle.
     */
    public King(boolean color, int initialSquare, boolean moved){
        this.currentSquare = initialSquare;
        this.color = color;
        this.moved = moved;
    }

    public String getPieceStr(){
        return "K";
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
        Integer[] n = b.numSquaresToEdge.get(currentSquare);
        for(int i = 0; i < direction.length; i++){
            checkingSquare = currentSquare;
            // as kings only move one square in each direction, the while loop is removed.
            if(n[i] > 0){
                checkingSquare += direction[i]; 

                if(b.board[checkingSquare] == null){
                    //empty square
                    validMoves.add(checkingSquare);
                } else if (b.board[checkingSquare].color != color){
                    validMoves.add(checkingSquare);
                }
            }

        }
        // later we need to create a method that removes methods that result in putting yourself in check
        //removeIllegalMoves();
        return validMoves;
    }

    public void check(){
        checked = true;
    }

}
