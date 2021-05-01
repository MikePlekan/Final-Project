
/**
 * This class is used to represent the logical representation of a board, rather than
 * including JavaFX comments, this class is used to help pieces calculate legal moves,
 * 
 *
 * @author Luke Jennings
 * @version Spring2021
 */
public class Board
{
    /* So I did some research, I personally think that storing all pieces in an array of
     * 64 piece objects is way easier to calculate valid moves than an 8x8 double array
     * The reason being is that instead of having to return both a rank (row) and file (column)
     * for every single calculation, we could instead use a single number to represent each square.
     * 
     * Also this would make a method that could be used to load board states easier to program, as the
     * current standard FEN takes a similar approach when loading in pieces.
     * As such 0 will represent the square a8, and 63 will represent h1.
     */
    
    protected Piece[] board = new Piece[64];
    
    public static String[] notation = {"a8", "b8", "c8", "d8", "e8", "f8", "g8", "h8",
        "a7", "b7", "c7", "d7", "e7", "f7", "g7", "h7",
        "a6", "b6", "c6", "d6", "e6", "f6", "g6", "h6",
        "a5", "b5", "c5", "d5", "e5", "f5", "g5", "h5",
        "a4", "b4", "c4", "d4", "e4", "f4", "g4", "h4",
        "a3", "b3", "c3", "d3", "e3", "f3", "g3", "h3",
        "a2", "b2", "c2", "d2", "e2", "f2", "g2", "h2",
        "a1", "b1", "c1", "d1", "e1", "f1", "g1", "h1"};
        
    public Board(){
        
    }
    
}
