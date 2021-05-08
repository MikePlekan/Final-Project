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
        Board b = new Board();
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
        Board a = new Board();
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
        System.out.println(b);
        
        
    }
}
