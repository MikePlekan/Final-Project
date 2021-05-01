import java.util.ArrayList;
/**
 * Write a description of class Pawn here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class WhitePawn extends Piece
{
    boolean moved;
    TwoVal position;
    public static final int VALUE = 1;
    public static final String color = "WHITE";
    
    public WhitePawn(int x, int y)
    {
        position = new TwoVal(x, y);
        
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
