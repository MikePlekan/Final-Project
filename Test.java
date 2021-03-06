import java.util.*;
/**
 * Class used to test movement of pieces;
 *
 * @author Luke Jennings
 * @version Spring 2021
 */
public class Test
{
    public static void main(String[] args){
        Board b = new Board(true);
        b.board[0] = new Queen(true,0);

        System.out.println("Possible squares on an empty board with a black queen on a8, white's move");
        ArrayList<Integer> validMoves = b.board[0].validMoves(b);
        for(Integer i: validMoves){
            System.out.println(Board.notation[i]);
        }
        b.board[1] = new Queen(false,1);
        System.out.println("Possible squares with a black queen on a8 and a white piece on b8, white's move");
        validMoves = b.board[0].validMoves(b);
        for(Integer i: validMoves){
            System.out.println(Board.notation[i]);
        }
        System.out.println("possibel squares with a black queen on a8 and b8, moving the A queen");
        b.board[1] = new Queen(true,1);
        validMoves = b.board[0].validMoves(b);
        for(Integer i: validMoves){
            System.out.println(Board.notation[i]);
        }
        Board a = new Board(true);
        a.board[36] = new Queen(false, 36);
        System.out.println("Possible squares with a white queen on e4 on an empty board");
        validMoves = a.board[36].validMoves(a);
        for(Integer i: validMoves){
            System.out.println(Board.notation[i]);
        }
        a.board[34] = new Bishop(false, 34);
        validMoves = a.board[34].validMoves(a);
        System.out.println("Possible squares with a white bishop on c4");

        for(Integer i: validMoves){
            System.out.println(Board.notation[i]);
        }

        a.board[2] = new Rook(false, 2);
        validMoves = a.board[2].validMoves(a);
        System.out.println("White rook on c8, on  same board as Bc4 and Qe4");
        for(Integer i: validMoves){
            System.out.println(Board.notation[i]);
        }

        System.out.println(a);

        System.out.println("Move queen from e4 to f5");
        a.movePiece(36,29);
        System.out.println(a);

        System.out.println("Invalid move queen from f5 to a8");
        validMoves = a.board[29].validMoves(a);
        for(Integer i: validMoves){
            System.out.println(Board.notation[i]);
        }
       // a.movePiece(29,0);
        //System.out.println(a);

        System.out.println("Invalid rook move to c4");
       // a.movePiece(2,34);
        System.out.println(a);

        System.out.println("Valid rook move to h8");
        a.movePiece(2,7);
        System.out.println(a);

        System.out.println("place white king on e1");
        a.placePiece("K",60);
        System.out.println(a);

        System.out.println("valid move Ke2");
        a.movePiece(60,52);
        System.out.println(a);

        System.out.println("empty board, place knight on f3");
        Board c = new Board(true);
        c.placePiece("N", 45);
        System.out.println(c);
        System.out.println("validMoves for Knight on f3 on an empty board");
        validMoves = c.board[45].validMoves(c);
        for(Integer i: validMoves){
            System.out.println(Board.notation[i]);
        }

        System.out.println("place knight on b1, check valid moves");
        c.placePiece("N", 57);
        System.out.println(c);
        validMoves = c.board[57].validMoves(c);
        for(Integer i: validMoves){
            System.out.println(Board.notation[i]);
        }

        System.out.println("place knight on g1, check valid moves");
        c.placePiece("n", 6);
        System.out.println(c);
        validMoves = c.board[6].validMoves(c);

        System.out.println("testing pawns on new board, white pawn on a5, c4, d4, e4, and h2, black pawn on a7,d5,h7");
        Board d = new Board(true);
        d.placePiece("P",24);
        d.placePiece("P",34);
        d.placePiece("P",35);
        d.placePiece("P",36);
        d.placePiece("P",55);
        d.placePiece("p",27);
        d.placePiece("p",8);
        d.placePiece("p",15);
        System.out.println(d);
        System.out.println("validMoves for e4 pawn");
        validMoves = d.board[36].validMoves(d);
        for(Integer i: validMoves){
            System.out.println(Board.notation[i]);
        }
        System.out.println("validMoves for d5 pawn");
        validMoves = d.board[27].validMoves(d);
        for(Integer i: validMoves){
            System.out.println(Board.notation[i]);
        }
        System.out.println("validMoves for h2 and h7 pawns");
        validMoves = d.board[55].validMoves(d);
        for(Integer i: validMoves){
            System.out.println(Board.notation[i]);
        }
        validMoves = d.board[15].validMoves(d);
        for(Integer i: validMoves){
            System.out.println(Board.notation[i]);
        }
        System.out.println("valid moves for a7 pawn");
        validMoves = d.board[8].validMoves(d);
        for(Integer i: validMoves){
            System.out.println(Board.notation[i]);
        }

        System.out.println("created new board e that has default starting position");
        Board e = new Board();
        System.out.println(e);

        System.out.println("print all validMoves for white on this board");
        ArrayList<Move> allValidMoves = e.generateMoves(false);
        for(Move m: allValidMoves){
            System.out.println(m);
        }

        System.out.println("print all validMoves for black on this board");
        allValidMoves = e.generateMoves(true);
        for(Move m: allValidMoves){
            System.out.println(m);
        }

        System.out.println("make a move, pawn to a4, and then undo it, then do it again");
        validMoves = e.board[48].validMoves(e);
        for(Integer i: validMoves){
            System.out.println(Board.notation[i]);
        }
        e.movePiece(48,32);
        System.out.println(e);

        e.undoMove();
        System.out.println(e);

        validMoves = e.board[48].validMoves(e);
        for(Integer i: validMoves){
            System.out.println(Board.notation[i]);
        }

        e.movePiece(48,32);
        System.out.println(e);

        System.out.println("make the move knight to c3, then undo it");
        e.movePiece("b1","c3");
        System.out.println(e);
        e.undoMove();
        System.out.println(e);

        System.out.println("Lets try undoing a capture");
        e.movePiece("e2","e4");
        e.movePiece("d7","d5");
        System.out.println(e);
        e.movePiece("e4","d5");
        System.out.println(e);
        e.undoMove();
        System.out.println(e);

        System.out.println("creating a new board to check generateLegalMoves()");
        Board f = new Board();
        f.movePiece("f2","f3");
        f.movePiece("e7","e5");
        f.movePiece("g2","g4");
        f.movePiece("d8","h4");
        System.out.println(f);
        System.out.println("checking generateMoves() and generateLegalMoves() for white, the second list should be empty");
        allValidMoves = f.generateMoves(false);
        for(Move m: allValidMoves){
            System.out.println(m);
        }
        System.out.println();
        ArrayList<Move> allLegalMoves = f.generateLegalMoves(false);
        for(Move m: allLegalMoves){
            System.out.println(m);
        }
        if(f.gameEnded){
            System.out.println(f.winner);
        }

        System.out.println("creating a new board to check if generateLegalMoves find the only moves that avoids checkmate");
        Board g = new Board(true);
        g.placePiece("k",7);
        g.placePiece("p",14);
        g.placePiece("p",15);
        g.placePiece("R",1);
        g.placePiece("r",13);
        System.out.println(g);
        validMoves = g.board[1].validMoves(g);
        for(Integer i: validMoves){
            System.out.println(Board.notation[i]);
        }
        allLegalMoves = g.generateLegalMoves(true);
        for(Move m: allLegalMoves){
            System.out.println(m);
        }
        if(g.gameEnded){
            System.out.println(g.winner);
        } else {
            System.out.println("game has not finished");
        }
        System.out.println("then play Rf8, and Rxf8#");
        g.movePiece("f7","f8");
        System.out.println(g);
        g.movePiece("b8","f8");
        System.out.println(g);
        if(g.gameEnded){
            System.out.println(g.winner);
        } else {
            System.out.println("game has not finished");
        }
        
        allLegalMoves = g.generateLegalMoves(true);
        for(Move m: allLegalMoves){
            System.out.println(m);
        }
        
        System.out.println("testing pawn promotion, currently promote to queen");
        Board h = new Board(true);
        h.placePiece("P",15);
        h.placePiece("P",9);
        h.placePiece("p",11);
        h.placePiece("q",0);
        System.out.println(h);
        h.movePiece(9,0);
        h.movePiece(11,3);
        System.out.println(h);
        
        System.out.println("All starting legal moves on turn 1");
        Board j = new Board();
        allLegalMoves = j.generateLegalMoves(false);
        for(Move m : allLegalMoves){
            System.out.println(m);
        }
        j.movePiece("e2","e4");
        j.movePiece("e7","e5");
        j.movePiece("d1","h5");
        j.movePiece("b8","c6");
        j.movePiece("f1","b5");
        System.out.println("Compare validMoves to legal Moves for black in this position");
        System.out.println(j);
        allValidMoves = j.generateMoves(true);
        for(Move m: allValidMoves){
            System.out.println(m);
        }
        System.out.println();
        allLegalMoves = j.generateLegalMoves(true);
        for(Move m : allLegalMoves){
            System.out.println(m);
        }
        
        j.movePiece("h5","e5");
        System.out.println(j);
        allLegalMoves = j.generateLegalMoves(true);
        System.out.println("now all of blacks legal Moves");
        for(Move m : allLegalMoves){
            System.out.println(m);
        }
        
        System.out.println("testing en passant");
        Board k = new Board();
        k.movePiece("e2","e4");

    }
}
