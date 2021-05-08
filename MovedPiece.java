
/**
 * This abstract class is utilized for pieces who need to check whether or not they have previously moved
 * in order to calculate valid moves. For example pawns if they have already moved, so they can no longer move
 * 2 squares in a turn, or kings and rooks in determining if castling is legal.
 *
 * @author Luke Jennings
 * @version Spring 2021
 */
public abstract class MovedPiece extends Piece
{
    protected boolean moved = false;
    
    public void moved(){
        moved = true;
    }
    
    public boolean hasMoved(){
        return moved;
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
        moved = true;
        

    }
}
