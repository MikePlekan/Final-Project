import java.util.ArrayList;
/**
 * Write a description of class Pawn here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class Pawn extends Piece
{
    boolean moved;
    TwoVal position;
    
    public Pawn()
    {
        moved = false;
    }
    @Override
    public ArrayList<TwoVal> validMoves()
    {
        ArrayList<TwoVal> moves = new ArrayList();
        if (!moved)
        {
            moves.add(new TwoVal(position.x, position.y + 2));
        }
        
        moves.add(new TwoVal(position.x, position.y + 2));
        
        return moves;
    }
}
