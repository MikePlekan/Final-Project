import java.util.ArrayList;
import java.awt.*;
/**
 * Class used to represent a Knight piece in a game of chess
 *
 * @author Luke Jennings
 * @version Spring 2021
 */
public class Knight extends Piece
{
    public Integer[] direction = {-17,-10,-15,-6,15,6,17,10};

    /**
     * Standard constructor for the Knight object.
     * 
     * @param color boolean value that represents what color the queen is, if false then queen is white, if true then queen is black
     * @param initialSquare the integer representation the square the piece is currently resting
     */
    public Knight(boolean color, int initialSquare){
        this.currentSquare = initialSquare;
        this.color = color;
        if(color)file="BlackKnight.png";
        else file="WhiteKnight.png";
        Toolkit toolkit=Toolkit.getDefaultToolkit();
        pic=toolkit.getImage(file);
    }

    public String getPieceStr(){
        return "N";
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

        Integer[] n = b.numSquaresToEdge.get(currentSquare);

        //I can't think of a great way of doing this without doing brute force so here it is
        for(int i = 0; i < 2; i++){
            // i will represent up and down
            for(int j = 0; j < 2; j++){
                checkingSquare = currentSquare;
                // j will represent left and right

                if(n[i + 2] >= 2 && n[j] >= 1){
                    checkingSquare += direction[4*i + 2*j]; 

                    if(b.board[checkingSquare] == null){
                        //empty square
                        validMoves.add(checkingSquare);
                    } else if (b.board[checkingSquare].color != color){
                        validMoves.add(checkingSquare);
                    }
                }
                 checkingSquare = currentSquare;
                if(n[i + 2] >= 1 && n[j] >= 2){
                    checkingSquare += direction[4*i + 2*j + 1]; 
                    if(b.board[checkingSquare] == null){
                        //empty square
                        validMoves.add(checkingSquare);
                    } else if (b.board[checkingSquare].color != color){
                        validMoves.add(checkingSquare);
                    }
                }
            }
        }

        // later we need to create a method that removes methods that result in putting yourself in check
        //removeIllegalMoves();
        return validMoves;
    }

}
