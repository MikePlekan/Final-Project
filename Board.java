import java.awt.Point;
import java.util.Arrays;
import java.util.ArrayList;
import java.util.ArrayDeque;

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
    public Piece[] board = new Piece[64];

    // protected ArrayList<Integer> whitePieces = new ArrayList<Integer>();

    // protected ArrayList<Integer> blackPieces = new ArrayList<Integer>();

    protected King whiteKing;

    protected King blackKing;

    protected int enPassantSquare = -1;

    protected ArrayDeque<Move> moves = new ArrayDeque<Move>();

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

    protected boolean gameEnded = false;

    // if Q, queen, if N, then knight, if R, then rook, if B, then bishop
    protected String promoteTo = "Q";

    /**
     * Default constructor for the board class, results in the chess starting position
     */
    public Board(){

        generateNumSquaresToEdge();

        placePiece('r',0);
        placePiece('n',1);
        placePiece('b',2);
        placePiece('q',3);
        placePiece('k',4);
        placePiece('b',5);
        placePiece('n',6);
        placePiece('r',7);
        for(int i = 8; i < 16; i++){
            placePiece('p',i);
        }
        for(int i = 48; i < 56; i++){
            placePiece('P',i);
        }
        placePiece('R',56);
        placePiece('N',57);
        placePiece('B',58);
        placePiece('Q',59);
        placePiece('K',60);
        placePiece('B',61);
        placePiece('N',62);
        placePiece('R',63);

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
     * @param selectedSquare string presentation of the square of a piece you wish to move
     * @param targetSquare string representation of the square where the piece is being moved too
     */
    public boolean movePiece(String selectedSquare, String targetSquare){
        int select = Arrays.asList(notation).indexOf(selectedSquare);
        int target = Arrays.asList(notation).indexOf(targetSquare);
        return movePiece(select,target);

    }

    /**
     * Moves the piece on a selectedSquare to targetSquare if it is a valid move
     * 
     * @param selectedSquare integer representation of the square of a piece you wish to move
     * @param targetSquare integer representation of the square where the piece is being moved too
     */
    public boolean movePiece(int selectedSquare, int targetSquare){
        boolean legal = false;
        //we must check that the game has not ended first, as if there is a checkmate or stalemate the game has come to an end
        if(!gameEnded){

            if(targetSquare < 64 && targetSquare > -1){
                if(board[selectedSquare] != null && board[selectedSquare].color == playerToMove){
                    //first we generate all our possible legal moves, then we check if the move we want to make is allowed.

                    ArrayList<Move> legalMoves = generateLegalMoves(board[selectedSquare].color);
                    Move desiredMove;
                    int x = board[selectedSquare].color ? -1 : 1;
                    // this is to figure out if a move is en passant and if it is legal
                    if(board[selectedSquare] instanceof Pawn && board[targetSquare] == null && enPassantSquare == targetSquare + (x * 8)){
                        desiredMove = new Move(this,board[selectedSquare].getPieceStr(),selectedSquare,targetSquare, board[enPassantSquare]);
                    }
                    desiredMove = new Move(this,board[selectedSquare].getPieceStr(),selectedSquare,targetSquare, board[targetSquare]);

                    if(legalMoves.contains(desiredMove)){
                        moves.push(desiredMove);
                        board[selectedSquare].move(this,targetSquare);
                        legal = true;

                        //code to check for castling
                        if(board[targetSquare] instanceof King){
                            if(Math.abs(selectedSquare - targetSquare) == 2){
                                if(selectedSquare < targetSquare){
                                    Rook r = (Rook) board[selectedSquare + 3];
                                    board[selectedSquare + 1] = r;
                                    r.moved = true;
                                    r.currentSquare = selectedSquare + 1;
                                    board[selectedSquare + 3] = null;
                                } else {
                                    Rook r = (Rook) board[selectedSquare - 4];
                                    board[selectedSquare - 1] = r;
                                    r.moved = true;
                                    r.currentSquare = selectedSquare - 1;
                                    board[selectedSquare - 4] = null;
                                }
                            }
                        }

                        //code for promotion here, only checks if the piece is a pawn and if it has reached the end of the board
                        // check the promote method for more info
                        if(board[targetSquare] instanceof Pawn){
                            if(promote(targetSquare)){ 
                                desiredMove.promotion = "=" + promoteTo;
                            }
                            if(enPassantSquare == targetSquare + (x * 8)){
                                desiredMove.pieceCaptured = board[enPassantSquare];
                                board[enPassantSquare] = null;

                            } else {
                                if(Math.abs(selectedSquare - targetSquare) == 16){
                                    enPassantSquare = targetSquare;
                                } else {
                                    enPassantSquare = -2;
                                }
                            }

                        } else { 
                            enPassantSquare = -2;
                        }

                        //code for checking if a king is in check, this impacts whether a player is in checkmate or stalemate
                        ArrayList<Integer> checkForCheck = allAttackedSquares(board[targetSquare].color);
                        if(board[targetSquare].color) { // if the piece is black
                            if(whiteKing != null && checkForCheck.contains(whiteKing.currentSquare)){
                                whiteKing.checked = true;
                                desiredMove.promotion += "+";
                            } else if (whiteKing != null){
                                whiteKing.checked = false;
                            }
                            if(blackKing != null){
                                blackKing.checked = false;
                            }

                        } else { // if the piece that was moved was white
                            if(blackKing != null && checkForCheck.contains(blackKing.currentSquare)){
                                blackKing.checked = true;
                                desiredMove.promotion = "+";
                            } else if (blackKing != null) {
                                blackKing.checked = false;
                            }
                            if(whiteKing != null){
                                whiteKing.checked = false;
                            }

                        }
                        //this is to check for any possible checkmate or stalemate after a move is played. Yes I know this is inefficient,
                        ArrayList<Move> opponentMoves = generateLegalMoves(!board[targetSquare].color);
                        if(opponentMoves != null && opponentMoves.size() == 0){
                            endGame();
                            if(winner != null){
                                desiredMove.promotion = "#";
                            }
                        }
                        playerToMove = !playerToMove;

                    }

                }

            }else {
                throw new IndexOutOfBoundsException("targetSquare is out of bounds");
            } 
        }

        return legal;
    }

    /**
     * Helper method that promotes a pawn that has reached the end of the board.
     */
    private boolean promote(int targetSquare){
        if(targetSquare / 8 == 0){
            if(!promoteTo.equals("R")){
                placePiece(promoteTo.toUpperCase(),targetSquare);
            } else if (promoteTo.equals("R")){
                board[targetSquare] = new Rook(false,targetSquare,true);
            }
            return true;
        } else if (targetSquare / 8 == 7){
            if(!promoteTo.equals("R")){
                placePiece(promoteTo.toLowerCase(),targetSquare);
            } else if (promoteTo.equals("R")){
                board[targetSquare] = new Rook(true,targetSquare,true);
            }
            return true;
        }
        return false;
    }

    protected void checkMovePiece(int selectedSquare, int targetSquare){
        if(targetSquare < 64 && targetSquare > -1){
            if(board[selectedSquare] != null){
                ArrayList<Integer> validMoves = board[selectedSquare].validMoves(this);

                Move desiredMove = new Move(this,board[selectedSquare].getPieceStr(),selectedSquare,targetSquare, board[targetSquare]);
                if(validMoves.contains(targetSquare)){
                    moves.push(new Move(this,board[selectedSquare].getPieceStr(),selectedSquare,targetSquare, board[targetSquare]));
                    board[selectedSquare].move(this,targetSquare);
                }

            }
        } else {
            throw new IndexOutOfBoundsException("targetSquare is out of bounds");
        }

    }

    public void undoMove(){
        //gets the last move made by the moves deque
        Move undoneMove = moves.pop();
        //moves the moved piece back to its original square
        enPassantSquare = undoneMove.previousEnPassant;
        placePiece(board[undoneMove.targetSquare],undoneMove.initialSquare);
        board[undoneMove.targetSquare] = null;
        board[undoneMove.initialSquare].currentSquare = undoneMove.initialSquare;

        // if a piece was captured, it puts it back to the square it was on
        if(undoneMove.pieceCaptured != null){
            placePiece(undoneMove.pieceCaptured,undoneMove.pieceCaptured.currentSquare);

        }

        // If a piece was previously moved or not moved then when we move the piece back we make sure it is in its proper state

        if(board[undoneMove.initialSquare] instanceof MovedPiece){
            MovedPiece p = (MovedPiece) board[undoneMove.initialSquare];
            p.moved = undoneMove.previouslyMoved;
        }

    }

    /**
     * Places piece on board. 
     * 
     * @param p Piece to be placed on the board
     * @param targetSquare integer representation of the square the piece is being placed on
     */
    public void placePiece(Piece p, int targetSquare){
        board[targetSquare] = p;
    }

    /**
     * Places piece on board
     * 
     * @param c char representation of a piece, valid inputs are K,Q,R,N,B,P
     * if pawn is black, the char should be lowercase.
     * @param targetSquare integer representation of the square the piece is being placed on
     */
    public void placePiece(char c, int targetSquare){
        if(targetSquare < 64 && targetSquare > -1){
            switch(c){
                case 'K':
                if(whiteKing == null){
                    whiteKing = new King(false,targetSquare);
                    board[targetSquare] = whiteKing;

                }
                break;
                case 'Q':
                board[targetSquare] = new Queen(false,targetSquare);
                break;
                case 'R':
                board[targetSquare] = new Rook(false,targetSquare);
                break;
                case 'B':
                board[targetSquare] = new Bishop(false,targetSquare);
                break;
                case 'N':
                board[targetSquare] = new Knight(false,targetSquare);
                break;
                case 'P':
                board[targetSquare] = new Pawn(false,targetSquare);
                break;

                case 'k':
                if(blackKing == null){
                    blackKing = new King(true,targetSquare);
                    board[targetSquare] = blackKing;
                }
                break;
                case 'q':
                board[targetSquare] = new Queen(true,targetSquare);
                break;
                case 'r':
                board[targetSquare] = new Rook(true,targetSquare);
                break;
                case 'b':
                board[targetSquare] = new Bishop(true,targetSquare);
                break;
                case 'n':
                board[targetSquare] = new Knight(true,targetSquare);
                break;
                case 'p':
                board[targetSquare] = new Pawn(true,targetSquare);
                break;

            }
        } else {
            throw new IndexOutOfBoundsException("targetSquare is out of bounds");
        }

    }

    /**
     * Places piece on board.This method should only be used in the Board constructors.
     * 
     * @param s String representation of a piece, valid inputs are K,Q,R,N,B,P
     * if pawn is black, the string should be lowercase.
     * @param targetSquare integer representation of the square the piece is being placed on
     */
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

    /**
     * This method, although possibly unnecessary, but this method checks turn order and special move types
     * 
     * @param selectedSquare integer representation of the square of a piece you wish to move
     * @param targetSquare integer representation of the square where the piece is being moved too
     * @param promote in case of 
     */
    public boolean turn(int selectedSquare, int targetSquare, String promote){
        return false;
    }

    /**
     * This is simply a helper method to make sure to calculate if a king is in check or not
     */
    private ArrayList<Integer> allAttackedSquares(boolean color){
        ArrayList<Integer> validMoves = new ArrayList<Integer>();
        for(int i = 0; i < 64; i++){
            if(board[i] != null && board[i].color == color){
                validMoves.addAll(board[i].validMoves(this));

            }
        }
        return validMoves;
    }

    public ArrayList<Move> generateMoves(boolean color){
        ArrayList<Move> allValidMoves = new ArrayList<Move>();
        ArrayList<Integer> validMoves;
        for(int i = 0; i < 64; i++){
            if(board[i] != null && board[i].color == color){
                validMoves = board[i].validMoves(this);
                for(Integer j: validMoves){
                    int x = board[i].color ? 1 : -1;
                    if((board[i] instanceof Pawn && board[j] == null && enPassantSquare > 0 && enPassantSquare == j + (x * 8))){
                        allValidMoves.add(new Move(this,board[i].getPieceStr(),i,j, board[enPassantSquare]));
                    } else {
                        allValidMoves.add(new Move(this,board[i].getPieceStr(),i,j,board[j]));
                    }
                }
            }
        }
        return allValidMoves;
    }

    /**
     * This currently does not work at all. Not even close.
     */
    public ArrayList<Move> generateLegalMoves(boolean color){
        ArrayList<Move> psuedoLegalMoves = generateMoves(color);
        ArrayList<Move> illegalMoves = new ArrayList<Move>();
        ArrayList<Move> opponentsResponses;
        for(Move m: psuedoLegalMoves){
            // generate all possible moves our opponent can play against us
            checkMovePiece(m.initialSquare,m.targetSquare);
            opponentsResponses = generateMoves(!color);

            if(!color){ // if the input color is white
                for(Move om : opponentsResponses){

                    //If our opponent has a move that targets our king after we make a move, that means we moved into check which is not allowed
                    //As such we much remove it from our list of moves
                    if(whiteKing != null && om.targetSquare == whiteKing.currentSquare){
                        illegalMoves.add(m);
                    }
                }
            } else { // if the input color is black
                for(Move om : opponentsResponses){

                    //If our opponent has a move that targets our king after we make a move, that means we moved into check which is not allowed
                    //As such we much remove it from our list of moves
                    if(blackKing != null && om.targetSquare == blackKing.currentSquare){
                        illegalMoves.add(m);
                    }
                }
            }
            undoMove();
        }
        //psuedoLegalMoves should now just be legal moves
        //System.out.println(psuedoLegalMoves.removeAll(illegalMoves));
        psuedoLegalMoves.removeAll(illegalMoves);
        if(psuedoLegalMoves.size() == 0){
            endGame();
        }

        return psuedoLegalMoves;
    }

    public boolean check(){
        return false;
    }

    public void endGame(){
        if(whiteKing != null && whiteKing.checked){
            winner = false;
        } else if (blackKing != null && blackKing.checked){
            winner = true;
        } else {
            winner = null;
        }
        gameEnded = true;
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

    private Piece getKing(boolean color){
        if(color){
            return blackKing;
        } else {
            return whiteKing;
        }

    }
}
