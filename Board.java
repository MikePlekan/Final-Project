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

    protected ArrayList<Integer> whitePieces = new ArrayList<Integer>();

    protected ArrayList<Integer> blackPieces = new ArrayList<Integer>();

    protected King whiteKing;

    protected King blackKing;

    protected int enPassantSquare = -1;

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

    protected Boolean winner = null;

    /**
     * Default constructor for the board class, results in the chess starting position
     */
    public Board(){

        generateNumSquaresToEdge();

        placePiece("r",0);
        placePiece("n",1);
        placePiece("b",2);
        placePiece("q",3);
        placePiece("k",4);
        placePiece("b",5);
        placePiece("n",6);
        placePiece("r",7);
        for(int i = 8; i < 16; i++){
            placePiece("p",i);
        }
        for(int i = 48; i < 56; i++){
            placePiece("P",i);
        }
        placePiece("R",56);
        placePiece("N",57);
        placePiece("B",58);
        placePiece("Q",59);
        placePiece("K",60);
        placePiece("B",61);
        placePiece("N",62);
        placePiece("R",63);

    }

    /**
     * Constructor that takes a string FEN input
     */
    public Board(String fen){

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
     * Moves the piece on a selectedSquare to targetSquare if it is a valid move
     * 
     * @param selectedSquare integer representation of the square of a piece you wish to move
     * @param targetSquare integer representation of the square where the piece is being moved too
     */
    public void movePiece(int selectedSquare, int targetSquare){

        if(targetSquare < 64 && targetSquare > -1){
            if(board[selectedSquare] != null){
                ArrayList<Integer> validMoves = board[selectedSquare].validMoves(this);
                if(validMoves.contains(targetSquare)){
                    board[selectedSquare].move(this,targetSquare);

                }
            }
        } else {
            throw new IndexOutOfBoundsException("targetSquare is out of bounds");
        }
        for(Piece p: board){
            if(p instanceof Pawn){
                Pawn other = (Pawn) p;
                other.setEnPassantable(false);
            }
        }

    }

    public void placePiece(String s, int targetSquare){
        if(targetSquare < 64 && targetSquare > -1){
            switch(s){
                case "K":
                if(whiteKing == null){
                    whiteKing = new King(false,targetSquare);
                    board[targetSquare] = whiteKing;

                }
                break;
                case "Q":
                board[targetSquare] = new Queen(false,targetSquare);
                break;
                case "R":
                board[targetSquare] = new Rook(false,targetSquare);
                break;
                case "B":
                board[targetSquare] = new Bishop(false,targetSquare);
                break;
                case "N":
                board[targetSquare] = new Knight(false,targetSquare);
                break;
                case "P":
                board[targetSquare] = new Pawn(false,targetSquare);
                break;

                case "k":
                if(blackKing == null){
                    blackKing = new King(true,targetSquare);
                    board[targetSquare] = blackKing;
                }
                break;
                case "q":
                board[targetSquare] = new Queen(true,targetSquare);
                break;
                case "r":
                board[targetSquare] = new Rook(true,targetSquare);
                break;
                case "b":
                board[targetSquare] = new Bishop(true,targetSquare);
                break;
                case "n":
                board[targetSquare] = new Knight(true,targetSquare);
                break;
                case "p":
                board[targetSquare] = new Pawn(true,targetSquare);
                break;

            }
        } else {
            throw new IndexOutOfBoundsException("targetSquare is out of bounds");
        }

    }

    public boolean check(){
        return false;
    }

    public Boolean getWinner(){
        return winner;
    }

    @Override
    public String toString(){
        StringBuffer sb = new StringBuffer();
        int n;
        for(int i = 0; i < 8; i++){
            for(int j = 0; j < 8; j++){
                n = 8 * i + j;
                if(board[n] != null){
                    if(board[n].color){
                        sb.append(board[n].getPieceStr().toLowerCase() + " ");

                    } else {
                        sb.append(board[n].getPieceStr() + " ");
                    }

                } else {
                    sb.append("_ ");
                }

            }
            sb.append("\n");
        }

        return sb.toString();
    }

}
