
/**
 * Class used to represent chess moves in Long Algebraic Notation and for calculating
 * move legality.
 *
 * @author Luke Jennings
 * @version Spring 2021
 */
public class Move
{
    // instance variables - replace the example below with your own
    String s = "";
    protected int initialSquare;
    protected int targetSquare;
    protected Piece pieceCaptured;
    protected boolean previouslyMoved = false;
    protected String promotion = "";
    protected String checking = "";
    protected int previousEnPassant;

    public Move(Board b,String s, int initialSquare,int targetSquare, Piece pieceCaptured){
        if(!s.equals("P")){
            this.s = s;
        } else {
            s = "";
        }
        if(b.board[initialSquare] instanceof MovedPiece){

            MovedPiece p = (MovedPiece) b.board[initialSquare];
            previouslyMoved = p.moved;

        }
        this.initialSquare = initialSquare;
        this.targetSquare = targetSquare;
        this.pieceCaptured = pieceCaptured;
        previousEnPassant = b.enPassantSquare;

    }

    @Override
    public String toString(){
        if(!s.equals("K") && !(Math.abs(initialSquare - targetSquare) > 1)){

        } else {
            if(targetSquare - initialSquare == 2){
                return "0-0";
            } else if(targetSquare - initialSquare == -2){
                return "0-0-0";
            }
        }

        if(pieceCaptured != null){

            return s + Board.notation[initialSquare] + "x" + Board.notation[targetSquare] + promotion + checking;
        } else {
            return s + Board.notation[initialSquare] + Board.notation[targetSquare] + promotion + checking;
        }
    }

    @Override
    public boolean equals(Object o){
        if(o instanceof Move){
            Move other = (Move) o;
            if(this.initialSquare == other.initialSquare && this.targetSquare == other.targetSquare){
                return true;
            } else {
                return false;
            }
        } else {
            throw new ClassCastException("not a object of type Move");
        }

    }
}