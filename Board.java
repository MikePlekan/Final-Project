import java.util.Arrays;

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
//<<<<<<< HEAD
        
    //these integers represent squares that are along the edge of the board.
    
    // public static Integer[] topEdge = {0,1,2,3,4,5,6,7};
    // public static Integer[] leftEdge = {0,8,16,24,32,40,48,56};
    // public static Integer[] rightEdge = {7,15,23,31,39,47,55,63};
    // public static Integer[] bottomEdge = {56,57,58,59,60,61,62,63};
    
    /*
     * 1 represents left and right, 7 represents diagonaly on the left to right diagonal, 8 represents up and down,
     * 9 represents digonally right to left diagonal
     * -1 = left
     * 1 = right
     * -8 = up
     * 8 = down
     * -7 = diagonal up right
     * 7 = diagonal down left
     * -9 = diagonal up left
     * 9 = diagonal down right    
     */
    public static Integer[] directions = {-1,1,-8,8,-7,7,-9,9};
    
    public static Integer[][] numSquaresToEdge = generateNumSquaresToEdge();
   
    public static Integer[][] generateNumSquaresToEdge(){
        Integer[][] output = new Integer[64][8];
        for(int i = 0; i < 64; i++){
            numSquaresToEdge[i]= squaresToEdge(i);
        }
        return output;
    }
    
    /**
     * Gives the number of squares from the edge of the board from a given square in all directions.
     * This is used to help calculate valid moves so pieces dont go off the board.
     * Diagonals can be calculated by taking the minumum between the left-right, top-bottom pair combinations
     * 
     * @return an Integer[] that contains the number of square from the edge of the board in this sequence 
     * [left,right, top, bottom,topright,bottomleft,topleft,bottomright]
     */
    public static Integer[] squaresToEdge(int currentSquare){
        Integer[] squaresToEdge = new Integer[8];
        
        squaresToEdge[0] = currentSquare % 8;
        squaresToEdge[1] = 7 - currentSquare % 8;
        squaresToEdge[2] = currentSquare / 8;
        squaresToEdge[3] = 7 - currentSquare / 8;
        
        squaresToEdge[4] = Math.min(squaresToEdge[1],squaresToEdge[2]);
        squaresToEdge[5] = Math.min(squaresToEdge[0],squaresToEdge[3]);
        squaresToEdge[6] = Math.min(squaresToEdge[0],squaresToEdge[2]);
        squaresToEdge[7] = Math.min(squaresToEdge[1],squaresToEdge[1]);
        
        return squaresToEdge;
    }
    
//=======
    
        
    //Other potential board implementation
   /* public Square[][] squares;
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
           */
//>>>>>>> 5249193d1da9094a7732cec0a0105684a88f2415
}
