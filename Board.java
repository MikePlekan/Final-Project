import java.util.ArrayList;

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

    public static ArrayList<Integer[]> numSquaresToEdge = new ArrayList<Integer[]>();

    protected boolean playerToMove = false;

    /**
     * Default constructor for the board class, results in the chess starting position
     */
    public Board(){

        generateNumSquaresToEdge();
    }

    /**
     * Overloaded constructor for the board class. If boolean is true, then it returns a completely empty board.
     * Otherwise, results in board being the default constructor.
     */
    public Board(boolean empty){
        generateNumSquaresToEdge();
    }

    public static void generateNumSquaresToEdge(){

        for(int i = 0; i < 64; i++){
            numSquaresToEdge.add(squaresToEdge(i));
        }
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
        squaresToEdge[7] = Math.min(squaresToEdge[1],squaresToEdge[3]);

        return squaresToEdge;
    }

    /**
     * Returns the player whose turn it is to move.
     * 
     * @return false if White's turn to move, true if Black's turn to move.
     */
    public boolean playerToMove(){
        return playerToMove;
    }

    /**
     * Moves the piece on a selectedSquare to 
     */
    public void move(int selectedSquare, int targetSquare){
        if(board[selectedSquare] != null){
            ArrayList<Integer> validMoves = board[selectedSquare].validMoves(this);
            if(validMoves.contains(targetSquare)){

            }
        }

    }

}
