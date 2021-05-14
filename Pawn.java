import java.util.ArrayList;
/**
 * Class used to represent a Pawn piece in a game of chess
 *
 * @author Luke Jennings
 * @version Spring 2021
 */
public class Pawn extends MovedPiece
{
    public Integer[] direction = {-8,8,-7,7,-9,9};

    public boolean enPassantable;

    public boolean promotable;

    /**
     * Standard constructor for the Pawn object.
     * 
     * @param color boolean value that represents what color the rook is, if false then queen is white, if true then queen is black
     * @param initialSquare the integer representation the square the piece is currently resting
     */
    public Pawn(boolean color, int initialSquare){
        this.currentSquare = initialSquare;
        this.color = color;
        this.moved = false;
    }

    /**
     * Constructor for the Pawn object, intended to be specifically for kings that have been
     * already moved. Important for FEN codes
     * 
     * @param color boolean value that represents what color the king is, if false then queen is white, if true then queen is black
     * @param initialSquare the integer representation the square the piece is currently resting
     * @param moved If false, rook is capable of castling, if true, rook is not able to castle.
     */
    public Pawn(boolean color, int initialSquare, boolean moved){
        this.currentSquare = initialSquare;
        this.color = color;
        this.moved = moved;
    }

    public String getPieceStr(){
        return "P";
    }

    @Override 
    public String toString(){
        return getSquareStr();
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
        int checkingSquare = currentSquare;

        // this is to help whether or not a pawn is black or white in its valid move calculations. If black, then x = 1, else x = 0;
        int x = color ? 1 : 0;

        Integer[] n = b.numSquaresToEdge.get(currentSquare);
        int j = 0;
        if(moved){
            j = 1; 
        }

        int i = direction[x];

        // basically x represents whether or not a pawn goes up the board or down the board, i is simply the offset in the direction of x
        // and j is just a loop variable

        // this loop checks whether or not a pawn can move 1 or 2 squares
        while(j < n[x + 2] && j < 2){
            checkingSquare += direction[x]; 

            if(b.board[checkingSquare] == null){
                //empty square
                validMoves.add(checkingSquare);
                
            }
            j++;
        }

        checkingSquare = currentSquare;

        if(n[4 + x] > 0){

            checkingSquare += direction[2 + x]; 
            if(b.board[checkingSquare] != null){
                if(b.board[checkingSquare].color != color){
                    //if square is occupied by a piece of an opposing color
                    validMoves.add(checkingSquare);
                }
            }
            // if(color && b.enPassantSquare + 8 == checkingSquare){
                //attempted to add in enpassant implementation
            // }
        }

        checkingSquare = currentSquare;
        if(n[6 + x] > 0){
            checkingSquare += direction[4 + x]; 
            if(b.board[checkingSquare] != null){
                if(b.board[checkingSquare].color != color){
                    //if square is occupied by a piece of an opposing color
                    validMoves.add(checkingSquare);
                }
            }

        }
        
        // //this ungodly codeblock checks for en passant moves
        // checkingSquare = currentSquare;
        // if(!color){
            // if(n[4 + x] > 0){
                // checkingSquare += direction[2+x];
                // if(b.board[currentSquare + 1] != null && b.board[currentSquare] instanceof Pawn){
                    // Pawn p = (Pawn) b.board[currentSquare];
                    // if(p.enPassantable){
                        // validMoves.add(checkingSquare);
                    // }
                    
                // }
            // }
            // checkingSquare = currentSquare;
            // if(n[6 + x] > 0){
                
            // }
        // }

        // later we need to create a method that removes methods that result in putting yourself in check
        //removeIllegalMoves();
        return validMoves;
    }

    /**
     * Moves a piece from its currentSquare to targetSquare
     * 
     * @param Board object that the piece is currently on
     * @param targetSquare integer representation of the square where the piece is moving
     */
    @Override
    public void move(Board b, int targetSquare){
        if(Math.abs(currentSquare - targetSquare) == 16){
            b.enPassantSquare = targetSquare;
        }
        b.board[targetSquare] = this;
        b.board[currentSquare] = null;
        currentSquare = targetSquare;
        moved = true;

    }

    /**
     * This method allows us to set whether or not a pawn is capable of being captured by en passant
     * 
     * @param b true if a pawn has just been moved for the first time 2 spaces, false if not
     */
    public void setEnPassantable(boolean b){
        enPassantable = b;
    }
}
