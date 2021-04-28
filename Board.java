
/**
 * Write a description of class Board here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class Board
{
    public Square[][] squares;
    public Piece[][] pieces;
    public Board()
    {
        squares = new Square[8][8];
        pieces = new Piece[8][8];
        
        for (int i = 0; i < 8; i++)
        {
            for (int j = 0; j < 8; j++)
            {
                squares[i][j] = new Square(i + 1, j + 1);
            }
        }
        
        setBoard();
    }
    
    public void setBoard()
    {
        pieces[1][2] = new Pawn(1, 2);
    }
}
